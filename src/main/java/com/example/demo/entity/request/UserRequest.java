package com.example.demo.entity.request;

import com.example.demo.entity.pojo.Consumer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest extends Consumer{
    private String token;

    private String code;
}
