# CompatPopupWindow
popup window fix offset

- 优化PopupWindow在屏幕右边或下边的时候显示不全的问题.
- 提供一套AnimationStyle.

![效果图](https://github.com/XuNeverMore/CompatPopupWindow/blob/master/img/popup.gif)

  调用showAsDropDown时，如果anchor太靠下，可能空间不够，造成PopupWindow显示不全，所以要判断进行y方向偏移。靠右的画gravity可以用`Gravity.END|Gravity.Top`解决。
  
### 核心代码
```Java
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
```
