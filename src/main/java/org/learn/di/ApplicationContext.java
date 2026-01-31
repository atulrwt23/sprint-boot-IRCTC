package org.learn.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;

public class ApplicationContext {
    private final HashMap<String, Object> cache;

    public ApplicationContext() {
        this.cache = new HashMap<>();
    }

    public <T> T get(Class<T> clz) {
        if (this.cache.containsKey(clz.getName())) {
            System.out.println("User instance from cache for class : " + clz.getName());
            return (T) this.cache.get(clz.getName());
        }
        System.out.println("Creating instance of " + clz.getName());
        Constructor<?> constructor = clz.getConstructors()[0];
        Object[] args = Arrays.stream(constructor.getParameters())
                .map(parameter -> get(parameter.getType()))
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
}
