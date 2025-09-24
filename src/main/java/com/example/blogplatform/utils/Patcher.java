package com.example.blogplatform.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public final class Patcher {

    private Patcher() {
    }

    public static <T> boolean patch(T existingObject, T newObject) throws IllegalAccessException {
        Class<?> classType = existingObject.getClass();
        Field[] fields = classType.getDeclaredFields();
        boolean isObjectChanged = false;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = field.get(newObject);
            if (fieldValue != null) {
                field.set(existingObject, fieldValue);
                isObjectChanged = true;
            }

            field.setAccessible(false);
        }
        return isObjectChanged;
    }
}
