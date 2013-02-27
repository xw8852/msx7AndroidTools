package com.msx7.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listviewtemplate.R;

public class PageFooter {
    protected ListView mListView;
    protected PageLoader mPageLoader;
    protected View mFootview;
    protected View mLoadingView;
    protected TextView mClickView;

    protected Page mPage;
    private int preCount;

    public PageFooter(ListView lisview, PageLoader pageLoader, Page page) {
        super();
        mPage = page;
        this.mListView = lisview;
        this.mPageLoader = pageLoader;
        mFootview = LayoutInflater.from(mListView.getContext()).inflate(R.layout.push_list_foot, null);
        mLoadingView = mFootview.findViewById(R.id.lin1);
        mClickView = (TextView) mFootview.findViewById(R.id.textView2);
        mClickView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPageLoader != null) {
                    if (mListView.getAdapter() != null) {
                        preCount = mListView.getAdapter().getCount();
                    }
                    mPageLoader.loadPage(mPage.getNextPage());
                    mClickView.setVisibility(View.GONE);
                    mLoadingView.setVisibility(View.VISIBLE);

                }
            }
        });
        lisview.addFooterView(mFootview, null, false);
    }

    public void onPageLoaderFinish() {
        int curCount = 0;
        if (mListView.getAdapter() != null) {
            curCount = mListView.getAdapter().getCount();
        }
        if (curCount <= preCount) {
            mPage.mCurPage--;
        }
        mClickView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        if (curCount <= preCount||curCount % mPage.mAVECount > 0) {
            showComplete();
        }
    }

    public void reset() {
        mPage.mCurPage = mPage.mStartPage;
        mClickView.setText("点击加载更多");
        mClickView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPageLoader != null) {
                    if (mListView.getAdapter() != null) {
                        preCount = mListView.getAdapter().getCount();
                    }
                    mPageLoader.loadPage(mPage.getNextPage());
                    mClickView.setVisibility(View.GONE);
                    mLoadingView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void showComplete() {
        mClickView.setOnClickListener(null);
        mClickView.setText("加载完毕");
    }

    public Page getPage() {
        return mPage;
    }

    public static interface PageLoader {
        public void loadPage(int pageNumber);
    }

    public static class Page {
        /**
         * 当前页
         */
        public int mCurPage;
        /**
         * 每页加载数
         */
        public int mAVECount;
        /**
         * 起始页，默认为0
         */
        public int mStartPage;
        /**
         * 总页数，如未知，默认为-1；
         */
        public int mCountPage = -1;

        public Page(int mAVECount, int mStartPage) {
            this(mAVECount, mStartPage, -1);
        }

        public Page(int mAVECount, int mStartPage, int mCountPage) {
            super();
            this.mAVECount = mAVECount;
            this.mStartPage = mStartPage;
            this.mCountPage = mCountPage;
            this.mCurPage = mStartPage;
        }

        public int getNextPage() {
            if (mCurPage == mCountPage && mCountPage > 0) {
                return -1;
            }
            return Math.max(mCurPage++, mStartPage);
        }

    }
}
