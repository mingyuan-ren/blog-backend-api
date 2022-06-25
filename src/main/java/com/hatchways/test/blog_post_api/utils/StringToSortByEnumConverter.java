package com.hatchways.test.blog_post_api.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortByEnumConverter implements Converter<String, SortByEnum> {
    @Override
    public SortByEnum convert(String source) {
        return SortByEnum.valueOf(source.toUpperCase());
    }
}
