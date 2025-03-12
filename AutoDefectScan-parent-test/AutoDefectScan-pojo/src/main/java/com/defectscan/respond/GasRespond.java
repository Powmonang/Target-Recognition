package com.defectscan.respond;

import com.defectscan.entity.GasReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasRespond {
    private String code;
    private String msg;
    private GasReport data;
}
