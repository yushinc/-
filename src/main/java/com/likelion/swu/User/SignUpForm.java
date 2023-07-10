package com.likelion.swu.User;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {
    @NotBlank
    @Length(min=3, max=20)
    @Pattern(regexp="^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String name;

    @NotBlank
    private String student_number;

    @NotBlank
    @Length(min=8, max=50)
    private String password;

    @NotBlank
    private String major;

    public String getStudentNumber() {
        return student_number;
    }
    public String getName() {
        return name;
    }
    public String getMajor() {return major;}
    public String getPassword(){return password;}
}
