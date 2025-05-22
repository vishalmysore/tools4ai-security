package io.github.vishalmysore;

import com.t4a.api.JavaMethodAction;
import com.t4a.processor.GeminiV2ActionProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SecureGeminiV2ActionProcessor extends GeminiV2ActionProcessor {
    public  Object invokeReflection(Method method, JavaMethodAction javaMethodAction, List<Object> parameterValues) throws IllegalAccessException, InvocationTargetException {
        Object result;
        result = method.invoke(javaMethodAction.getActionInstance(), parameterValues.toArray());
        return result;
    }
}
