package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;


}
