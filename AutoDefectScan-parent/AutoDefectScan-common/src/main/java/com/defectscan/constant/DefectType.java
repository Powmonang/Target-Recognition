package com.defectscan.constant;

import java.util.HashMap;
import java.util.Map;

public class DefectType {
    private static final Map<Integer, String> defectTypeMap;

    // 静态代码块，在类加载时执行，用于初始化 defectTypeMap
    static {
        defectTypeMap = new HashMap<>();
        // 向 defectTypeMap 中添加键值对
        defectTypeMap.put(1, "边缘裂纹");
        defectTypeMap.put(2, "横向裂纹");
        defectTypeMap.put(3, "表面杂质");
        defectTypeMap.put(4, "斑块缺陷");
        defectTypeMap.put(5, "其他");
    }

    // 提供一个公共的静态方法，用于获取 defectTypeMap
    public static Map<Integer, String> getDefectTypeMap() {
        return defectTypeMap;
    }
}
