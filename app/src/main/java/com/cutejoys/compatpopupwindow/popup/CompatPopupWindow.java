package com.cutejoys.compatpopupwindow.popup;

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

}
