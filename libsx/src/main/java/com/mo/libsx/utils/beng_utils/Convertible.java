package com.mo.libsx.utils.beng_utils;

/**
 * @ author：mo
 * @ data：2019/2/15:10:46
 * @ 功能：
 */
public interface Convertible<F, T> {

    T convert(F from);
}
