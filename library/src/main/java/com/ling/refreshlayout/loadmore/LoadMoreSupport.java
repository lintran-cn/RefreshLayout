package com.ling.refreshlayout.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

/**
 * Load More wrap.
 * add load more function to target view
 *
 * Created by lingquan(quan.ling@hotmail.com) on 15/10/31.
 */
public class LoadMoreSupport{

    /**
     * 处理loadmore的事件
     * 如在此方法里调用接口,加载数据
     */
    public interface LoadMoreHandler {

        /**
         * 检查是否加载下一页
         */
        boolean checkCanDoLoadMore(final LoadMoreSupport loadMoreContainer, final View content);

        /**
         * 触发加载下一页
         * 加载完成后需调用 {@link LoadMoreSupport#loadMoreFinish(boolean, boolean)}
         * or {@link LoadMoreSupport#loadMoreError(int, String)}
         */
        void onLoadMore(LoadMoreSupport loadMoreContainer);
    }

    /**
     * 处理loadmore状态改变时,变更相应UI
     * 自定义LoadMore时,实现该接口
     */
    public interface LoadMoreUIHandler {
        /**
         * 开始加载下一页
         * @param container
         */
        void onLoading(LoadMoreSupport container);

        /**
         * 下一页加载成功
         * @param container
         * @param empty
         * @param hasMore
         */
        void onLoadFinish(LoadMoreSupport container, boolean empty, boolean hasMore);

        /**
         * 等待加载下一页
         * @param container
         */
        void onWaitToLoadMore(LoadMoreSupport container);

        /**
         * 加载下一页发生错误
         * @param container
         * @param errorCode
         * @param errorMessage
         */
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

    private AbsLoadMoreIntegration mLoadMoreHolder;

    public LoadMoreSupport(View content) {
        mLoadMoreHolder = getLoadMoreView(content);

        init();
    }

    public LoadMoreSupport(AbsLoadMoreIntegration loadMoreHolder) {
        mLoadMoreHolder = loadMoreHolder;

        init();
    }

    public void useDefaultFooter() {
//        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
//        footerView.setVisibility(View.GONE);
//        setLoadMoreHolder(footerView);
//        setLoadMoreUIHandler(footerView);
    }

    /**
     * 由{@link AbsLoadMoreIntegration#bindScroll(LoadMoreSupport)}绑定的滚动事件中到达底部时触发
     */
    public void onReachBottom() {
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


    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    public void setLoadMoreView(View view) {
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

    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * 根据View类型,初始化不同的LoadMoreIntegration
     * @param content 目标View
     * @return
     */
    protected AbsLoadMoreIntegration getLoadMoreView(View content) {
        if (content instanceof AbsListView) {
            return new LoadMoreListViewIntegration((AbsListView)content);
        }else if (content instanceof RecyclerView) {
            return new LoadMoreRecyclerViewIntegration((RecyclerView) content);
        } else {
            return null;
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
