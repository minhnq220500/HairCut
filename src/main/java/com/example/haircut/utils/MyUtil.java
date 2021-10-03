package com.example.haircut.utils;

public class MyUtil {

    public String getAutoIncreasementId(String id){
        String head = id.substring(0,1);
        String body = id.substring(1,id.length());
        int newId = Integer.parseInt(body) + 1;
        String result = head + newId;
        return result;
    }

}
