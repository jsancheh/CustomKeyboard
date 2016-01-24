package com.joseluis.jlshkeyboard.views;

/**
 * Created by jlsh on 22/1/16.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;


/**
 * Author José Luis Sánchez Hurtado
 */
public class CustomKeyboardView extends KeyboardView {

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return super.getOnKeyboardActionListener();
    }

    @Override
    public void setPreviewEnabled(boolean previewEnabled) {
        super.setPreviewEnabled(previewEnabled);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void showWithAnimation(Animation animation) {
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.VISIBLE);
            }
        });

        setAnimation(animation);
    }
}
