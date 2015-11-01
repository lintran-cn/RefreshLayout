package com.ling.refreshlayout.loadmore;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class AbsListViewLoadMoreHolder extends AbsLoadMoreHolder<AbsListView> {

    public AbsListViewLoadMoreHolder(AbsListView content, LoadMoreSupport loadMoreSupport) {
        super(content, loadMoreSupport);
    }

    @Override
    public void addLoadMoreView(View v) {

    }

    @Override
    public void removeLoadMoreView(View v) {

    }

    @Override
    public void bindScroll() {
        mContent.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

//                if (null != mOnScrollListener) {
//                    mOnScrollListener.onScrollStateChanged(view, scrollState);
//                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        getLoadMoreSupport().onReachBottom();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (null != mOnScrollListener) {
//                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    mIsEnd = true;
                } else {
                    mIsEnd = false;
                }
            }
        });
    }
}
