package utils;

import mechanics.GMResources;

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

    public void setFieldValue(Object object,
                              String fieldName,
                              String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                field.set(object, value);
                System.out.print(value);
            } else {
                if (field.getType().equals(int.class)) {
                    field.set(object, Integer.decode(value));
                } else {
                    if (field.getType().equals(ArrayList.class)) {
//                        boolean a = ((ArrayList<String>) field.get(this)).add(value);
//                        field.set(object, value);
                        ((GMResources) object).getColors().add(value);
                    }
                }
            }

            field.setAccessible(false);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
