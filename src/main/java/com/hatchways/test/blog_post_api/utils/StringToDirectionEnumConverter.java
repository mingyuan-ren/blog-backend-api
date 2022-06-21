package com.hatchways.test.blog_post_api.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
public class StringToDirectionEnumConverter implements Converter<String, DirectionEnum> {
    @Override
    public DirectionEnum convert(String source) {
        try {
            return DirectionEnum.valueOf(source.toUpperCase());
        } catch(MethodArgumentTypeMismatchException e) {
            return null;
        }
    }
}
