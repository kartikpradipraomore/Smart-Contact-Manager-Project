package com.scmpro.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scmpro.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactForm {

    @NotBlank(message = "Name Is Required")
    private String name;

    @Email
    @NotBlank(message = "Email Is Required")
    private String email;

    @NotBlank(message = "Phone Number Is Required")
    private String phoneNumber;

    @NotBlank(message = "Address Is Required")
    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

    @ValidFile
    private MultipartFile contactImage;

}
