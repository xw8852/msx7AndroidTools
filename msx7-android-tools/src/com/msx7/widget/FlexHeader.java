package com.msx7.widget;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.listviewtemplate.R;

public class FlexHeader extends AbstractHeader {
    View view;
    private int height;

    public FlexHeader(AdapterView<?> adapterView, View view) {
        super(adapterView, false);
        this.view = view;
        measureView(view);
        height = view.getMeasuredHeight();
        mState = DONE;
        changeHeaderViewByState(mState);
    }

    public FlexHeader(AdapterView<?> adapterView) {
        super(adapterView, false);
        this.view = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.input, null);
        this.view.setBackgroundColor(Color.TRANSPARENT);
        height = view.getMeasuredHeight();
        mState = DONE;
        changeHeaderViewByState(mState);
    }

    @Override
    protected int getVisiableHeight() {
        return height;
    }

    @Override
    public View getHeader() {
        return view;
    }

    @Override
    protected void changeHeaderViewByState(int state) {
        switch (state) {
        case PULL_To_REFRESH:
        case RELEASE_To_REFRESH:
            break;
        case REFRESHING:
            mState = DONE;
            changeHeaderViewByState(mState);
            break;
        case DONE:
            getHeader().setPadding(0, -1 * getVisiableHeight(), 0, 0);
            break;
        }
    }

}
