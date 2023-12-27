package com.example.be_java_hisp_w23_g3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ValidationExceptionDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;
}
