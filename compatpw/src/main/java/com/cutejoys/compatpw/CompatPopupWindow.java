package com.cutejoys.compatpw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * @author XuChuanting
 * on 2018/11/19-19:46
 */
public class CompatPopupWindow extends PopupWindow {
    private int[] location = new int[2];
    private int minWidowHeight = -1;
    private boolean useAnimStyle = true;

    public CompatPopupWindow(Context context) {
        super(context);
    }

    public CompatPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CompatPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public CompatPopupWindow() {
        super(null, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public CompatPopupWindow(View contentView) {
        super(null, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    public CompatPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public boolean isUseAnimStyle() {
        return useAnimStyle;
    }

    public void setUseAnimStyle(boolean useAnimStyle) {
        this.useAnimStyle = useAnimStyle;
    }

    public int getMinWidowHeight() {
        return minWidowHeight;
    }

    public void setMinWidowHeight(int minWidowHeight) {
        this.minWidowHeight = minWidowHeight;
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        int adjustYoff = adjustYoff(anchor, yoff);
        super.showAsDropDown(anchor, xoff, adjustYoff, gravity);
    }

    private int adjustYoff(View anchor, int yoff) {
        if (anchor == null) {
            return 0;
        }
        anchor.getLocationInWindow(location);
        int availableHeight = UtilsSize.getScreenHeight(anchor.getContext()) - location[1] - anchor.getHeight();
        int minWidowHeight = getMinWidowHeight();
        if (availableHeight < minWidowHeight && minWidowHeight != -1) {
            yoff -= minWidowHeight;
            if (useAnimStyle) {
                setAnimationStyle(R.style.AnimStyle_Bottom);
            }
        } else {
            if (useAnimStyle) {
                setAnimationStyle(R.style.AnimStyle);
            }
        }
        return yoff;
    }

}
