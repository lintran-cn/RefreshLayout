package com.ling.refreshlayout.loadmore;

import android.view.View;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public abstract class AbsLoadMoreHolder<VT> {
    protected LoadMoreSupport mLoadMoreSupport;

    protected VT mContent;

    public AbsLoadMoreHolder(VT content, LoadMoreSupport loadMoreSupport) {
        mContent = content;
    }

    public abstract void addLoadMoreView(View v);

    public abstract void removeLoadMoreView(View v);

    public abstract void bindScroll();

    public LoadMoreSupport getLoadMoreSupport() {
        return mLoadMoreSupport;
    }

    public VT getContent() {
        return mContent;
    }
}
