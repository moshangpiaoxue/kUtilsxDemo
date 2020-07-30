package com.mo.libx;

import android.app.Application;
import android.content.Context;

import com.mo.libx.inject.ViewInjector;
import com.mo.libx.utils.activity_utils.ActivitysUtil;

import java.lang.reflect.Method;

/**
 * @ author：mo
 * @ data：2020/7/20:10:10
 * @ 功能：
 */
public final class k {
    /**
     * 项目的整体上下文环境
     *
     * @return
     */
    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("在manifest注册Application，并且在Application#onCreate()方法里 x.Ext.init(app) ");
            }
        }
        return Ext.app;
    }
    public static boolean isDebug() {
        return Ext.debug;
    }

    public static class Ext {

        private static Application app;
        private static boolean debug = true;
        private static ViewInjector viewInjector;
        private Ext() {
        }
        public static void init(Application app) {
            if (Ext.app == null) {
                Ext.app = app;
            }
            Ext.app.registerActivityLifecycleCallbacks(ActivitysUtil.getCallBack());
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }
        public static void setViewInjector(ViewInjector viewInjector) {
            Ext.viewInjector = viewInjector;
        }
    }
    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
