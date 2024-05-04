package com.example.web.common;

public class NumberFormatConvert {
    private static final StringBuilder sb = new StringBuilder();
    public static String convert(Long value){
        while(value >= 10){
            sb.append(value%10);
            value /= 10;
            if(sb.length() % 4 == 3){
                sb.append(",");
            }
        }
        sb.append(value);
        String str = sb.reverse().toString();
        sb.setLength(0);
        return str;
    }
}
