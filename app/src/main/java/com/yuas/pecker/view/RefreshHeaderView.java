package com.yuas.pecker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.yuas.pecker.R;

public class RefreshHeaderView extends TextView implements SwipeRefreshTrigger, SwipeTrigger {

    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
        // setText("REFRESHING");
        setText(getResources().getString(R.string.load_more));
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                //    setText("RELEASE TO REFRESH");
            } else {
                //    setText("SWIPE TO REFRESH");
            }
        } else {
            //  setText("REFRESH RETURNING");
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        // setText("COMPLETE");
        setText(getResources().getString(R.string.load_complete));
    }

    @Override
    public void onReset() {
        setText("");
    }
}
