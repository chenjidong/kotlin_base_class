package com.cjd.base.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cjd.base.utils.LogUtils;

/**
 * Created by chenjidong
 */
public class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener implements OnLoadMoreListener {

    /**
     * layoutManager的类型（枚举）
     */
    protected LAYOUT_MANAGER_TYPE layoutManagerType = null;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions = null;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState;

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager)
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            else if (layoutManager instanceof LinearLayoutManager)
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            else if (layoutManager instanceof StaggeredGridLayoutManager)
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            else
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                (lastVisibleItemPosition) >= totalItemCount - 1)) {
            LogUtils.d("Loading More");
            loadMore();
        }
        if ((lastVisibleItemPosition) >= totalItemCount - 1)
            rollBottom();
    }

    public void rollBottom() {

    }

    @Override
    public void loadMore() {

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }
}
