package com.intheeast.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    // 공용 ObjectMapper 인스턴스 (Thread-safe)
    public static final ObjectMapper MAPPER = new ObjectMapper();
}
