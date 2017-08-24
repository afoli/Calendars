package com.example.jalaliandgregoriancalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jalaliandgregoriancalendar.R;
import com.example.jalaliandgregoriancalendar.customview.CalItem;
import com.example.jalaliandgregoriancalendar.customview.JalaliCalendar;
import com.example.jalaliandgregoriancalendar.interfaces.OnRecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by A.Falah on 8/20/2017.
 */

public class JalaliCalenderAdapter extends
        RecyclerView.Adapter<JalaliCalenderAdapter.MyViewHolder> {

    private Context context;
    private OnRecyclerItemClickListener itemClickListener;

    private List<CalItem> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    private final int[] monthsINT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private final int[] daysOfMonth = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
    private int month, year;
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private HashMap eventsPerMonthMap;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private int statusMonth;

    private int nextDaysColorJalaliItem;
    private int previewDaysColorJalaliItem;
    private int otherMonthDaysColorJalaliItem;
    private String fontJalaliItem;
    private int sizeJalaliItem;
    private Drawable backgroundDrawableJalaliItem;
    private Drawable backgroundSelectedDrawableJalaliItem;
    private int backgroundColorJalaliItem;
    private int backgroundSelectedColorJalaliItem;
    private int textSelectedColorJalaliItem;
    private int textSelectedSizeJalaliItem;

    public JalaliCalenderAdapter(Context context,
                                 int month,
                                 int year,
                                 int statusMonth,
                                 int nextDaysColorJalaliItem,
                                 int previewDaysColorJalaliItem,
                                 int otherMonthDaysColorJalaliItem,
                                 Drawable backgroundDrawableJalaliItem,
                                 Drawable backgroundSelectedDrawableJalaliItem,
                                 int backgroundColorJalaliItem,
                                 int backgroundSelectedColorJalaliItem,
                                 int textSelectedColorJalaliItem,
                                 String fontJalaliItem,
                                 int sizeJalaliItem,
                                 int textSelectedSizeJalaliItem,
                                 OnRecyclerItemClickListener itemClickListener) {

        this.context = context;
        this.list = new ArrayList<>();
        this.month = month;
        this.year = year;
        this.statusMonth = statusMonth;
        this.nextDaysColorJalaliItem = nextDaysColorJalaliItem;
        this.previewDaysColorJalaliItem = previewDaysColorJalaliItem;
        this.otherMonthDaysColorJalaliItem = otherMonthDaysColorJalaliItem;
        this.backgroundDrawableJalaliItem = backgroundDrawableJalaliItem;
        this.backgroundSelectedDrawableJalaliItem = backgroundSelectedDrawableJalaliItem;
        this.backgroundColorJalaliItem = backgroundColorJalaliItem;
        this.backgroundSelectedColorJalaliItem = backgroundSelectedColorJalaliItem;
        this.textSelectedColorJalaliItem = textSelectedColorJalaliItem;
        this.fontJalaliItem = fontJalaliItem;
        this.sizeJalaliItem = sizeJalaliItem;
        this.textSelectedSizeJalaliItem = textSelectedSizeJalaliItem;
        this.itemClickListener = itemClickListener;

        JalaliCalendar calendar = new JalaliCalendar();
        setCurrentDayOfMonth(calendar.get(JalaliCalendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(JalaliCalendar.DAY_OF_WEEK));
        // Print Month
        printMonth(month, year);

        // Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    @Override
    public JalaliCalenderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_gridcell, parent, false));
    }

    @Override
    public void onBindViewHolder(JalaliCalenderAdapter.MyViewHolder holder, int position) {

        String[] day_color = list.get(position).getDay().split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
            if (eventsPerMonthMap.containsKey(theday)) {
                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                holder.num_events_per_day.setText(numEvents.toString());
            }
        }

        holder.gridcell.setText(theday);
        holder.gridcell.setTag(theday + "-" + themonth + "-" + theyear);

        switch (statusMonth) {
            case -1: // pre month

                if (day_color[1].equals("GREY")) {
                    if (otherMonthDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                }
                if (day_color[1].equals("WHITE")) {
                    if (previewDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(previewDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.GRAY);
                    }
                }
                if (day_color[1].equals("BLUE")) {
                    if (previewDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(previewDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.GRAY);
                    }
                }
                holder.gridcell.setEnabled(false);

                break;
            case 0: // curent month

                switch (list.get(position).getStatus()) {
                    case 0:
                        holder.gridcell.setEnabled(false);
                        break;
                    case 1:
                        holder.gridcell.setEnabled(true);
                        break;
                }
                if (day_color[1].equals("GREY")) {
                    if (otherMonthDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                }
                if (day_color[1].equals("WHITE")) {
                    if (nextDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    if (list.get(position).getStatus() == 0) {
                        if (previewDaysColorJalaliItem != 0) {
                            holder.gridcell.setTextColor(previewDaysColorJalaliItem);
                        } else {
                            holder.gridcell.setTextColor(Color.GRAY);
                        }
                    }
                }
                if (day_color[1].equals("BLUE")) {
                    if (nextDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }

                    holder.gridcell.setBackgroundResource(R.drawable.selected_items);
                }
                break;
            case 1: // next month

                if (day_color[1].equals("GREY")) {
                    if (otherMonthDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                    holder.gridcell.setEnabled(false);
                }
                if (day_color[1].equals("WHITE")) {
                    if (nextDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    holder.gridcell.setEnabled(true);
                }
                if (day_color[1].equals("BLUE")) {
                    if (nextDaysColorJalaliItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorJalaliItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    holder.gridcell.setEnabled(true);
                }
                break;
        }

        if (list.get(position).isClick()) {
            if (backgroundSelectedDrawableJalaliItem != null) {
                holder.gridcell.setBackground(backgroundSelectedDrawableJalaliItem);
            } else if (backgroundSelectedColorJalaliItem == 0) {
                holder.gridcell.setBackgroundResource(R.drawable.selected_items);
            }

            if (backgroundSelectedColorJalaliItem != 0) {
                holder.gridcell.setBackgroundColor(backgroundSelectedColorJalaliItem);
            } else if (backgroundSelectedDrawableJalaliItem == null){
                holder.gridcell.setBackgroundResource(R.drawable.selected_items);
            }

            if (textSelectedColorJalaliItem != 0) {
                holder.gridcell.setTextColor(Color.TRANSPARENT);
            }

            if (textSelectedSizeJalaliItem != 0) {
                holder.gridcell.setTextSize(textSelectedSizeJalaliItem);
            }

        } else {
            if (backgroundDrawableJalaliItem != null) {
                holder.gridcell.setBackground(backgroundDrawableJalaliItem);
            } else if (backgroundColorJalaliItem != 0) {
                holder.gridcell.setBackgroundColor(backgroundColorJalaliItem);
            } else {
                holder.gridcell.setBackgroundResource(Color.TRANSPARENT);
            }

        }

        // FIXME: 8/21/2017 send to Jalali adapter
        if (fontJalaliItem != null) {
            String fnt = fontJalaliItem + ".ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets()
                    , fnt);
            holder.gridcell.setTypeface(tf);
        }

        if (sizeJalaliItem != 0) {
            holder.gridcell.setTextSize(sizeJalaliItem);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button gridcell;
        TextView num_events_per_day;

        public MyViewHolder(View itemView) {
            super(itemView);
            num_events_per_day = (TextView) itemView.findViewById(R.id.num_events_per_day);
            gridcell = (Button) itemView.findViewById(R.id.calendar_day_gridcell);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "iransansmobilemedium.ttf");
            gridcell.setTypeface(tf);
            gridcell.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), list);
        }
    }


    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    private void printMonth(int mm, int yy) {
        int dd = 0;
        // The number of days to leave blank at
        // the start of this month.
        int trailingSpaces = 0;
        int leadSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        JalaliCalendar cal = new JalaliCalendar(yy, currentMonth, 1);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        // Compute how much to leave before before the first day of the
        // month.
        // getDay() returns 0 for Sunday.
        int currentWeekDay = cal.get(JalaliCalendar.DAY_OF_WEEK);
        trailingSpaces = currentWeekDay;

        if (cal.isLeepYear(cal.get(JalaliCalendar.YEAR)) && mm == 12) {
            ++daysInMonth;
        }

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            list.add(new CalItem(
                    String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
                            + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear
                    , R.drawable.selected_items
            ));

            if (trailingSpaces >= 7) {
                list.clear();
            }
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            if (i < getCurrentDayOfMonth()) {
                list.add(new CalItem(
                        String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy
                        , R.drawable.selected_items
                        ,/*status disable*/0
                ));
            } else if (i == getCurrentDayOfMonth()) {
                list.add(new CalItem(
                        String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy
                        , R.drawable.selected_items
                        ,/*status enable*/1
                ));
            } else {
                list.add(new CalItem(
                        String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy
                        , R.drawable.selected_items
                        ,/*status disable*/1
                ));
            }
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            list.add(new CalItem(
                    String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear
                    , R.drawable.selected_items
            ));
        }
    }

    private HashMap findNumberOfEventsPerMonth(int year, int month) {
        HashMap map = new HashMap<>();
        return map;
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

}

