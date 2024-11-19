package com.example.moodymovie.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebUtils {

    public static final String MSG_INFO = "infoMessage";
    public static final String MSG_ERROR = "errorMessage";

    private static MessageSource messageSource;

    public WebUtils(MessageSource messageSource) {
        WebUtils.messageSource = messageSource;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}