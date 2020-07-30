package com.mo.libsx.modle.inject.methods;


import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ author：mo
 * @ data：2019/2/26:9:30
 * @ 功能：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KInjectRecycleViewAdapter {
    //recycleView id
    @IdRes
    int value();
    //list数据
    String date();
}
