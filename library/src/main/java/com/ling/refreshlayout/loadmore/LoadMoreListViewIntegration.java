package com.ling.refreshlayout.loadmore;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class LoadMoreListViewIntegration extends AbsLoadMoreIntegration<ListView> {

    public LoadMoreListViewIntegration(ListView content) {
        super(content);
    }

    @Override
    public void addLoadMoreView(View v) {
        getContent().addFooterView(v);
    }

    @Override
    public void removeLoadMoreView(View v) {
        getContent().removeFooterView(v);
    }

    @Override
    public void bindScroll(final LoadMoreSupport support) {
        mContent.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

//                if (null != mOnScrollListener) {
//                    mOnScrollListener.onScrollStateChanged(view, scrollState);
//                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        support.onReachBottom();
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
