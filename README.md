# Persian CalendarView and TimePicker 

A highly customizable calendar library for Android, powered by RecyclerView.

[![CI](https://github.com/kizitonwose/CalendarView/workflows/CI/badge.svg?branch=master)](https://github.com/kizitonwose/CalendarView/actions) 
[![JitPack](https://jitpack.io/v/kizitonwose/CalendarView.svg)](https://jitpack.io/#kizitonwose/CalendarView) 
[![License](https://img.shields.io/badge/linkdin-linkdin-blue)](https://www.linkedin.com/in/adel-dadras) 
[![Twitter](https://img.shields.io/badge/Twitter-@kizitonwose-9C27B0.svg)](https://twitter.com/kizitonwose)


**With this library, your calendar will look however you want it to.**

![Preview](https://github.com/Loopersgroup/persian-calendar/blob/master/images/calendar_img.jpeg)

## Features

- [x] [Single or range selection](#date-selection) - The library provides the calendar logic which enables you to implement the view whichever way you like.
- [x] [Disable desired dates](#disabling-dates) - Prevent selection of some dates by disabling them.
- [x] Boundary dates - limit the calendar date range.
- [x] Custom date view - make your day cells look however you want, with any functionality you want.
- [x] Custom calendar view - make your calendar look however you want, with whatever functionality you want.
- [x] Easily scroll to any date or month view using the date.
- [x] Use all RecyclerView customisations(decorators etc) since CalendarView extends from RecyclerView.


## Sample project

It's very important to check out the sample app. Most techniques that you would want to implement are already implemented in the examples.


View the sample app's source code [here](https://github.com/Loopersgroup/persian-calendar.git)

## Setup

The library uses `java.time` classes via [Java 8+ API desugaring](https://developer.android.com/studio/write/java8-support#library-desugaring) for backward compatibility since these classes were added in Java 8.

#### Step 1

To setup your project for desugaring, you need to first ensure that you are using [Android Gradle plugin](https://developer.android.com/studio/releases/gradle-plugin#updating-plugin) 4.0.0 or higher.

Then include the following in your app's build.gradle file:

```groovy
android {
  defaultConfig {
    // Required ONLY when setting minSdkVersion to 17 or higher
    multiDexEnabled true
  }

  compileOptions {
    // Flag to enable support for the new language APIs
    coreLibraryDesugaringEnabled true
    // Sets Java compatibility to Java 8
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}

```



#### Step 2

Add the JitPack repository to your project level `build.gradle`:

```groovy
allprojects {
 repositories {
    google()
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

Add CalendarView to your app `build.gradle`:

```groovy
dependencies {
	implementation 'com.github.Loopersgroup:persian-calendar:<latest-version>'
}
```

## Usage

#### Step 1

use date picker and show this by new DatePickerDialog 
when need range date picker must use `DateRangeCalendarView.SelectionMode.Range` 
when use single date picker must use `DateRangeCalendarView.SelectionMode.Single` 
when want last days disable must use `DateRangeCalendarView.HolidayMode.Enable`
when use last days enable must use   `DateRangeCalendarView.HolidayMode.Disable`
The view passed in here is the inflated day view resource which you provided.

```java
        AppCompatEditText dateTxt = findViewById(R.id.date_txt);
         DatePickerDialog datePickerDialog = null;
         datePickerDialog = new DatePickerDialog(MainActivity.this);
         datePickerDialog.setSelectionMode(DateRangeCalendarView.SelectionMode.Range, DateRangeCalendarView.HolidayMode.Enable);
         datePickerDialog.setCanceledOnTouchOutside(true);
         datePickerDialog.setOnRangeDateSelectedListener(new DatePickerDialog.OnRangeDateSelectedListener() {
                    @Override
                    public void onRangeDateSelected(PersianCalendar startDate, PersianCalendar endDate) {
                        dateTxt.setText(startDate.getPersianShortDate());
                    }
                });

         datePickerDialog.showDialog();
```
#### Step 2
use time picker and show this by new TimePickerDialog 

```java
 TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.TimePickerCallback() {
                @Override
                public void onTimeSelected(int hours, int mins) {
                    TimeTxt.setText(String.valueOf(hours)+" : "+String.valueOf(mins));
                }

                @Override
                public void onCancel() {

                }
            });

            timePickerDialog.showDialog();
```



## Inspiration

CalendarView was inspired by the Android library [PersianRangeDatePicker](https://github.com/ali-sardari/PersianRangeDatePicker). I used PersianRangeDatePicker in an Android project but couldn't find anything as customizable on Android so I built this. 
You'll find some similar terms like `InDateStyle`, `OutDateStyle`, `DayOwner` etc.

