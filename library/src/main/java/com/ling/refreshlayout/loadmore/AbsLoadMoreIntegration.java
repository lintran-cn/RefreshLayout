package com.ling.refreshlayout.loadmore;

import android.view.View;

/**
 *
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public abstract class AbsLoadMoreIntegration<VT> {
    protected VT mContent;

    public AbsLoadMoreIntegration(VT content) {
        mContent = content;
    }

    public abstract void addLoadMoreView(View v);

    public abstract void removeLoadMoreView(View v);

    /**
     * 绑定滚动事件
     * 当滚动到最低端时调用{@link LoadMoreSupport#onReachBottom()}
     */
    public abstract void bindScroll(final LoadMoreSupport support);

    public VT getContent() {
        return mContent;
    }
}
