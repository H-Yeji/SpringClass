package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;


@NoArgsConstructor
@Data
public class CommonResDto {
    private int status_code;
    private String status_message;
    private Object result;

    public CommonResDto(HttpStatus httpStatus, String message, Object result) {
        this.status_code = httpStatus.value(); // int로 받기위해
        this.status_message = message;
        this.result = result;
    }
}
