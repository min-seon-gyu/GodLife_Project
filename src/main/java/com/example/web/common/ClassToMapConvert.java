package com.example.web.common;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassToMapConvert {
    public static Map<Object, Object> convert(Object object){
        try{
            Field[] fields = object.getClass().getDeclaredFields();
            Map<Object, Object> result = new HashMap<>();
            for(int i = 0 ; i < fields.length ; i++){
                fields[i].setAccessible(true);
                result.put(fields[i].getName(), fields[i].get(object));
            }
            return result;
        }catch (IllegalAccessException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Map 타입 변환에 실패했습니다.");
        }
    }
}
