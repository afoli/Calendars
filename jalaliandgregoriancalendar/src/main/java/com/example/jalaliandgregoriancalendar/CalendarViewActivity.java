package com.example.jalaliandgregoriancalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jalaliandgregoriancalendar.adapter.GregorianCalenderAdapter;
import com.example.jalaliandgregoriancalendar.adapter.JalaliCalenderAdapter;
import com.example.jalaliandgregoriancalendar.customview.CalItem;
import com.example.jalaliandgregoriancalendar.customview.DividerItemDecoration;
import com.example.jalaliandgregoriancalendar.customview.GridViewItemDecoration;
import com.example.jalaliandgregoriancalendar.customview.JalaliCalendar;
import com.example.jalaliandgregoriancalendar.interfaces.OnRecyclerItemClickListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarViewActivity extends RelativeLayout implements View.OnClickListener {

    private TextView currentMonth;
    private TextView prevMonth;
    private TextView nextMonth;
    private RecyclerView calendarView;
    private View page;
    private Button btnJalali;
    private Button btnGregorian;

    private TextView titleTextGregorian1;
    private TextView titleTextGregorian2;
    private TextView titleTextGregorian3;
    private TextView titleTextGregorian4;
    private TextView titleTextGregorian5;
    private TextView titleTextGregorian6;
    private TextView titleTextGregorian7;

    private TextView titleTextJalali1;
    private TextView titleTextJalali2;
    private TextView titleTextJalali3;
    private TextView titleTextJalali4;
    private TextView titleTextJalali5;
    private TextView titleTextJalali6;
    private TextView titleTextJalali7;

    private GregorianCalenderAdapter adapter;
    private JalaliCalenderAdapter adapterPersian;
    private Calendar _calendar;
    private JalaliCalendar jalaliCalendar;
    private int month, year;
    private int statusMonth = 0;
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    private final String[] monthsName = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    private boolean isPersian = false;

    private int monthN;
    private int yearN;

    private View viewHeaderJalali;
    private View viewHeaderGregorian;

    private AttributeSet attrs;

    private int textColorGregorianTitle;//
    private String fontGregorianTitle;//
    private int sizeGregorianTitle;//

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

    private String fontJalaliTitle;//
    private int sizeJalaliTitle;//
    private int nextDaysColorJalaliTitle;//

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

    private int previewNextButtonTextColor;//
    private int previewNextButtonBackgroundColor;//
    private Drawable previewNextButtonBackgroundDrawable;//
    private String previewNextButtonFont;//
    private int previewNextButtonSize;//
    private int previewNextButtonWidth;// FIXME: 8/22/2017 dont work
    private int previewNextButtonHeight;// FIXME: 8/22/2017 dont work
    private String previewNextButtonText;//

    private int JalaliButtonTextColor;//
    private int JalaliButtonBackgroundColor;//
    private Drawable JalaliButtonBackgroundDrawable;//
    private String JalaliButtonFont;//
    private int JalaliButtonSize;//
    private int JalaliButtonWidth;// FIXME: 8/22/2017 dont work
    private int JalaliButtonHeight;// FIXME: 8/22/2017 dont work
    private String JalaliButtonText;//

    private int GregorianButtonTextColor;//
    private int GregorianButtonBackgroundColor;//
    private Drawable GregorianButtonBackgroundDrawable;//
    private String GregorianButtonFont;//
    private int GregorianButtonSize;//
    private int GregorianButtonWidth;// FIXME: 8/22/2017 dont work
    private int GregorianButtonHeight;// FIXME: 8/22/2017 dont work
    private String GregorianButtonText;//

    private int typeOfCalender;//

    private int holidaysColors;
    private int pageBackgroundColor;//
    private Drawable pageBackgroundDrawable;//
    private int calenderBackgroundColor;//
    private Drawable calenderBackgroundDrawable;

    private int horizontalLine;//
    private int verticalLine;//

    private int headerJalaliBackgroundColor;//
    private Drawable headerJalaliBackgroundDrawable;//
    private int headerJalaliTextColor;//
    private int headerJalaliTextSize;//

    private int headerGregorianBackgroundColor;//
    private Drawable headerGregorianBackgroundDrawable;//
    private int headerGregorianTextColor;//
    private int headerGregorianTextSize;//

    private int itemDecorationSize;//

    private int typeOfSelect; // 0: single,1: multi

    private void setAttrs(TypedArray a) {

        textColorGregorianTitle = a.getColor(R.styleable.calenders_textColorGregorianTitle, 0);
        fontGregorianTitle = a.getString(R.styleable.calenders_fontGregorianTitle);
        sizeGregorianTitle = a.getInteger(R.styleable.calenders_sizeGregorianTitle, 0);

        nextDaysColorGregorianItem = a.getColor(R.styleable.calenders_nextDaysColorGregorianItem, 0);
        previewDaysColorGregorianItem = a.getColor(R.styleable.calenders_previewDaysColorGregorianItem, 0);
        otherMonthDaysColorGregorianItem = a.getColor(R.styleable.calenders_otherMonthDaysColorGregorianItem, 0);
        fontGregorianItem = a.getString(R.styleable.calenders_fontGregorianItem);
        sizeGregorianItem = a.getInteger(R.styleable.calenders_sizeGregorianItem, 0);
        backgroundDrawableGregorianItem = a.getDrawable(R.styleable.calenders_backgroundDrawableGregorianItem);
        backgroundSelectedDrawableGregorianItem = a.getDrawable(R.styleable.calenders_backgroundSelectedDrawableGregorianItem);
        backgroundColorGregorianItem = a.getColor(R.styleable.calenders_backgroundColorGregorianItem, 0);
        backgroundSelectedColorGregorianItem = a.getColor(R.styleable.calenders_backgroundSelectedColorGregorianItem, 0);
        textSelectedColorGregorianItem = a.getColor(R.styleable.calenders_textSelectedColorGregorianItem, 0);
        textSelectedSizeGregorianItem = a.getInteger(R.styleable.calenders_textSelectedSizeGregorianItem, 14);

        //--------------------------------//
        nextDaysColorJalaliTitle = a.getColor(R.styleable.calenders_textColorJalaliTitle, 0);
        fontJalaliTitle = a.getString(R.styleable.calenders_fontJalaliTitle);
        sizeJalaliTitle = a.getInteger(R.styleable.calenders_sizeJalaliTitle, 0);

        nextDaysColorJalaliItem = a.getColor(R.styleable.calenders_nextDaysColorJalaliItem, 0);
        previewDaysColorJalaliItem = a.getColor(R.styleable.calenders_previewDaysColorJalaliItem, 0);
        otherMonthDaysColorJalaliItem = a.getColor(R.styleable.calenders_otherMonthDaysColorJalaliItem, 0);
        fontJalaliItem = a.getString(R.styleable.calenders_fontJalaliItem);
        sizeJalaliItem = a.getInteger(R.styleable.calenders_sizeJalaliItem, 0);
        backgroundDrawableJalaliItem = a.getDrawable(R.styleable.calenders_backgroundDrawableJalaliItem);
        backgroundSelectedDrawableJalaliItem = a.getDrawable(R.styleable.calenders_backgroundSelectedDrawableJalaliItem);
        backgroundColorJalaliItem = a.getColor(R.styleable.calenders_backgroundColorJalaliItem, 0);
        backgroundSelectedColorJalaliItem = a.getColor(R.styleable.calenders_backgroundSelectedColorJalaliItem, 0);
        textSelectedColorJalaliItem = a.getColor(R.styleable.calenders_textSelectedColorJalaliItem, 0);
        textSelectedSizeJalaliItem = a.getInteger(R.styleable.calenders_textSelectedSizeJalaliItem, 14);

        //--------------------------------//
        previewNextButtonTextColor = a.getColor(R.styleable.calenders_previewNextButtonTextColor, 0);
        previewNextButtonBackgroundColor = a.getColor(R.styleable.calenders_previewNextButtonBackgroundColor, 0);
        previewNextButtonBackgroundDrawable = a.getDrawable(R.styleable.calenders_previewNextButtonBackgroundDrawable);
        previewNextButtonFont = a.getString(R.styleable.calenders_previewNextButtonFont);
        previewNextButtonSize = a.getInteger(R.styleable.calenders_previewNextButtonSize, 0);
        previewNextButtonWidth = a.getInteger(R.styleable.calenders_previewNextButtonWidth, 0);
        previewNextButtonHeight = a.getInteger(R.styleable.calenders_previewNextButtonHeight, 0);
        previewNextButtonText = a.getString(R.styleable.calenders_previewNextButtonText);

        //--------------------------------//
        JalaliButtonTextColor = a.getColor(R.styleable.calenders_JalaliButtonTextColor, 0);
        JalaliButtonBackgroundColor = a.getColor(R.styleable.calenders_JalaliButtonBackgroundColor, 0);
        JalaliButtonBackgroundDrawable = a.getDrawable(R.styleable.calenders_JalaliButtonBackgroundDrawable);
        JalaliButtonFont = a.getString(R.styleable.calenders_JalaliButtonFont);
        JalaliButtonSize = a.getInteger(R.styleable.calenders_JalaliButtonSize, 0);
        JalaliButtonWidth = a.getInteger(R.styleable.calenders_JalaliButtonWidth, 0);
        JalaliButtonHeight = a.getInteger(R.styleable.calenders_JalaliButtonHeight, 0);
        JalaliButtonText = a.getString(R.styleable.calenders_JalaliButtonText);

        //--------------------------------//
        GregorianButtonTextColor = a.getColor(R.styleable.calenders_GregorianButtonTextColor, 0);
        GregorianButtonBackgroundColor = a.getColor(R.styleable.calenders_GregorianButtonBackgroundColor, 0);
        GregorianButtonBackgroundDrawable = a.getDrawable(R.styleable.calenders_GregorianButtonBackgroundDrawable);
        GregorianButtonFont = a.getString(R.styleable.calenders_GregorianButtonFont);
        GregorianButtonSize = a.getInteger(R.styleable.calenders_GregorianButtonSize, 0);
        GregorianButtonWidth = a.getInteger(R.styleable.calenders_GregorianButtonWidth, 0);
        GregorianButtonHeight = a.getInteger(R.styleable.calenders_GregorianButtonHeight, 0);
        GregorianButtonText = a.getString(R.styleable.calenders_GregorianButtonText);

        //--------------------------------//
        typeOfCalender = a.getInteger(R.styleable.calenders_typeOfCalender, 0);

        //--------------------------------//
        holidaysColors = a.getColor(R.styleable.calenders_holidaysColors, 0);
        pageBackgroundColor = a.getColor(R.styleable.calenders_pageBackgroundColor, 0);
        pageBackgroundDrawable = a.getDrawable(R.styleable.calenders_pageBackgroundDrawable);
        calenderBackgroundColor = a.getColor(R.styleable.calenders_calenderBackgroundColor, 0);
        calenderBackgroundDrawable = a.getDrawable(R.styleable.calenders_calenderBackgroundDrawable);

        //--------------------------------//
        horizontalLine = a.getInteger(R.styleable.calenders_horizontalLine, 1);
        verticalLine = a.getInteger(R.styleable.calenders_verticalLine, 1);

        //--------------------------------//
        headerJalaliBackgroundColor = a.getInteger(R.styleable.calenders_headerJalaliBackgroundColor, 0);
        headerJalaliTextColor = a.getColor(R.styleable.calenders_headerJalaliTextColor, 0);
        headerJalaliTextSize = a.getInteger(R.styleable.calenders_headerJalaliTextSize, 0);
        headerJalaliBackgroundDrawable = a.getDrawable(R.styleable.calenders_headerJalaliBackgroundDrawable);

        //--------------------------------//
        headerGregorianBackgroundColor = a.getInteger(R.styleable.calenders_headerGregorianBackgroundColor, 0);
        headerGregorianTextColor = a.getColor(R.styleable.calenders_headerGregorianTextColor, 0);
        headerGregorianTextSize = a.getInteger(R.styleable.calenders_headerGregorianTextSize, 0);
        headerGregorianBackgroundDrawable = a.getDrawable(R.styleable.calenders_headerGregorianBackgroundDrawable);

        //--------------------------------//
        typeOfSelect = a.getInteger(R.styleable.calenders_typeOfSelect, 0);
        itemDecorationSize = a.getInteger(R.styleable.calenders_itemDecorationSize, 8);

        a.recycle();

    }

    private void setAttrsForAllType() {
        //
        if (headerGregorianTextColor != 0) {
            titleTextGregorian1.setTextColor(headerGregorianTextColor);
            titleTextGregorian2.setTextColor(headerGregorianTextColor);
            titleTextGregorian3.setTextColor(headerGregorianTextColor);
            titleTextGregorian4.setTextColor(headerGregorianTextColor);
            titleTextGregorian5.setTextColor(headerGregorianTextColor);
            titleTextGregorian6.setTextColor(headerGregorianTextColor);
            titleTextGregorian7.setTextColor(headerGregorianTextColor);
        }

        if (headerJalaliTextColor != 0) {
            titleTextJalali1.setTextColor(headerJalaliTextColor);
            titleTextJalali2.setTextColor(headerJalaliTextColor);
            titleTextJalali3.setTextColor(headerJalaliTextColor);
            titleTextJalali4.setTextColor(headerJalaliTextColor);
            titleTextJalali5.setTextColor(headerJalaliTextColor);
            titleTextJalali6.setTextColor(headerJalaliTextColor);
            titleTextJalali7.setTextColor(headerJalaliTextColor);
        }

        //
        if (previewNextButtonTextColor != 0) {
            prevMonth.setTextColor(previewNextButtonTextColor);
            nextMonth.setTextColor(previewNextButtonTextColor);
        }

        if (previewNextButtonBackgroundColor != 0) {
            prevMonth.setBackgroundColor(previewNextButtonBackgroundColor);
            nextMonth.setBackgroundColor(previewNextButtonBackgroundColor);
        }

        if (previewNextButtonBackgroundDrawable != null) {
            prevMonth.setBackground(previewNextButtonBackgroundDrawable);
            nextMonth.setBackground(previewNextButtonBackgroundDrawable);
        }

        if (previewNextButtonFont != null) {
            String fnt = previewNextButtonFont + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            prevMonth.setTypeface(tf);
            nextMonth.setTypeface(tf);
        }

        if (previewNextButtonSize != 0) {
            prevMonth.setTextSize(previewNextButtonSize);
            nextMonth.setTextSize(previewNextButtonSize);
        }

        if (previewNextButtonWidth != 0) {
            prevMonth.setWidth(previewNextButtonWidth);
            nextMonth.setWidth(previewNextButtonWidth);
        }

        if (previewNextButtonHeight != 0) {
            prevMonth.setWidth(previewNextButtonHeight);
            nextMonth.setWidth(previewNextButtonHeight);
        }

        if (previewNextButtonText != null) {

            if (previewNextButtonBackgroundDrawable == null) {
                prevMonth.setBackground(null);
                nextMonth.setBackground(null);
            }

            prevMonth.setText(previewNextButtonText);
            nextMonth.setText(previewNextButtonText);
        }

        //jalali btn
        if (JalaliButtonTextColor != 0) {
            btnJalali.setTextColor(JalaliButtonTextColor);
        }

        if (JalaliButtonBackgroundColor != 0) {
            btnJalali.setBackgroundColor(JalaliButtonBackgroundColor);
        }

        if (JalaliButtonBackgroundDrawable != null) {
            btnJalali.setBackground(JalaliButtonBackgroundDrawable);
        }

        if (JalaliButtonFont != null) {
            String fnt = JalaliButtonFont + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            btnJalali.setTypeface(tf);
        }

        if (JalaliButtonSize != 0) {
            btnJalali.setTextSize(JalaliButtonSize);
        }

        if (JalaliButtonWidth != 0) {
            btnJalali.setWidth(JalaliButtonWidth);
        }

        if (JalaliButtonHeight != 0) {
            btnJalali.setHeight(JalaliButtonHeight);
        }

        if (JalaliButtonText != null) {
            btnJalali.setText(JalaliButtonText);
        }

        //Gregorian btn
        if (GregorianButtonTextColor != 0) {
            btnGregorian.setTextColor(GregorianButtonTextColor);
        }

        if (GregorianButtonBackgroundColor != 0) {
            btnGregorian.setBackgroundColor(GregorianButtonBackgroundColor);
        }

        if (GregorianButtonBackgroundDrawable != null) {
            btnGregorian.setBackground(GregorianButtonBackgroundDrawable);
        }

        if (GregorianButtonFont != null) {
            String fnt = GregorianButtonFont + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            btnGregorian.setTypeface(tf);
        }

        if (GregorianButtonSize != 0) {
            btnGregorian.setTextSize(GregorianButtonSize);
        }

        if (GregorianButtonWidth != 0) {
            btnGregorian.setWidth(GregorianButtonWidth);
        }

        if (GregorianButtonHeight != 0) {
            btnGregorian.setHeight(GregorianButtonHeight);
        }

        if (GregorianButtonText != null) {
            btnGregorian.setText(GregorianButtonText);
        }

        // FIXME: 8/22/2017 send to adapters
        if (holidaysColors != 0) {

        }

        if (pageBackgroundColor != 0) {
            page.setBackgroundColor(pageBackgroundColor);
        }

        if (pageBackgroundDrawable != null) {
            page.setBackground(pageBackgroundDrawable);
        }

        if (calenderBackgroundColor != 0) {
            calendarView.setBackgroundColor(calenderBackgroundColor);
        }

        if (calenderBackgroundDrawable != null) {
            calendarView.setBackground(calenderBackgroundDrawable);
        }

        //
        switch (horizontalLine) {
            case 0:
                calendarView.addItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.HORIZONTAL, Color.BLUE));
                break;
            case 1:
                calendarView.removeItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.HORIZONTAL, Color.BLUE));
                break;
            case 2:
                calendarView.removeItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.HORIZONTAL, Color.BLUE));
                break;
        }

        //
        switch (verticalLine) {
            case 0:
                calendarView.addItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.VERTICAL, Color.BLUE));
                break;
            case 1:
                calendarView.removeItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.VERTICAL, Color.BLUE));
                break;
            case 2:
                calendarView.removeItemDecoration(new DividerItemDecoration
                        (getContext(), LinearLayoutManager.VERTICAL, Color.BLUE));
                break;
        }

        if (itemDecorationSize != 8) {
            calendarView.addItemDecoration(new GridViewItemDecoration(itemDecorationSize));
        }
    }

    private void setValuesGregorian() {
        if (textColorGregorianTitle != 0) {
            currentMonth.setTextColor(textColorGregorianTitle);
        }

        if (fontGregorianTitle != null) {
            String fnt = fontGregorianTitle + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            currentMonth.setTypeface(tf);
        }

        if (sizeGregorianTitle != 0) {
            currentMonth.setTextSize(sizeGregorianTitle);
        }

        if (headerGregorianBackgroundColor != 0) {
            viewHeaderGregorian.setBackgroundColor(headerGregorianBackgroundColor);
        } else if (headerGregorianBackgroundDrawable == null) {
            viewHeaderGregorian.setBackgroundResource(R.drawable.back_cal_title);
        }

        if (headerGregorianBackgroundDrawable != null) {
            viewHeaderGregorian.setBackground(headerGregorianBackgroundDrawable);
        } else if (headerGregorianBackgroundColor == 0) {
            viewHeaderGregorian.setBackgroundResource(R.drawable.back_cal_title);
        }
    }

    private void setvaluesJalali() {
        if (nextDaysColorJalaliTitle != 0) {
            currentMonth.setTextColor(nextDaysColorJalaliTitle);
        }

        if (fontJalaliTitle != null) {
            String fnt = fontJalaliTitle + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            currentMonth.setTypeface(tf);
        }

        if (sizeJalaliTitle != 0) {
            currentMonth.setTextSize(sizeJalaliTitle);
        }

        if (headerJalaliBackgroundColor != 0) {
            viewHeaderJalali.setBackgroundColor(headerJalaliBackgroundColor);
        } else if (headerJalaliBackgroundDrawable == null) {
            viewHeaderJalali.setBackgroundResource(R.drawable.back_cal_title);
        }

        if (headerJalaliBackgroundDrawable != null) {
            viewHeaderJalali.setBackground(headerJalaliBackgroundDrawable);
        } else if (headerJalaliBackgroundColor == 0) {
            viewHeaderJalali.setBackgroundResource(R.drawable.back_cal_title);
        }
    }

    private void findViews() {
        btnJalali = (Button) findViewById(R.id.persian);
        btnGregorian = (Button) findViewById(R.id.english);
        prevMonth = (TextView) this.findViewById(R.id.prevMonth);
        currentMonth = (TextView) this.findViewById(R.id.currentMonth);
        nextMonth = (TextView) this.findViewById(R.id.nextMonth);
        calendarView = (RecyclerView) this.findViewById(R.id.calendar);
        page = this.findViewById(R.id.page);
        viewHeaderJalali = findViewById(R.id.title_jalali);
        viewHeaderGregorian = findViewById(R.id.title_gregorian);

        titleTextGregorian1 = (TextView) this.findViewById(R.id.title_gregorian_1);
        titleTextGregorian2 = (TextView) this.findViewById(R.id.title_gregorian_2);
        titleTextGregorian3 = (TextView) this.findViewById(R.id.title_gregorian_3);
        titleTextGregorian4 = (TextView) this.findViewById(R.id.title_gregorian_4);
        titleTextGregorian5 = (TextView) this.findViewById(R.id.title_gregorian_5);
        titleTextGregorian6 = (TextView) this.findViewById(R.id.title_gregorian_6);
        titleTextGregorian7 = (TextView) this.findViewById(R.id.title_gregorian_7);

        titleTextJalali1 = (TextView) this.findViewById(R.id.title_jalali_1);
        titleTextJalali2 = (TextView) this.findViewById(R.id.title_jalali_2);
        titleTextJalali3 = (TextView) this.findViewById(R.id.title_jalali_3);
        titleTextJalali4 = (TextView) this.findViewById(R.id.title_jalali_4);
        titleTextJalali5 = (TextView) this.findViewById(R.id.title_jalali_5);
        titleTextJalali6 = (TextView) this.findViewById(R.id.title_jalali_6);
        titleTextJalali7 = (TextView) this.findViewById(R.id.title_jalali_7);
    }

    public CalendarViewActivity(Context context) {
        super(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.calenders);
        setAttrs(a);

        initialize(context);
    }

    public CalendarViewActivity(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.calenders);
        setAttrs(a);

        initialize(context);
    }

    public CalendarViewActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.calenders);
        setAttrs(a);

        initialize(context);
    }

    private void initialize(final Context context) {
        inflate(context, R.layout.calendar_view, this);
        findViews();
        setAttrsForAllType();
        setGregorianCalender();

        if (fontGregorianTitle != null) {
            String fnt = fontGregorianTitle + ".ttf";
            Typeface tf = Typeface.createFromAsset(getContext().getAssets()
                    , fnt);
            currentMonth.setTypeface(tf);
        }

        switch (typeOfCalender) {
            case 0:
                btnGregorian.setVisibility(GONE);
                btnJalali.setVisibility(GONE);
                setGregorianCalender();
                break;
            case 1:
                btnGregorian.setVisibility(GONE);
                btnJalali.setVisibility(GONE);
                setJalaliCalender();
                break;
            case 2:
                btnGregorian.setVisibility(VISIBLE);
                btnJalali.setVisibility(VISIBLE);
                break;
        }

        prevMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        calendarView.setLayoutManager(new GridLayoutManager(context, 7));

        btnGregorian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setGregorianCalender();
            }
        });

        btnJalali.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setJalaliCalender();
            }
        });

    }

    /**
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        if (isPersian) {

            adapterPersian = new JalaliCalenderAdapter(
                    getContext(),
                    month,
                    year,
                    statusMonth,
                    nextDaysColorJalaliItem,
                    previewDaysColorJalaliItem,
                    otherMonthDaysColorJalaliItem,
                    backgroundDrawableJalaliItem,
                    backgroundSelectedDrawableJalaliItem,
                    backgroundColorJalaliItem,
                    backgroundSelectedColorJalaliItem,
                    textSelectedColorJalaliItem,
                    fontJalaliItem,
                    sizeJalaliItem,
                    textSelectedSizeJalaliItem,
                    new OnRecyclerItemClickListener() {
                        @Override
                        public void onClick(View v, int pos, List<CalItem> list) {
                            if (typeOfSelect == 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (i == pos) {
                                        if (list.get(pos).isClick()) {
                                            list.get(pos).setClick(false);
                                        } else {
                                            list.get(pos).setClick(true);
                                        }
                                    } else {
                                        list.get(i).setClick(false);
                                    }
                                }
                                adapterPersian.notifyDataSetChanged();
                            } else {
                                if (list.get(pos).isClick()) {
                                    list.get(pos).setClick(false);
                                } else {
                                    list.get(pos).setClick(true);
                                }
                                adapterPersian.notifyDataSetChanged();
                            }
                        }
                    });

            jalaliCalendar.set(year, month - 1, jalaliCalendar.get(JalaliCalendar.DAY_OF_MONTH));

            currentMonth.setText(monthsName[month - 1] + " " + year);//Persian
            calendarView.setAdapter(adapterPersian);
            calendarView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setVisibility(true, false);

        } else {

            adapter = new GregorianCalenderAdapter(
                    getContext(),
                    month,
                    year,
                    statusMonth,
                    nextDaysColorGregorianItem,
                    previewDaysColorGregorianItem,
                    otherMonthDaysColorGregorianItem,
                    backgroundDrawableGregorianItem,
                    backgroundSelectedDrawableGregorianItem,
                    backgroundColorGregorianItem,
                    backgroundSelectedColorGregorianItem,
                    textSelectedColorGregorianItem,
                    fontGregorianItem,
                    sizeGregorianItem,
                    textSelectedSizeGregorianItem,
                    new OnRecyclerItemClickListener() {
                        @Override
                        public void onClick(View v, int pos, List<CalItem> list) {
                            if (typeOfSelect == 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (i == pos) {
                                        if (list.get(pos).isClick()) {
                                            list.get(pos).setClick(false);
                                        } else {
                                            list.get(pos).setClick(true);
                                        }
                                    } else {
                                        list.get(i).setClick(false);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                if (list.get(pos).isClick()) {
                                    list.get(pos).setClick(false);
                                } else {
                                    list.get(pos).setClick(true);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
            _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));

            currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime())); //English

            calendarView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            calendarView.setAdapter(adapter);
            setVisibility(false, true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }

            if (isPersian) {
                jalaliCalendar = new JalaliCalendar(Locale.getDefault());
                monthN = jalaliCalendar.get(JalaliCalendar.MONTH) + 1;
                yearN = jalaliCalendar.get(JalaliCalendar.YEAR);
            } else {
                _calendar = Calendar.getInstance(Locale.getDefault());
                monthN = _calendar.get(Calendar.MONTH) + 1;
                yearN = _calendar.get(Calendar.YEAR);
            }

            if (yearN > year) {
                statusMonth = -1;
            } else if (yearN == year) {
                if (monthN == month) {
                    statusMonth = 0;
                } else if (monthN > month) {
                    statusMonth = -1;
                } else if (monthN < month) {
                    statusMonth = 1;
                }
            } else if (yearN < year) {
                statusMonth = 1;
            }

            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth) {
            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }

            if (isPersian) {
                jalaliCalendar = new JalaliCalendar(Locale.getDefault());
                monthN = jalaliCalendar.get(JalaliCalendar.MONTH) + 1;
                yearN = jalaliCalendar.get(JalaliCalendar.YEAR);
            } else {
                _calendar = Calendar.getInstance(Locale.getDefault());
                monthN = _calendar.get(Calendar.MONTH) + 1;
                yearN = _calendar.get(Calendar.YEAR);
            }

            if (yearN > year) {
                statusMonth = -1;
            } else if (yearN == year) {
                if (monthN == month) {
                    statusMonth = 0;
                } else if (monthN > month) {
                    statusMonth = -1;
                } else if (monthN < month) {
                    statusMonth = 1;
                }
            } else if (yearN < year) {
                statusMonth = 1;
            }

            setGridCellAdapterToDate(month, year);
        }
    }

    /**
     * for Items in Header
     */
    public void setVisibility(boolean jalali, boolean georg) {

        if (jalali) {
            viewHeaderJalali.setVisibility(View.VISIBLE);
        } else {
            viewHeaderJalali.setVisibility(View.GONE);
        }

        if (georg) {
            viewHeaderGregorian.setVisibility(View.VISIBLE);
        } else {
            viewHeaderGregorian.setVisibility(View.GONE);
        }
    }

    public void setCurrentMonthData() {

        if (isPersian) {
            jalaliCalendar = new JalaliCalendar(Locale.getDefault());
            monthN = jalaliCalendar.get(JalaliCalendar.MONTH) + 1;
            yearN = jalaliCalendar.get(JalaliCalendar.YEAR);
        } else {
            _calendar = Calendar.getInstance(Locale.getDefault());
            monthN = _calendar.get(Calendar.MONTH) + 1;
            yearN = _calendar.get(Calendar.YEAR);
        }

        if (yearN > year) {
            statusMonth = -1;
        } else if (yearN == year) {
            if (monthN == month) {
                statusMonth = 0;
            } else if (monthN > month) {
                statusMonth = -1;
            } else if (monthN < month) {
                statusMonth = 1;
            }
        } else if (yearN < year) {
            statusMonth = 1;
        }

        setGridCellAdapterToDate(month, year);
    }

    private void setGregorianCalender() {
        setValuesGregorian();

        isPersian = false;

        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        setCurrentMonthData();
        currentMonth.setText(dateFormatter.format(dateTemplate, _calendar.getTime())); //English

        adapter = new GregorianCalenderAdapter(
                getContext(),
                month,
                year,
                statusMonth,
                nextDaysColorGregorianItem,
                previewDaysColorGregorianItem,
                otherMonthDaysColorGregorianItem,
                backgroundDrawableGregorianItem,
                backgroundSelectedDrawableGregorianItem,
                backgroundColorGregorianItem,
                backgroundSelectedColorGregorianItem,
                textSelectedColorGregorianItem,
                fontGregorianItem,
                sizeGregorianItem,
                textSelectedSizeGregorianItem,
                new OnRecyclerItemClickListener() {
                    @Override
                    public void onClick(View v, int pos, List<CalItem> list) {

                        if (typeOfSelect == 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if (i == pos) {
                                    if (list.get(pos).isClick()) {
                                        list.get(pos).setClick(false);
                                    } else {
                                        list.get(pos).setClick(true);
                                    }
                                } else {
                                    list.get(i).setClick(false);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            if (list.get(pos).isClick()) {
                                list.get(pos).setClick(false);
                            } else {
                                list.get(pos).setClick(true);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        calendarView.setAdapter(adapter);

        calendarView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setVisibility(false, true);

    }

    private void setJalaliCalender() {
        setvaluesJalali();

        isPersian = true;

        jalaliCalendar = new JalaliCalendar(Locale.getDefault());
        month = jalaliCalendar.get(Calendar.MONTH) + 1;
        year = jalaliCalendar.get(Calendar.YEAR);

        setCurrentMonthData();
        currentMonth.setText(monthsName[month - 1] + " " + year);//Persian

        adapterPersian = new JalaliCalenderAdapter(
                getContext(),
                month,
                year,
                statusMonth,
                nextDaysColorJalaliItem,
                previewDaysColorJalaliItem,
                otherMonthDaysColorJalaliItem,
                backgroundDrawableJalaliItem,
                backgroundSelectedDrawableJalaliItem,
                backgroundColorJalaliItem,
                backgroundSelectedColorJalaliItem,
                textSelectedColorJalaliItem,
                fontJalaliItem,
                sizeJalaliItem,
                textSelectedSizeJalaliItem,
                new OnRecyclerItemClickListener() {
                    @Override
                    public void onClick(View v, int pos, List<CalItem> list) {
                        if (typeOfSelect == 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if (i == pos) {
                                    if (list.get(pos).isClick()) {
                                        list.get(pos).setClick(false);
                                    } else {
                                        list.get(pos).setClick(true);
                                    }
                                } else {
                                    list.get(i).setClick(false);
                                }
                            }
                            adapterPersian.notifyDataSetChanged();
                        } else {
                            if (list.get(pos).isClick()) {
                                list.get(pos).setClick(false);
                            } else {
                                list.get(pos).setClick(true);
                            }
                            adapterPersian.notifyDataSetChanged();
                        }
                    }
                });

        calendarView.setAdapter(adapterPersian);

        calendarView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setVisibility(true, false);
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
