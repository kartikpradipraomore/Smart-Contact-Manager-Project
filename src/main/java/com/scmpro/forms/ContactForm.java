package com.scmpro.forms;


import org.springframework.web.multipart.MultipartFile;

import com.scmpro.validators.ValidFile;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactForm {


   
    private String name;

   
    private String email;

   
    private String phoneNumber;

    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

    @ ValidFile
    private MultipartFile contactImage;


}
