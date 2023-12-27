package com.example.be_java_hisp_w23_g3.exception;

public class InvalidOrderException extends RuntimeException {
   public InvalidOrderException(String message) {
       super(message);
   }
}
