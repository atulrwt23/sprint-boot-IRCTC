package di.error;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AutoConfigurationError extends RuntimeException {
    public AutoConfigurationError(String message) {
        super(message);
    }
    public static AutoConfigurationError isInvalidCountOfConfigureMethod(Class<?> aClass, ArrayList<Method> methods) {
        String methodName = methods.stream().map(Method::getName).collect(Collectors.joining(", "));
        String message = "Invalid count of configure method found in class " + aClass.getName() + " count is " + methodName;
        return new AutoConfigurationError(message);
    }

    public static AutoConfigurationError isInvalidConfigureMethod(Class<?> aClass, String name, String message) {
        String msg = "Invalid configure method found in class " + aClass.getName() + " method name is " + name;
        return new AutoConfigurationError(message + "\n" + msg);
    }
}
