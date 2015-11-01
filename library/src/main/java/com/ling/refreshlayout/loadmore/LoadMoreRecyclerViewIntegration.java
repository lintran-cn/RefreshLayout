package com.ling.refreshlayout.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class LoadMoreRecyclerViewIntegration extends AbsLoadMoreIntegration<RecyclerView> {

    public LoadMoreRecyclerViewIntegration(RecyclerView content) {
        super(content);
    }

    @Override
    public void addLoadMoreView(View v) {

    }

    @Override
    public void removeLoadMoreView(View v) {

    }

    @Override
    public void bindScroll(LoadMoreSupport support) {
        getContent().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
