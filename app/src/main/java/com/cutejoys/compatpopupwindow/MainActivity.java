package com.cutejoys.compatpopupwindow;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cutejoys.compatpopupwindow.adapter.BaseRecyclerViewAdapter;
import com.cutejoys.compatpopupwindow.adapter.BaseViewHolder;
import com.cutejoys.compatpopupwindow.popup.CompatPopupWindow;

public class MainActivity extends AppCompatActivity {

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());

        CompatPopupWindow popupWindow = new CompatPopupWindow();
    }

    private void showPopWindow(ImageView ivMore) {
        Context context = ivMore.getContext();
        mPopupWindow = new PopupWindow();
        int width = UtilsSize.dpToPx(this,140);
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(context).inflate(R.layout.window_more, null);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_more_window));
        mPopupWindow.setOutsideTouchable(true);
//        popupWindow.setTouchable(true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setElevation(16);
        int[] location = new int[2];
        ivMore.getLocationInWindow(location);

        int yoff = -ivMore.getMeasuredHeight() / 2;
        int availableHeight = UtilsSize.getScreenHeight(context) - location[1];
        int windowHeight = UtilsSize.dpToPx(context, 215);
        if (availableHeight < windowHeight) {
            yoff = availableHeight - windowHeight;
            mPopupWindow.setAnimationStyle(R.style.AnimStyle_Bottom);
        } else {
            mPopupWindow.setAnimationStyle(R.style.AnimStyle);
        }
        mPopupWindow.showAsDropDown(ivMore, -width + ivMore.getMeasuredWidth(), yoff);
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<String>{

        @Override
        protected ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        protected void onBindDataViewHolder(BaseViewHolder baseViewHolder, int position) {
            final ViewHolder viewHolder = (ViewHolder) baseViewHolder;

            viewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopWindow(viewHolder.ivMore);
                }
            });

        }

        @Override
        protected int getLayoutResByType(int type) {
            return R.layout.item_video_list;
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    static class ViewHolder extends BaseViewHolder{

        ImageView ivMore;
        public ViewHolder(View itemView) {
            super(itemView);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
