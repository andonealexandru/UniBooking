package com.unibooking.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExceptionResponse {
    String timestamp;
    Integer status;
    String error;
    String path;
}
