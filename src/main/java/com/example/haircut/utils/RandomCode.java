package com.example.haircut.utils;

public class RandomCode {
    public String verifyCode() {
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        String codeStr=Integer.toString(code);
        return codeStr;
    }
}
