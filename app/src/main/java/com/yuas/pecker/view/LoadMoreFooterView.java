package com.yuas.pecker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.yuas.pecker.R;


public class LoadMoreFooterView extends TextView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        //  setText("LOADING MORE");
        setText(getResources().getString(R.string.load_more));

    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
//                setText("RELEASE TO LOAD MORE");


            } else {
               // setText("SWIPE TO LOAD MORE");
               // setText("SWIPE TO LOAD MORE");
            }
        } else {
//            setText("LOAD MORE RETURNING");
        }
    }

    @Override
    public void onRelease() {
        // setText("LOADING MORE");
        setText(getResources().getString(R.string.load_more));
    }

    @Override
    public void onComplete() {
        //  setText("COMPLETE");
        setText(getResources().getString(R.string.load_complete));
    }

    @Override
    public void onReset() {
        setText("");
    }
}
