package com.ll.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther kylin
 * @Data 2020/6/26 - 23:01
 * @Package com.ll.apt_annotation
 * @Description
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface LLBindView {
    int value();
}
