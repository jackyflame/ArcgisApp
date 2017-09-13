package com.haozi.baselibrary.constants;

import okhttp3.MediaType;

public interface ContentType {
    String CHARSET_UTF_8 = "utf-8";
    String CONTENT_TYPE_JSON = "application/json; charset=" + CHARSET_UTF_8;
    String CONTENT_TYPE_TEXT_PLAIN = "text/plain; charset=" + CHARSET_UTF_8;
    String CONTENT_TYPE_MULTIPART = "multipart/form-data; charset=" + CHARSET_UTF_8;

    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; " + CHARSET_UTF_8);
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; " + CHARSET_UTF_8);
    MediaType MEDIA_TYPE_TEXT_PLAIN = MediaType.parse("text/plain; " + CHARSET_UTF_8);
    MediaType MEDIA_TYPE_MULTIPART = MediaType.parse("multipart/form-data; " + CHARSET_UTF_8);

}
