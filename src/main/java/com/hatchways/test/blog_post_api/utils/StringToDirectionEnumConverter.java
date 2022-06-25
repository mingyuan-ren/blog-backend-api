package com.hatchways.test.blog_post_api.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDirectionEnumConverter implements Converter<String, DirectionEnum> {
    @Override
    public DirectionEnum convert(String source) {
        return DirectionEnum.valueOf(source.toUpperCase());
    }
}
