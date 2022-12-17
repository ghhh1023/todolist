package com.example.todolist.pojo;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Validated
@Getter
@Setter
@AllArgsConstructor
public class User {
    @NotNull
//    @Length(max = 11, min = 11, message = "手机号的长度必须是11位.")
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号的长度必须是11位.")
    private String userName;

    @NotNull
    @Length(max = 16, min = 6, message = "密码不合法")
    private String password;

    private String salt;

    private Integer id;

    public User() {

    }

    @Override
    public String toString() {
        String ret = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ret = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
