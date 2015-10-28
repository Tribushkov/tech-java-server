package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by dmitri on 28.10.15.
 */

public class ReflectionHelper {
    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object object,
                                     String fieldName,
                                     String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                if (!fieldName.equals("colors")) {
                    field.set(object, value);
                } else {
                    field.set(object, value);
                }
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.decode(value));
            }

            field.setAccessible(false);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
