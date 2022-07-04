package com.app.baseproject.utils.customView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by test(test@gmail.com) on 11/11/16.
 */

public class CustomRecyclerView extends RecyclerView {

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private GridSpacingItemDecoration mGridSpacingItemDecoration;
    private EqualSpacingItemDecoration mEqualSpacingItemDecoration;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUpAsList() {
        setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLinearLayoutManager);
    }

    public void setUpAsListNotFixed() {
        setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLinearLayoutManager);
    }

    public void setUpAsHorizontal() {
        setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext(),HORIZONTAL,false);
        setLayoutManager(mLinearLayoutManager);
    }

    public void setUpItemDecorationVertical(int spacing) {
        setHasFixedSize(false);
        mEqualSpacingItemDecoration = new EqualSpacingItemDecoration(getContext(), spacing, EqualSpacingItemDecoration.VERTICAL);
        addItemDecoration(mEqualSpacingItemDecoration);
    }

    public void setUpAsHorizontalNotFixed() {
        setHasFixedSize(false);
        mLinearLayoutManager = new LinearLayoutManager(getContext(),HORIZONTAL,false);
        setLayoutManager(mLinearLayoutManager);
    }

    public void setUpItemDecorationHorizontal(int spacing) {
        setHasFixedSize(true);
        mEqualSpacingItemDecoration = new EqualSpacingItemDecoration(getContext(), spacing, EqualSpacingItemDecoration.HORIZONTAL);
        addItemDecoration(mEqualSpacingItemDecoration);
    }

    public void setUpItemDecorationHorizontalSide(int spacing) {
        setHasFixedSize(true);
        mEqualSpacingItemDecoration = new EqualSpacingItemDecoration(getContext(), spacing, EqualSpacingItemDecoration.HORIZONTAL_SIDE);
        addItemDecoration(mEqualSpacingItemDecoration);
    }

    public void setUpAsGrid(int spanCount) {
        setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        setLayoutManager(mGridLayoutManager);
    }

    public void setUpItemDecoration(int spanCount, int spacing, boolean resevseItem) {
        setHasFixedSize(true);
        mGridSpacingItemDecoration = new GridSpacingItemDecoration(getContext(), spanCount,spacing,resevseItem);
        addItemDecoration(mGridSpacingItemDecoration);
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    public GridLayoutManager getGridLayoutManager() {
        return mGridLayoutManager;
    }
}
