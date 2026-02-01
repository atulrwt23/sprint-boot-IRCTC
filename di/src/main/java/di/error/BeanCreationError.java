package di.error;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCreationError extends RuntimeException {
    public BeanCreationError(String message) {
        super(message);
    }


    public static <T> BeanCreationError notAComponent(Class<T> clz) {
        return new BeanCreationError(clz.getName() + " is not annotated with @Component");
    }

    public static <T> BeanCreationError noImplementationFound(Class<T> clz) {
        return new BeanCreationError("No implementation found for " + clz.getName());
    }

    public static <T> BeanCreationError noPrimaryImplementationFound(Class<T> clz, List<Class<?>> implementingClasses) {
        String implementationClassesNames = implementingClasses.stream().map(Class::getName).collect(Collectors.joining(", "));
        return new BeanCreationError("No primary implementation found for " + clz.getName() + ". Available implementations are: " + implementationClassesNames);
    }

    public static <T> BeanCreationError moreThanOnePrimaryImplementationPresent(Class<T> clz, List<Class<?>> primaryClass) {
        String implementationClassesNames = primaryClass.stream().map(Class::getName).collect(Collectors.joining(", "));
        return new BeanCreationError("More than one primary implementation found for " + clz.getName() + " : " + implementationClassesNames);
    }
}
