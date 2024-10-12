package com.DDN.login.exception;

public class BrandNotFoundException extends IllegalArgumentException{
    public BrandNotFoundException(String msg){
        super(msg);
    }
}
