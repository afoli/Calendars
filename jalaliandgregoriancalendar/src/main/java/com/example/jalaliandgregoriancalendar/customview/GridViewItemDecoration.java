package com.example.jalaliandgregoriancalendar.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridViewItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GridViewItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;

        if (parent.getChildAdapterPosition(view) == 0
                || parent.getChildAdapterPosition(view) == 1
                || parent.getChildAdapterPosition(view) == 2
                || parent.getChildAdapterPosition(view) == 3
                || parent.getChildAdapterPosition(view) == 4
                || parent.getChildAdapterPosition(view) == 5
                || parent.getChildAdapterPosition(view) == 6) {
            outRect.top = space;
            outRect.bottom = space;
        } else {
            outRect.bottom = space;
        }

        if (parent.getChildAdapterPosition(view) % 7 == 0) {
            outRect.right = space;
            outRect.left = space/2;
        } else {
            outRect.left = space;
            outRect.right = space/2;
        }
    }
}