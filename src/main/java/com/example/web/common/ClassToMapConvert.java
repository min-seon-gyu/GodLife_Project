package com.example.web.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassToMapConvert {
    public static Map<Object, Object> convert(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<Object, Object> result = new HashMap<>();
        for(int i = 0 ; i < fields.length ; i++){
            fields[i].setAccessible(true);
            result.put(fields[i].getName(), fields[i].get(object));
        }
        return result;
    }
}
