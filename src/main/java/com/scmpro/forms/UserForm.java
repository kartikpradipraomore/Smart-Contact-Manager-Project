package com.scmpro.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {

    // @NotBlank(message = "User Name Is Required")
    @Size(min = 3, max = 20)
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email Is Required")
    private String email;

    @NotBlank(message = "Password Is Required")
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank(message = "About Is Required")
    private String about;

    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;

}
