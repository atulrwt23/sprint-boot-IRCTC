package org.learn.di;

import org.learn.di.annotation.Component;
import org.learn.di.annotation.Primary;
import org.learn.di.error.BeanCreationError;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    private <T> T get(Class<T> clz, Collection<Class<?>> classes) {
        if (this.cache.containsKey(clz.getName())) {
            System.out.println("User instance from cache for class : " + clz.getName());
            return (T) this.cache.get(clz.getName());
        }

        if (!clz.isAnnotationPresent(Component.class) && !clz.isInterface()) {
            throw BeanCreationError.notAComponent(clz);
        }

        if (clz.isInterface()) {
            Class<?> implementationClass = findImplementationOf(clz, classes);
            if (implementationClass == null) {
                throw BeanCreationError.noImplementationFound(clz);
            }

            System.out.println("Found implementation for interface " + clz.getName() + " : " + implementationClass.getName());
            T instance = (T) createInstanceOf(implementationClass, classes);
            this.cache.put(clz.getName(), instance);
            return instance;
        }

        return createInstanceOf(clz, classes);
    }

    private <T> T createInstanceOf(Class<T> clz, Collection<Class<?>> classes) {
        System.out.println("Creating instance of " + clz.getName());
        Constructor<?> constructor = clz.getConstructors()[0];
        Object[] args = Arrays.stream(constructor.getParameters())
                .map(parameter -> get(parameter.getType(), classes))
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

    private <T> Class<?> findImplementationOf(Class<T> clz, Collection<Class<?>> classes) {
        List<Class<?>> implementingClasses = classes.stream()
                .filter(aClass -> clz.isAssignableFrom(aClass))
                .collect(Collectors.toList());

        if (implementingClasses.isEmpty()) {
            throw BeanCreationError.noImplementationFound(clz);
        }

        if (implementingClasses.size() == 1) {
            return implementingClasses.get(0);
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

        return primaryClass.get(0);
    }

    public void init() {
        Collection<Class<?>> allComponentsClasses = ComponentScanner.getAllComponentsClasses("");
        for (Class<?> aClass : allComponentsClasses) {
            get(aClass, allComponentsClasses);
        }
    }

    public <T> T getBean(Class<T> Clz) {
        return (T) cache.get(Clz.getName());
    }
}
