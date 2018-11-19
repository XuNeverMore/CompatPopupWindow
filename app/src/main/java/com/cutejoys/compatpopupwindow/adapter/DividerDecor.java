package com.cutejoys.compatpopupwindow.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class DividerDecor extends RecyclerView.ItemDecoration {


    private float dividerHeight = 2;
    private float marginLeft = 0;
    private float marginRight = 0;
    private int dividerColor = 0xffdfdfdf;
    private boolean skipHeader = false;

    public void setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DividerDecor(float dividerHeight, float marginLeft, float marginRight, int dividerColor) {
        this.dividerHeight = dividerHeight;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.dividerColor = dividerColor;
    }

    public DividerDecor() {

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {


        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0 && skipHeader) {
                continue;
            }
            View childAt = parent.getChildAt(i);
            float top = childAt.getBottom();
            float left = i == childCount - 1 ? 0 : marginLeft;
            float right = i == childCount - 1 ? childAt.getRight() : childAt.getRight() - marginRight;
            float bottom = top + dividerHeight;
//            paint.setColor(Color.WHITE);
//            c.drawRect(0, top, childAt.getRight(), bottom, paint);
            paint.setColor(dividerColor);

            c.drawRect(new RectF(left, top, right, bottom), paint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, (int) dividerHeight);
    }
}
