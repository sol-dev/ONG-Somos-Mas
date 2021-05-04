package com.team32.ong.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrganizationErrorInfo {
    @JsonProperty("message")
    private String message;
    @JsonProperty("status_code")
    private int statusCode;
 
}
