package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private String id;
    private String name;
    private String type;
    private String keyTime;
    private String mark;
}
