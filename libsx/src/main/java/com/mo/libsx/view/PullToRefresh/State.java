package com.mo.libsx.view.PullToRefresh;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jiang on 16/8/17.
 */

public class State {


    @IntDef({REFRESH, LOADMORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface REFRESH_STATE {

    }

    public static final int REFRESH = 10;
    public static final int LOADMORE = 11;
}
