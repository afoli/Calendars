package com.example.jalaliandgregoriancalendar.interfaces;

import android.view.View;

import com.example.jalaliandgregoriancalendar.customview.CalItem;

import java.util.List;

public interface OnRecyclerItemClickListener {
    public void onClick(View v, int pos, List<CalItem> list);
}
