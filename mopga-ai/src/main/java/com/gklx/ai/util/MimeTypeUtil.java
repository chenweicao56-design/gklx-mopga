package com.gklx.ai.util;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeUtil {
    private static final Map<String, String> MIME_MAP = new HashMap<>();

    static {
        MIME_MAP.put("jpg", "image/jpeg");
        MIME_MAP.put("jpeg", "image/jpeg");
        MIME_MAP.put("png", "image/png");
        MIME_MAP.put("mp4", "video/mp4");
        MIME_MAP.put("pdf", "application/pdf");
        MIME_MAP.put("txt", "text/plain");
    }

    public static String getMimeType(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return MIME_MAP.getOrDefault(ext, "application/octet-stream");
    }
}