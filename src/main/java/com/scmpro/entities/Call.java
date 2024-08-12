package com.scmpro.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "Who_Contact")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "First name is Required")
    private String firstName;
    @NotBlank(message = "Last name is Required")
    private String lastName;

    private String phoneNumber;
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Proffesion is required")
    private String profession;
    @Column(length = 1500)
    @NotBlank(message = "Message is required")
    private String message;


}
