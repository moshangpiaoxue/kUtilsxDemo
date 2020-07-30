package com.mo.kutilsxdemo.activitys.widget.text_view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.text_view.CreditsRollTextView;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：星球大战样式滚动TextView
 */
public class CreditsRollTextViewActivity extends BaseActivity implements TitleBarAction {
    private CreditsRollTextView cr_fast_look;
    private SeekBar sb_fast_look;
    private static final float SCROLL_ANIM_DURATION = 30000;    // [ms] = 30 s

    private boolean mScrolling;
    private ValueAnimator mScrollAnimator;

    private int tag=0;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textview_creditroll;
    }

    @Override
    protected void initView() {
        setTitle("星球大战样式滚动TextView");
        cr_fast_look=findViewById(R.id.cr_fast_look);
        sb_fast_look=findViewById(R.id.sb_fast_look);

        sb_fast_look.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cr_fast_look.setScrollPosition(progress / 1000f); // We have increments of 1/100000 %
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mScrolling) {
                    stopScrollAnimation();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cr_fast_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mScrolling) {
                    animateScroll();
                } else {
                    stopScrollAnimation();
                }
            }
        });
        String[] split = cr_fast_look.getText().toString().split("\n|\r");
        sb_fast_look.setMax(split.length*9);
        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (tag!=sb_fast_look.getMax()){
                    sb_fast_look.setProgress(tag++);
                    handler.postDelayed(this, 100);
                }else {
                    handler.removeCallbacks(this);
                }

            }
        };
        handler.postDelayed(runnable, 100);//每两秒执行一次runnable.

    }

    private void animateScroll() {
        mScrolling = true;
        mScrollAnimator = ObjectAnimator.ofInt(sb_fast_look, "progress", sb_fast_look.getProgress(), sb_fast_look.getMax());
        mScrollAnimator.setDuration(
                (long) (SCROLL_ANIM_DURATION * (1 - (float) sb_fast_look.getProgress() / sb_fast_look.getMax())));
        mScrollAnimator.setInterpolator(new LinearInterpolator());
        mScrollAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Don't care
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Don't care
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Don't care
            }
        });
        mScrollAnimator.start();
    }

    private void stopScrollAnimation() {
        if (mScrollAnimator != null) {
            mScrollAnimator.cancel();
            mScrollAnimator = null;
        }
    }
    @Override
    protected void initData() {

    }
}
