package org.learn.di.error;

public class ApplicationPropertiesLoadError extends RuntimeException {
    public ApplicationPropertiesLoadError(String msg) {
        super(msg);
    }
}
