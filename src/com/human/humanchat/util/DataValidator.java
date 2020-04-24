package com.human.humanchat.util;

public class DataValidator {
    private DataValidator(){

    }
    public  static  boolean isNullOrBlank(String value){
        return value == null ||  value.trim().isEmpty();
    }
}
