package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasTokenBody {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;
}
