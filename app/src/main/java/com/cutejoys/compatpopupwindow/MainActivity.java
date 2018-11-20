package com.cutejoys.compatpopupwindow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cutejoys.compatpopupwindow.adapter.BaseRecyclerViewAdapter;
import com.cutejoys.compatpopupwindow.adapter.BaseViewHolder;
import com.cutejoys.compatpw.CompatPopupWindow;
import com.cutejoys.compatpw.UtilsSize;

public class MainActivity extends AppCompatActivity {

    private CompatPopupWindow mPopupWindow;
    private int mHeight;
    private int mAvailableHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyAdapter());

        CompatPopupWindow popupWindow = new CompatPopupWindow();
    }

    private void showPopWindow(final ImageView ivMore) {
        Context context = ivMore.getContext();
        mPopupWindow = new CompatPopupWindow();
        int width = UtilsSize.dpToPx(this,140);
        mPopupWindow.setWidth(width);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        final View contentView = LayoutInflater.from(context).inflate(R.layout.window_more, null);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_more_window));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setElevation(16);
        mPopupWindow.setMinWidowHeight(UtilsSize.dpToPx(this,200));
        mPopupWindow.showAsDropDown(ivMore, 0, 0, Gravity.END|Gravity.TOP);

    }

    private static final String TAG = "MainActivity";

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
