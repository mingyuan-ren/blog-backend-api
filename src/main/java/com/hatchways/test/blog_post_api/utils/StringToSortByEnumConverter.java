package com.hatchways.test.blog_post_api.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
public class StringToSortByEnumConverter implements Converter<String, SortByEnum> {
    @Override
    public SortByEnum convert(String source) {
        try {
            return SortByEnum.valueOf(source.toUpperCase());
        } catch(MethodArgumentTypeMismatchException e) {
            return null;
        }
    }
}
