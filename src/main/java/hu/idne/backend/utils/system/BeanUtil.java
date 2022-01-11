package hu.idne.backend.utils.system;

import hu.idne.backend.exceptions.OwnDefaultException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanUtil {

    private BeanUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> toProperties(T t) {
        if (t == null) {
            return null;
        }

        if (t instanceof Map) {
            return (Map<String, Object>) t;
        }

        Map<String, Object> result = new HashMap<>();

        if (t.getClass().isArray()) {
            Object[] arr = (Object[]) t;
            for (int i = 0; i < arr.length; i++) {
                result.put(Integer.toString(i), arr[i]);
            }
            return result;
        }

        if (t instanceof Iterable<?>) {
            Iterator<?> it = ((Iterable<?>) t).iterator();
            int i = 0;
            while (it.hasNext()) {
                result.put(Integer.toString(i++), it.next());
            }
            return result;
        }


        for (Field field : getFields(t.getClass())) {
            if ("$jacocoData".equals(field.getName())) {
                continue;
            }
            result.put(field.getName(), invokeMethod(field, t));
        }

        return result;
    }

    private static <T> List<Field> getFields(Class<T> top) {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = top;
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static String getFieldGetterName(Field field) {
        String name = field.getName();
        String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return field.getType().equals(boolean.class) ? "is" + methodName : "get" + methodName;
    }

    private static Method getMethod(Class<?> sourceClazz, Field field) {
        try {
            return sourceClazz.getMethod(getFieldGetterName(field));
        } catch (NoSuchMethodException e) {
            throw new OwnDefaultException("something went very wrong", e);
        }
    }

    private static <T> String invokeMethod(Field field, T target) {
        Method method = getMethod(target.getClass(), field);
        Object value;
        try {
            value = method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new OwnDefaultException("something went very wrong", e);
        }

        if (value != null) {
            return value.toString();
        }

        return null;
    }
}
