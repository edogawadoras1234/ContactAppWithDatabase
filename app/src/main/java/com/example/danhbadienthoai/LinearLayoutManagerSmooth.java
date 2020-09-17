package com.example.danhbadienthoai;

import android.content.Context;
import android.graphics.PointF;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutManagerSmooth extends LinearLayoutManager {
    public LinearLayoutManagerSmooth(Context context) {
        super(context);
    }

    public LinearLayoutManagerSmooth(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new TopSnapedSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);

    }

    private class TopSnapedSmoothScroller extends LinearSmoothScroller {

        public TopSnapedSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
          return LinearLayoutManagerSmooth.this.computeScrollVectorForPosition(targetPosition);

        }
    }
}
