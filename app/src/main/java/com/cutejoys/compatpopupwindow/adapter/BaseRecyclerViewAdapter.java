package com.cutejoys.compatpopupwindow.adapter;

import android.content.Context;
import android.database.Observable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create by XuChuanting
 * on 2018/8/31-18:29
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "BaseRecyclerViewAdapter";
    protected OnItemClickListener mOnItemClickListener;
    private ObservableSelectAll mObservableSelectAll = new ObservableSelectAll();
    private View mHeaderView = null;
    private int TYPE_HEADER = 0;
    private boolean selectedAll = false;
    private List<T> mDataList = null;

    public BaseRecyclerViewAdapter() {
        mDataList = new ArrayList<>();
    }

    public BaseRecyclerViewAdapter(@NonNull List<T> dataList) {
        mDataList = dataList;
    }

    public void registerSelectAllListener(OnSelectAllListener listener) {
        mObservableSelectAll.registerObserver(listener);
    }

    public void unregisterSelectAllListener(OnSelectAllListener listener) {
        mObservableSelectAll.unregisterObserver(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public Object getItem(int position) {
        return mDataList.get(position);
    }

    public boolean hasHeader() {
        return mHeaderView != null;
    }

    public void addHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (list != null) {
            mDataList.addAll(list);
        }
    }

    public void replaceAll(Collection<T> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeData(int positon) {
        mDataList.remove(positon);
        notifyItemRemoved(positon);
    }

    public void addData(int positon, T t) {
        mDataList.add(positon, t);
        notifyItemInserted(positon);
    }

    public void setSelectAll(boolean selectAll) {
        long l = System.currentTimeMillis();
        if (this.selectedAll && selectAll) {
            return;
        }
        for (T t : mDataList) {
            if (t instanceof Selectable) {
                ((Selectable) t).setSelected(selectAll);
            }
        }
        this.selectedAll = selectAll;
        notifyDataSetChanged();
        if (selectAll) {
            mObservableSelectAll.notifyAllSelected();
        } else {
            mObservableSelectAll.notifyNotAllSelected();
        }
    }

    /**
     * 获取全选状态，通知观察者
     */
    public void obtainSelectAllState() {

        boolean selectAll = true;
        for (T t : mDataList) {
            if (t instanceof Selectable) {
                if (!((Selectable) t).isSelected()) {
                    selectAll = false;
                    break;
                }
            }
        }
        if (selectAll) {
            mObservableSelectAll.notifyAllSelected();
        } else {
            mObservableSelectAll.notifyNotAllSelected();
        }
        this.selectedAll = selectAll;
    }

    public boolean isSelectedAll() {
        return selectedAll;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (hasHeader() && type == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView);
        }
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(getLayoutResByType(type), viewGroup, false);
        return createViewHolder(view);
    }

    protected abstract BaseViewHolder createViewHolder(View view);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (!hasHeader()) {
            onBindDataViewHolder(holder, position);
        } else {
            if (position != 0) {
                onBindDataViewHolder(holder, position - 1);
            }
        }
    }

    protected abstract void onBindDataViewHolder(BaseViewHolder baseViewHolder, int position);

    protected abstract @LayoutRes
    int getLayoutResByType(int type);

    @Override
    public int getItemViewType(int position) {
        if (hasHeader()) {
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return super.getItemViewType(position) + 1;
            }
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int size = mDataList.size();
        if (hasHeader()) {
            size += 1;
        }
        return size;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    private class ObservableSelectAll extends Observable<OnSelectAllListener> {
        synchronized void notifyAllSelected() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onAllSelected();
            }
        }

        synchronized void notifyNotAllSelected() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onNotAllSelected();
            }
        }
    }
}
