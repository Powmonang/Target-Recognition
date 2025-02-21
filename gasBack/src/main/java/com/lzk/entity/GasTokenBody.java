package com.lzk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasTokenBody {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;
}
