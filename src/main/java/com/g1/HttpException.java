package com.g1;

import org.springframework.http.ResponseEntity;

public class HttpException extends Exception{

    public ResponseEntity responseEntity;

    public HttpException(ResponseEntity responseEntity){
        this.responseEntity = responseEntity;
    }

}
