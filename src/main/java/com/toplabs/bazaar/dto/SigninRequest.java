package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.Role32;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    private String mobileNumber;
    private Role32 requestedRole;
}
