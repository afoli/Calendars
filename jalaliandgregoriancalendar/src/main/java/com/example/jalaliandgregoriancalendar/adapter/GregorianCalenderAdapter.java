package com.example.jalaliandgregoriancalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jalaliandgregoriancalendar.R;
import com.example.jalaliandgregoriancalendar.customview.CalItem;
import com.example.jalaliandgregoriancalendar.interfaces.OnRecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by A.Falah on 8/20/2017.
 */

public class GregorianCalenderAdapter extends
        RecyclerView.Adapter<GregorianCalenderAdapter.MyViewHolder> {

    private static final String TAG = "GregorianCalenderAdapte";

    private Context context;
    private OnRecyclerItemClickListener itemClickListener;

    private List<CalItem> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final int[] monthsINT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int month, year;
    private int daysInMonth, prevMonthDays;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private HashMap eventsPerMonthMap;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private int statusMonth;
    private int typeOfSelect; // 0: single,1: multi

    private int nextDaysColorGregorianItem;
    private int previewDaysColorGregorianItem;
    private int otherMonthDaysColorGregorianItem;
    private String fontGregorianItem;
    private int sizeGregorianItem;
    private Drawable backgroundDrawableGregorianItem;
    private Drawable backgroundSelectedDrawableGregorianItem;
    private int backgroundColorGregorianItem;
    private int backgroundSelectedColorGregorianItem;
    private int textSelectedColorGregorianItem;
    private int textSelectedSizeGregorianItem;

    public GregorianCalenderAdapter(Context context,
                                    int month,
                                    int year,
                                    int statusMonth,
                                    int nextDaysColorGregorianItem,
                                    int previewDaysColorGregorianItem,
                                    int otherMonthDaysColorGregorianItem,
                                    Drawable backgroundDrawableGregorianItem,
                                    Drawable backgroundSelectedDrawableGregorianItem,
                                    int backgroundColorGregorianItem,
                                    int backgroundSelectedColorGregorianItem,
                                    int textSelectedColorGregorianItem,
                                    String fontGregorianItem,
                                    int sizeGregorianItem,
                                    int textSelectedSizeGregorianItem,
                                    OnRecyclerItemClickListener itemClickListener) {
        this.context = context;
        this.list = new ArrayList<>();
        this.month = month;
        this.year = year;
        this.statusMonth = statusMonth;
        this.nextDaysColorGregorianItem = nextDaysColorGregorianItem;
        this.previewDaysColorGregorianItem = previewDaysColorGregorianItem;
        this.otherMonthDaysColorGregorianItem = otherMonthDaysColorGregorianItem;
        this.backgroundDrawableGregorianItem = backgroundDrawableGregorianItem;
        this.backgroundSelectedDrawableGregorianItem = backgroundSelectedDrawableGregorianItem;
        this.backgroundColorGregorianItem = backgroundColorGregorianItem;
        this.backgroundSelectedColorGregorianItem = backgroundSelectedColorGregorianItem;
        this.textSelectedColorGregorianItem = textSelectedColorGregorianItem;
        this.fontGregorianItem = fontGregorianItem;
        this.sizeGregorianItem = sizeGregorianItem;
        this.textSelectedSizeGregorianItem = textSelectedSizeGregorianItem;
        this.itemClickListener = itemClickListener;

        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        // Print Month
        printMonth(month, year);

        // Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    @Override
    public GregorianCalenderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_gridcell, parent, false));
    }

    @Override
    public void onBindViewHolder(final GregorianCalenderAdapter.MyViewHolder holder, final int position) {
        // ACCOUNT FOR SPACING
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

        // Set the Day GridCell
        holder.gridcell.setText(theday);
        holder.gridcell.setTag(theday + "-" + themonth + "-" + theyear);

        switch (statusMonth) {
            case -1: // pre month

                if (day_color[1].equals("GREY")) {
                    if (otherMonthDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                }
                if (day_color[1].equals("WHITE")) {
                    if (previewDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(previewDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.GRAY);
                    }
                }
                if (day_color[1].equals("BLUE")) {
                    if (previewDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(previewDaysColorGregorianItem);
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
                    if (otherMonthDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                }
                if (day_color[1].equals("WHITE")) {
                    if (nextDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    if (list.get(position).getStatus() == 0) {
                        if (previewDaysColorGregorianItem != 0) {
                            holder.gridcell.setTextColor(previewDaysColorGregorianItem);
                        } else {
                            holder.gridcell.setTextColor(Color.GRAY);
                        }
                    }

                }
                if (day_color[1].equals("BLUE")) {
                    if (nextDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }

                    holder.gridcell.setBackgroundResource(R.drawable.selected_items);
                }

                break;
            case 1: // next month

                if (day_color[1].equals("GREY")) {
                    if (otherMonthDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(otherMonthDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.TRANSPARENT);
                    }
                    holder.gridcell.setEnabled(false);
                }
                if (day_color[1].equals("WHITE")) {
                    if (nextDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    holder.gridcell.setEnabled(true);
                }
                if (day_color[1].equals("BLUE")) {
                    if (nextDaysColorGregorianItem != 0) {
                        holder.gridcell.setTextColor(nextDaysColorGregorianItem);
                    } else {
                        holder.gridcell.setTextColor(Color.BLACK);
                    }
                    holder.gridcell.setEnabled(true);
                }
                break;
        }

        if (list.get(position).isClick()) {
            if (backgroundSelectedDrawableGregorianItem != null) {
                holder.gridcell.setBackground(backgroundSelectedDrawableGregorianItem);
            } else if (backgroundSelectedColorGregorianItem == 0) {
                holder.gridcell.setBackgroundResource(R.drawable.selected_items);
            }

            if (backgroundSelectedColorGregorianItem != 0) {
                holder.gridcell.setBackgroundColor(backgroundSelectedColorGregorianItem);
            } else if (backgroundSelectedDrawableGregorianItem == null) {
                holder.gridcell.setBackgroundResource(R.drawable.selected_items);
            }

            if (textSelectedColorGregorianItem != 0) {
                holder.gridcell.setTextColor(textSelectedColorGregorianItem);
            } else {
                holder.gridcell.setTextColor(Color.TRANSPARENT);
            }

            if (textSelectedSizeGregorianItem != 0) {
                holder.gridcell.setTextSize(textSelectedSizeGregorianItem);
            }
        } else {
            if (backgroundDrawableGregorianItem != null) {
                holder.gridcell.setBackground(backgroundDrawableGregorianItem);
            } else if (backgroundColorGregorianItem != 0) {
                holder.gridcell.setBackgroundColor(backgroundColorGregorianItem);
            } else {
                holder.gridcell.setBackgroundResource(Color.TRANSPARENT);
            }

        }

        // FIXME: 8/21/2017 send to Gregorian adapter
        if (fontGregorianItem != null) {
            String fnt = fontGregorianItem + ".ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets()
                    , fnt);
            holder.gridcell.setTypeface(tf);
        }

        if (sizeGregorianItem != 0) {
            holder.gridcell.setTextSize(sizeGregorianItem);
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
        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

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
        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
            ++daysInMonth;
        }

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            list.add(new CalItem(
                    String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
                            + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear
                    , R.drawable.selected_items
            ));
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);

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
