package com.lipcoding.restfulwebservice.helloworld;

// lombok

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // ToString, Getter, Setter
@AllArgsConstructor // 필드 포함 생성자
@NoArgsConstructor  // default 생성자
public class HelloWorldBean {
    private String message;

}

