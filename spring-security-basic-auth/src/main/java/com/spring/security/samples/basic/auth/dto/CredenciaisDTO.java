package com.spring.security.samples.basic.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {

    private String login;
    private String senha;
    
}
