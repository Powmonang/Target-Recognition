package com.lzk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGasDataDTO {
    private Integer start;
    private Integer end;
    private Integer limit;
}
