package org.ei.telemedicine.view.customControls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by naveen on 5/19/15.
 */
public class CustomFrameLayout extends FrameLayout {
    OnClickListener mOnClickListener;

    public CustomFrameLayout(Context context) {
        super(context);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CustomFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mOnClickListener != null;
    }
}