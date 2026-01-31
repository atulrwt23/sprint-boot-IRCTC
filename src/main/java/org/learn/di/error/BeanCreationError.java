package org.learn.di.error;

public class BeanCreationError extends RuntimeException {
    public BeanCreationError(String message) {
        super(message);
    }


    public static <T> BeanCreationError notAComponent(Class<T> clz) {
        return new BeanCreationError(clz.getName() + " is not annotated with @Component");
    }
}
