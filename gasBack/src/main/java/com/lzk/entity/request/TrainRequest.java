package com.lzk.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainRequest {
    private String baseModel;
    private List<String> imageData;
    private Map<String, Object> trainParameter;
}
