package com.kasra.picker.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.kasra.picker.R;
import com.kasra.picker.customviews.DateRangeCalendarView;
import com.kasra.picker.utils.FontUtils;
import com.kasra.picker.utils.MyUtils;
import com.kasra.picker.utils.OnSwipeTouchListener;
import com.kasra.picker.utils.PersianCalendar;

public class DatePickerDialog extends Dialog {
    //region Fields
    private Context mContext;
    private DateRangeCalendarView calendar;
    private Button btn_Accept;
    private FrameLayout contentLayout,swipeItem;
    private AppCompatTextView toDateTxt;
    private LinearLayout toDateLinear;
    private AppCompatTextView fromDateTxt;
    private AppCompatTextView dateTxtfirst,dateTxtSecond;
    private PersianCalendar date, startDate, endDate;
    private Typeface typeface;
    //endregion

    public DatePickerDialog(Context context) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getWindow() != null) {
            getWindow().setGravity(Gravity.CENTER);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        this.typeface = FontUtils.Default(mContext);

        initView();

        PersianCalendar today = new PersianCalendar();
        setCurrentDate(today);
    }

    private void initView() {
        //region init View & Font
        setContentView(R.layout.dialog_date_picker);

        btn_Accept = findViewById(R.id.btn_Accept);
        toDateLinear = findViewById(R.id.to_date_linear);
        contentLayout = findViewById(R.id.content);
        swipeItem = findViewById(R.id.swipe_item);
        toDateTxt = findViewById(R.id.todate_txt);
        fromDateTxt = findViewById(R.id.date_txt);
        dateTxtfirst = findViewById(R.id.date_one_txt);
        dateTxtSecond = findViewById(R.id.date_two_txt);
        btn_Accept.setTypeface(typeface);
        toDateTxt.setTypeface(typeface);
        fromDateTxt.setTypeface(typeface);
        dateTxtfirst.setTypeface(typeface);
        dateTxtSecond.setTypeface(typeface);
        acceptButtonColor = ContextCompat.getColor(mContext, R.color.buttonBackgroundColor);
//        calendar = findViewById(R.id.calendar);
        //endregion
    }

    @SuppressLint("ClickableViewAccessibility")
    public void showDialog() {
        calendar = new DateRangeCalendarView(mContext);
        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onDateSelected(PersianCalendar _date) {
                date = _date;
                fromDateTxt.setText(date.getPersianShortDate());
            }

            @Override
            public void onDateRangeSelected(PersianCalendar _startDate, PersianCalendar _endDate) {
                startDate = _startDate;
                endDate = _endDate;
                if (selectionMode == DateRangeCalendarView.SelectionMode.Range) {
                    if (startDate != null)
                        fromDateTxt.setText(startDate.getPersianShortDate());
                    else {
                        fromDateTxt.setText(startDate.getPersianShortDate());
                    }
                    if (endDate != null)
                        toDateTxt.setText(endDate.getPersianShortDate());
                    else{
                        toDateTxt.setText(mContext.getResources().getString(R.string.selection_item));
                    }
                }

            }

            @Override
            public void onCancel() {

            }
        });

        btn_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionMode == DateRangeCalendarView.SelectionMode.Single) {
                    //region SelectionMode.Single
                    if (date != null) {
                        if (onSingleDateSelectedListener != null) {
                            onSingleDateSelectedListener.onSingleDateSelected(date);
                        }

                        dismiss();
                    } else {
                        MyUtils.getInstance().Toast(mContext, "لطفا یک تاریخ انتخاب کنید");
                    }
                    //endregion
                } else if (selectionMode == DateRangeCalendarView.SelectionMode.Range) {
                    //region SelectionMode.Range
                    if (startDate != null) {
                        if (endDate != null) {
                            if (onRangeDateSelectedListener != null) {
                                onRangeDateSelectedListener.onRangeDateSelected(startDate, endDate);

                            }

                            dismiss();
                        } else {
                            MyUtils.getInstance().Toast(mContext, "لطفا بازه زمانی را مشخص کنید");
                        }
                    } else {
                        MyUtils.getInstance().Toast(mContext, "لطفا بازه زمانی را مشخص کنید");
                    }
                    //endregion
                }
            }
        });

        //config
        calendar.setSelectionMode(selectionMode.getValue());
        calendar.setHolidayModeBg(holidayMode.getValue());
        calendar.setDisableDaysAgo(disableDaysAgo);
        calendar.setTypeface(typeface);
        calendar.setCurrentDate(currentDate);
        calendar.setMaxDate(maxDate);
        calendar.setMinDate(minDate);
        calendar.setShowGregorianDate(showGregorianDate);
        calendar.setShouldEnabledTime(enableTimePicker);

        //theme
        calendar.setSelectedDateCircleColor(selectedDateCircleColor);
        calendar.setWeekColor(weekColor);
        calendar.setRangeStripColor(rangeStripColor);
        calendar.setSelectedDateColor(selectedDateColor);
        calendar.setDefaultDateColor(defaultDateColor);
        calendar.setDisableDateColor(disableDateColor);
        calendar.setRangeDateColor(rangeDateColor);
        calendar.setTodayColor(todayColor);
        calendar.setTextSizeTitle(textSizeTitle);
        calendar.setTextSizeWeek(textSizeWeek);
        calendar.setTextSizeDate(textSizeDate);

        //init
        calendar.setAttributes();
        calendar.build();

        ViewGroup insertPoint = findViewById(R.id.content);


        if (insertPoint.getChildCount() > 0) {
            insertPoint.removeAllViews();
        }

        insertPoint.addView(calendar);


        if (selectionMode.getValue() == DateRangeCalendarView.SelectionMode.None.getValue()) {
            btn_Accept.setVisibility(View.GONE);
        }

        btn_Accept.setBackgroundColor(acceptButtonColor);
        if (selectionMode == DateRangeCalendarView.SelectionMode.Single) {
            toDateLinear.setVisibility(View.GONE);
            dateTxtfirst.setText("تاریخ انتخابی");
        }
        contentLayout.setOnTouchListener(new OnSwipeTouchListener(mContext) {
            public void onSwipeRight() {
                calendar.nextMonth();
            }

            public void onSwipeLeft() {
                calendar.prevMonth();
            }
        });
        this.show();
    }

    //region Properties
    //region DisableDaysAgo -> Default = True
    private boolean disableDaysAgo = true;

    public boolean isDisableDaysAgo() {
        return disableDaysAgo;
    }

    public void setDisableDaysAgo(boolean disableDaysAgo) {
        this.disableDaysAgo = disableDaysAgo;
    }
    //endregion

    //region Typeface -> Default = IranYekan
    public void setTypeface(Typeface typeface) {
        if (typeface != null) {
            this.typeface = typeface;
        }
    }
    //endregion

    //region SelectionMode -> Default = Range | Enum -> {Single(1),Range(2),None(3)}
    private DateRangeCalendarView.SelectionMode selectionMode = DateRangeCalendarView.SelectionMode.Range;
    private DateRangeCalendarView.HolidayMode holidayMode = DateRangeCalendarView.HolidayMode.Enable;

    public DateRangeCalendarView.SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(DateRangeCalendarView.SelectionMode selectionMode, DateRangeCalendarView.HolidayMode holidayMode) {
        if (selectionMode != null) {
            this.selectionMode = selectionMode;
            this.holidayMode = holidayMode;
        }
    }
    //endregion

    //region CurrentDate -> Default = Today
    private PersianCalendar currentDate = new PersianCalendar();

    public PersianCalendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(PersianCalendar currentDate) {
        this.currentDate = currentDate;
    }
    //endregion

    //region MinDate -> Default = Today
    private PersianCalendar minDate;

    public PersianCalendar getMinDate() {
        return minDate;
    }

    public void setMinDate(PersianCalendar minDate) {
        this.minDate = minDate;
//        calendar.setMinDate(minDate);
    }
    //endregion

    //region MaxDate -> Default = Today + 1 year
    private PersianCalendar maxDate;

    public PersianCalendar getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(PersianCalendar maxDate) {
        this.maxDate = maxDate;
//        calendar.setMaxDate(maxDate);
    }
    //endregion

    //region showGregorianDate -> Default = false
    private boolean showGregorianDate = false;

    public boolean isShowGregorianDate() {
        return showGregorianDate;
    }

    public void setShowGregorianDate(boolean showGregorianDate) {
        this.showGregorianDate = showGregorianDate;
    }
    //endregion

    //region timePicker -> Default = false
    private boolean enableTimePicker = false;

    public boolean isEnableTimePicker() {
        return enableTimePicker;
    }

    public void setEnableTimePicker(boolean enableTimePicker) {
        this.enableTimePicker = enableTimePicker;
    }
    //endregion


    //region Listener -> OnSingleDateSelected & OnRangeDateSelected
    //region OnSingleDateSelected -> Getter/Setter
    private OnSingleDateSelectedListener onSingleDateSelectedListener;

    public OnSingleDateSelectedListener getOnSingleDateSelectedListener() {
        return onSingleDateSelectedListener;
    }

    public void setOnSingleDateSelectedListener(OnSingleDateSelectedListener onSingleDateSelectedListener) {
        this.onSingleDateSelectedListener = onSingleDateSelectedListener;
    }
    //endregion

    //region OnRangeDateSelected -> Getter/Setter
    private OnRangeDateSelectedListener onRangeDateSelectedListener;

    public OnRangeDateSelectedListener getOnRangeDateSelectedListener() {
        return onRangeDateSelectedListener;
    }

    public void setOnRangeDateSelectedListener(OnRangeDateSelectedListener onRangeDateSelectedListener) {
        this.onRangeDateSelectedListener = onRangeDateSelectedListener;
    }
    //endregion
    //endregion


    //region theme
    private int acceptButtonColor, headerBackgroundColor, weekColor, rangeStripColor, selectedDateCircleColor, selectedDateColor, defaultDateColor, disableDateColor, rangeDateColor, holidayColor, todayColor;
    private float textSizeTitle, textSizeWeek, textSizeDate;

    public int getAcceptButtonColor() {
        return acceptButtonColor;
    }

    public void setAcceptButtonColor(int acceptButtonColor) {
        this.acceptButtonColor = acceptButtonColor;
    }

    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(int headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public int getWeekColor() {
        return weekColor;
    }

    public void setWeekColor(int weekColor) {
        this.weekColor = weekColor;
    }

    public int getRangeStripColor() {
        return rangeStripColor;
    }

    public void setRangeStripColor(int rangeStripColor) {
        this.rangeStripColor = rangeStripColor;
    }

    public int getSelectedDateCircleColor() {
        return selectedDateCircleColor;
    }

    public void setSelectedDateCircleColor(int selectedDateCircleColor) {
        this.selectedDateCircleColor = selectedDateCircleColor;
    }

    public int getSelectedDateColor() {
        return selectedDateColor;
    }

    public void setSelectedDateColor(int selectedDateColor) {
        this.selectedDateColor = selectedDateColor;
    }

    public int getDefaultDateColor() {
        return defaultDateColor;
    }

    public void setDefaultDateColor(int defaultDateColor) {
        this.defaultDateColor = defaultDateColor;
    }

    public int getDisableDateColor() {
        return disableDateColor;
    }

    public void setDisableDateColor(int disableDateColor) {
        this.disableDateColor = disableDateColor;
    }

    public int getRangeDateColor() {
        return rangeDateColor;
    }

    public void setRangeDateColor(int rangeDateColor) {
        this.rangeDateColor = rangeDateColor;
    }

    public int getHolidayColor() {
        return holidayColor;
    }

    public void setHolidayColor(int holidayColor) {
        this.holidayColor = holidayColor;
    }

    public int getTodayColor() {
        return todayColor;
    }

    public void setTodayColor(int todayColor) {
        this.todayColor = todayColor;
    }

    public float getTextSizeTitle() {
        return textSizeTitle;
    }

    public void setTextSizeTitle(float textSizeTitle) {
        this.textSizeTitle = textSizeTitle;
    }

    public float getTextSizeWeek() {
        return textSizeWeek;
    }

    public void setTextSizeWeek(float textSizeWeek) {
        this.textSizeWeek = textSizeWeek;
    }

    public float getTextSizeDate() {
        return textSizeDate;
    }

    public void setTextSizeDate(float textSizeDate) {
        this.textSizeDate = textSizeDate;
    }

    //endregion

    //endregion

    //region Listeners -> Interface
    public interface OnSingleDateSelectedListener {
        void onSingleDateSelected(PersianCalendar date);
    }

    public interface OnRangeDateSelectedListener {
        void onRangeDateSelected(PersianCalendar startDate, PersianCalendar endDate);
    }

//    public interface OnMultipleDateSelectedListener {
//        void onMultipleDateSelected(ArrayList<PersianCalendar> startDate);
//    }
    //endregion
}
