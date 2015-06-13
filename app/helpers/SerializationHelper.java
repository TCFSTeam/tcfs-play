package helpers;

import com.avaje.ebean.annotation.EnumValue;

import java.lang.reflect.Field;

/**
 * Created by Alexander on 6/12/2015.
 */
public class SerializationHelper {
    public static <T extends Enum<T>> String serialize(T theEnum) throws IllegalAccessException {
            for (Field f : theEnum.getDeclaringClass().getDeclaredFields()) {
                if (theEnum.equals(f.get(theEnum))) {
                    EnumValue enumValue = f.getAnnotation(EnumValue.class);
                    if (enumValue != null)
                        return enumValue.value();
                }
            }
        return null;
    }
}
