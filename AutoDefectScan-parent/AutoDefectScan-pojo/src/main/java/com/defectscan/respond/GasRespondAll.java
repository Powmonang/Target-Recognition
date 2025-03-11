package com.defectscan.respond;

import com.lzk.entity.GasReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasRespondAll {
    private String code;
    private String msg;
    private List<GasReport> data;
}
