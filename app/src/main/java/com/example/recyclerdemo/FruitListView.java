package com.example.recyclerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class FruitListView extends ListView {
    private int maxOverScrollYDis;

    private float y1, y2;

    private Context mContext;

    private int mMaxYOverScrollDistance;

    public FruitListView(Context context) {
        super(context);
        mContext = context;
        Init();
    }

    public FruitListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        Init();
    }

    public FruitListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Init();
    }

    private void Init() {
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverScrollDistance = (int) (density * 200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = ev.getY(0);
                break;
            case MotionEvent.ACTION_MOVE:
                y2 = ev.getY(0);
                maxOverScrollYDis = (int) Math.abs(y2 - y1);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(
            int deltaX, int deltaY, int scrollX, int scrollY,
            int scrollRangeX, int scrollRangeY,
            int maxOverScrollX, int maxOverScrollY,
            boolean isTouchEvent) {
//        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
//                maxOverScrollX, maxOverScrollY, isTouchEvent);
        Log.d("Scroll Dims", "Delta X: " + deltaX + " Delta Y: " + deltaY +
                " scroll X: " + scrollX + " scroll Y: " + scrollY + " scrollRange X: " + scrollRangeX
                + " scrollRange Y: " + scrollRangeY + " maxOverScrollX " + maxOverScrollX +
                " maxOverScrollY " + maxOverScrollYDis / 2 + " isTouch" + isTouchEvent);
        return super.overScrollBy(
                deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX,
                mMaxYOverScrollDistance, isTouchEvent);
    }
}
