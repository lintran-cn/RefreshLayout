package com.ling.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;

import com.ling.refreshlayout.loadmore.LoadMoreSupport;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class RefreshLayout extends PtrFrameLayout {
    LoadMoreSupport mLoadMoreSupport;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }
}
