package com.example.haircut.utils;

public class MyUtil {

//    public String getAutoIncreasementId(String id){
//        String head = id.substring(0,1);
//        String body = id.substring(1,id.length());
//        int newId = Integer.parseInt(body) + 1;
//        String result = head + newId;
//        return result;
//    }

    public String autoIncrementId(String id){
        try{
            String head;
            String result;
            
            String[] elements = id.split("-");
            head = elements[0] + "-";
            int currentMaxId = Integer.parseInt(elements[1]) + 1;
            result = head + currentMaxId;

            return result;
        }catch (Exception e){
            return null;
        }
    }
}
