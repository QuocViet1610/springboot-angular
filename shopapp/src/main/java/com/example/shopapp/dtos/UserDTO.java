package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "phone number is required")
    private String phoneNumber;


    @JsonProperty("address")
    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "password can not is blank")
    private String password;

    private String retypePassword;

    @JsonProperty("date_of_Brith")
    private LocalDateTime dateOfBirth;

    @JsonProperty("facebook_account_id")
    private Long facebookAccountId;

    @JsonProperty("google_account_id")
    private Long googleAccountId;

    // database ở int tương tương kiểu long java
    @JsonProperty("role_id")
    @NotNull(message = "Role ID is required")
    private Long roleId;


}









