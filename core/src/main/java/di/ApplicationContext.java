package di;

import di.annotation.*;
import di.error.AutoConfigurationError;
import di.error.BeanCreationError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationContext {
    private final HashMap<String, Object> cache;

    private ApplicationContext(HashMap<String, Object> cache) {
        this.cache = cache;
    }

    public static ApplicationContext getInstance() {
        ApplicationContext applicationContext = new ApplicationContext(new HashMap<>());
        applicationContext.init();
        return applicationContext;
    }

    private <T> T get(Class<T> clz, Collection<Class<?>> classes, ApplicationProperties applicationProperties) {
        if (this.cache.containsKey(clz.getName())) {
            System.out.println("User instance from cache for class : " + clz.getName());
            return (T) this.cache.get(clz.getName());
        }

        if (!isAnnotatedWithComponent(clz) && !clz.isInterface()) {
            throw BeanCreationError.notAComponent(clz);
        }

        if (clz.isAnnotation()) {
            return null;
        }

        if (clz.isInterface()) {
            Class<T> implementationClass = findImplementationOf(clz, classes);
            if (implementationClass == null) {
                throw BeanCreationError.noImplementationFound(clz);
            }

            System.out.println("Found implementation for interface " + clz.getName() + " : " + implementationClass.getName());
            T instance = (T) createInstanceOf(implementationClass, classes, applicationProperties);
            this.cache.put(clz.getName(), instance);
            return instance;
        }

        return createInstanceOf(clz, classes, applicationProperties);
    }

    private <T> boolean isAnnotatedWithComponent(Class<T> clz) {
        return clz.isAnnotationPresent(Component.class) ||
                Arrays.stream(clz.getAnnotations())
                        .anyMatch(annotation -> annotation
                                .annotationType()
                                .isAnnotationPresent(Component.class)
                        );
    }

    private <T> T createInstanceOf(Class<T> clz, Collection<Class<?>> classes, ApplicationProperties applicationProperties) {
        System.out.println("Creating instance of " + clz.getName());
        Constructor<?>[] constructors = clz.getConstructors();
        if (constructors.length < 1) {
            return null;
        }
        Constructor<?> constructor = constructors[0];
        Object[] args = Arrays.stream(constructor.getParameters())
                .map(parameter -> {
                    if (parameter.isAnnotationPresent(Property.class)) {
                        String value = applicationProperties.get(parameter.getAnnotation(Property.class).value());
                        return value;
                    }
                    return get(parameter.getType(), classes, applicationProperties);
                })
                .toArray();
        try {

            T createdInstance = (T) constructor.newInstance(args);
            this.cache.put(clz.getName(), createdInstance);
            return createdInstance;
        } catch (Throwable e) {
            System.out.println("Message : " + e.getMessage().toUpperCase());
            throw new RuntimeException("Unable to instantiate class " + clz.getName());
        }
    }

    private <T> Class<T> findImplementationOf(Class<T> clz, Collection<Class<?>> classes) {
        List<Class<?>> implementingClasses = classes.stream()
                .filter(aClass -> clz.isAssignableFrom(aClass))
                .collect(Collectors.toList());

        if (implementingClasses.isEmpty()) {
            throw BeanCreationError.noImplementationFound(clz);
        }

        if (implementingClasses.size() == 1) {
            return (Class<T>) implementingClasses.get(0);
        }

        List<Class<?>> primaryClass = implementingClasses.stream()
                .filter(aClass -> aClass.isAnnotationPresent(Primary.class))
                .collect(Collectors.toList());

        if (primaryClass.isEmpty()) {
            throw BeanCreationError.noPrimaryImplementationFound(clz, implementingClasses);
        }

        if (primaryClass.size() > 1) {
            throw BeanCreationError.moreThanOnePrimaryImplementationPresent(clz, primaryClass);
        }

        return (Class<T>) primaryClass.get(0);
    }

    public void init() {
        Collection<Class<?>> classes = ComponentScanner.getAllComponentsClasses("");
        ApplicationProperties applicationProperties = ApplicationProperties.load();
        for (Class<?> aClass : classes) {
            get(aClass, classes, applicationProperties);
        }
        runAutoConfigurations(this.cache.values());
    }

    private void runAutoConfigurations(Collection<Object> allClasses) {
        allClasses.stream()
                .filter(clz -> clz.getClass().isAnnotationPresent(AutoConfiguration.class))
                .forEach(clz -> runAutoConfiguration(clz , allClasses));
    }

    private void runAutoConfiguration(Object clz, Collection<Object> allClasses) {
        ArrayList<Method> method = Arrays.stream(clz.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Configure.class))
                .collect(Collectors.toCollection(ArrayList::new));

        if (method.isEmpty() || method.size() > 1) {
            throw AutoConfigurationError.isInvalidCountOfConfigureMethod(clz.getClass(), method);
        }

        try {
            method.get(0).invoke(clz, allClasses);
        } catch (Throwable e) {
            throw AutoConfigurationError.isInvalidConfigureMethod(clz.getClass(), method.get(0).getName(), e.getMessage());
        }
    }

    public <T> T getBean(Class<T> Clz) {
        return (T) cache.get(Clz.getName());
    }
}
