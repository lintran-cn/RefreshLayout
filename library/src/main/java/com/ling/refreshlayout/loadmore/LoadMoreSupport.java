package com.ling.refreshlayout.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class LoadMoreSupport {

    /**
     * 处理loadmore发出的事件
     */
    public interface LoadMoreHandler {

        /**
         * Check can do refresh or not. For example the content is empty or the first child is in view.
         */
        boolean checkCanDoLoadMore(final LoadMoreSupport loadMoreContainer, final View content);

        void onLoadMore(LoadMoreSupport loadMoreContainer);
    }

    /**
     * 自定义LoadMore时,实现该接口
     */
    public interface LoadMoreUIHandler {

        void onLoading(LoadMoreSupport container);

        void onLoadFinish(LoadMoreSupport container, boolean empty, boolean hasMore);

        void onWaitToLoadMore(LoadMoreSupport container);

        void onLoadError(LoadMoreSupport container, int errorCode, String errorMessage);
    }


    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;
    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;
    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;

    private View mFooterView;

    private AbsLoadMoreHolder mLoadMoreHolder;

    public LoadMoreSupport(View content) {
        mLoadMoreHolder = getLoadMoreView(content);

        init();
    }

    public LoadMoreSupport(AbsLoadMoreHolder loadMoreHolder) {
        mLoadMoreHolder = loadMoreHolder;

        init();
    }

    public void useDefaultFooter() {
//        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
//        footerView.setVisibility(View.GONE);
//        setLoadMoreHolder(footerView);
//        setLoadMoreUIHandler(footerView);
    }


    protected void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    protected AbsLoadMoreHolder getLoadMoreView(View content) {
        return new RecyclerViewLoadMoreHolder((RecyclerView) content, this);
    }

    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }
//
//    @Override
//    public void setOnScrollListener(AbsListView.OnScrollListener l) {
//        mOnScrollListener = l;
//    }

    public void setLoadMoreHolder(View view) {
        // has not been initialized
        mFooterView = view;

        // remove previous
        if (mFooterView != null && mFooterView != view) {
            mLoadMoreHolder.removeLoadMoreView(view);
        }

        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });

        mLoadMoreHolder.addLoadMoreView(view);
    }

    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * page has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    private void init() {

        if (mFooterView != null) {
            mLoadMoreHolder.addLoadMoreView(mFooterView);
        }


    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

}
