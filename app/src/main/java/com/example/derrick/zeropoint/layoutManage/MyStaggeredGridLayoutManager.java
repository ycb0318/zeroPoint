package com.example.derrick.zeropoint.layoutManage;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by derrick on 2018/8/14.
 */

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    private boolean isScrollEnabled = true;

    public MyStaggeredGridLayoutManager(int spanCount, int orientation, boolean isScrollEnabled) {
        super(spanCount, orientation);
        this.isScrollEnabled = isScrollEnabled;
    }


    @Override
    public boolean canScrollVertically() {
//        return super.canScrollVertically();
        return isScrollEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollHorizontally();
    }
}
