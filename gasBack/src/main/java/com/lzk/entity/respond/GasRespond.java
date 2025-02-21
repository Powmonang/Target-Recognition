package com.lzk.entity.respond;

import com.lzk.entity.GasReport;
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
