# DatePickerTimerDialog
DatePickerTimerDialog日期选择控件
关于日期选择控件，之前项目中用到了好多次，不同的项目需求是不一样的，比如说UI啊，时间选择限制等等。这次写的demo就从几个方面来解决项目中可能出现的需求情况。
首先看效果：
      ![只显示年月日](http://img.blog.csdn.net/20170228145919454?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGYwODE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![显示年月日和时间](http://img.blog.csdn.net/20170228150109111?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGYwODE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


1.首先关于dialog的XML布局

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="fill_parent"
              android:layout_height="wrap_content"
              android:background="#FFFFFFFF"
              android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:padding="12dp">

        <TextView
            android:id="@+id/date_title"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text=""
            android:textColor="#49ADE9"
            android:textSize="17dp"/>

        <ImageView
            android:id="@+id/date_cancle"
            android:layout_width="20dp"
            android:layout_height="20dp"
            android:background="@mipmap/icon_x"
            android:scaleType="center"/>
    </LinearLayout>

    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="#49ADE9"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <DatePicker
            android:id="@+id/datepicker"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:calendarViewShown="false"
            android:datePickerMode="spinner"
            android:descendantFocusability="blocksDescendants"
            android:spinnersShown="true"
            android:theme="@style/Theme.AppCompat.Light"></DatePicker>

        <TimePicker
            android:id="@+id/timepicker"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:theme="@style/Theme.AppCompat.Light"
            android:timePickerMode="spinner"
            android:visibility="gone"/>
    </LinearLayout>

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="#f4f4f4"/>

    <TextView
        android:id="@+id/date_submit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingBottom="16dp"
        android:paddingTop="16dp"
        android:text="完成"
        android:textSize="17dp"/>
</LinearLayout>
```
      注：1>.在DatePicker中有个属性datePickerMode，这个是为了选择显示模式，比如：日历模式，滚动模式。
       2>.同样在TimePicker中也有个属性timePickerMode，是否是显示日历模式还是滚动模式
2.Activity布局显示：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    android:id="@+id/activity_main"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.example.li.datepickertimerdialog.MainActivity">

    <Button
        android:id="@+id/btn_time"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="选择时间"
        android:textSize="14dp"
        android:textColor="#000000"/>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="10dp">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#ff0000"
            android:text="时间显示："/>
        <TextView
            android:id="@+id/show_time"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="#ff0000"/>
    </LinearLayout>

</LinearLayout>

```
3.点击弹出时间选择控件

```
  Button btnTime = (Button) findViewById(R.id.btn_time);
  final TextView showTime = (TextView)findViewById(R.id.show_time);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(MainActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(showTime, 0, false);
            }
        });
```
      这里关于dateTimePicKDialog中几个参数的介绍：
       ①.showTime是要显示最终选择的时间
       ②.0表示大于当前时间0天，如果不需要限制时间选择的话，我这里是传了-9999，这块代码有些问题，没来得及修改，但是不影响操作，望见谅
       ③.boolean值表示是否要展示TimePicker,false表示不展示
   4.DateTimePickDialogUtil代码
   初始化：
   

```
public void init(DatePicker datePicker) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (day > 0) {
            calendar.setTime(new Date(System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24)));
            dateTime = sdf.format(calendar.getTime());
        } else {
            dateTime = sdf.format(calendar.getTime());
        }

        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "- ";
        }

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (day > 0) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.setTime(new Date(System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24)));
                    // 获得日历实例
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(view.getYear(), view.getMonth(),
                            view.getDayOfMonth());
                    if (System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24) > calendar.getTimeInMillis()) {
                        view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
                        dateTime = sdf.format(mCalendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    } else {
                        // 获得日历实例
                        dateTime = sdf.format(calendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    }
                } else if (day == -9999) {
                    // 获得日历实例
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(view.getYear(), view.getMonth(),
                            view.getDayOfMonth());
                    dateTime = sdf.format(calendar.getTime());
                    dateTitle.setText(dateTime + getWeek(dateTime));
                } else {
                    if (isDateAfter(view)) {
                        Calendar mCalendar = Calendar.getInstance();
                        view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
                        dateTime = sdf.format(mCalendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    } else {
                        // 获得日历实例
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(view.getYear(), view.getMonth(),
                                view.getDayOfMonth());
                        dateTime = sdf.format(calendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    }
                }
            }

            private boolean isDateAfter(DatePicker tempView) {
                Calendar mCalendar = Calendar.getInstance();
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.set(tempView.getYear(), tempView.getMonth(), tempView.getDayOfMonth(), 0, 0, 0);
                if (tempCalendar.after(mCalendar))
                    return false;
                else
                    return true;

            }
        });
    }
```

   
弹出dialog：

```
 /**
     * 弹出日期时间选择框方法
     * 当前时间的下一天
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public Dialog dateTimePicKDialog(final TextView inputDate, int day, final boolean isShowTime) {
        this.day = day;
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_datetime, null);
        final Dialog dialog = new android.app.AlertDialog.Builder(activity).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keylistener);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(dateTimeLayout);


        dateTitle = (TextView) dateTimeLayout.findViewById(R.id.date_title);
        TimePicker timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        if (isShowTime) {
            timePicker.setVisibility(View.VISIBLE);
            timePicker.setIs24HourView(true);
        }
        hours = timePicker.getCurrentHour();
        minutes = timePicker.getCurrentMinute();
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
            }
        });
        dateTitle.setText(initDateTime);
        ImageView dateCancle = (ImageView) dateTimeLayout.findViewById(R.id.date_cancle);
        TextView dateSubmit = (TextView) dateTimeLayout.findViewById(R.id.date_submit);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);

        dateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Long diff = null;
                try {
                    Date d1 = df.parse(initDateTime);
                    Date d2 = df.parse(dateTime); //前的时间
                    diff = d1.getTime() - d2.getTime();   //两时间差
                    d = d1.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (diff >= 0) {
                    Toast.makeText(activity.getApplicationContext(), "请选择大于现在的日期", Toast.LENGTH_SHORT).show();

                } else {
                    if (isShowTime) {
                        if (minutes < 10) {
                            inputDate.setText(dateTime + " " + hours + ":0" + minutes);
                        } else {
                            inputDate.setText(dateTime + " " + hours + ":" + minutes);
                        }
                    } else {
                        inputDate.setText(dateTime);
                    }

                    if (cd != null) {
                        cd.toShow(d);
                    }
                }
                dialog.dismiss();
            }
        });
        dateCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        init(datePicker);
        dateTitle.setText(dateTime + getWeek(dateTime));
        return dialog;
    }
```
关于选择周几：

```
private String getWeek(String time) {
        String Week = " 周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//也可将此值当参数传进来
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                Week += "日";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }
        return Week;
    }
```
   由于有的项目中需要在年月日中的显示中添加“日”，这个一直没找到好的方法，以及对时间的显示只显示时，去掉分，这个也一直没想到好的做法（除了自定义，有水平有限）。希望有大神了可以再此基础上修改，可以发到我邮箱（1346765933@qq.com）。
   最后如果有写的不好的请提出您宝贵的意见，谢谢！
   CSDN博客地址：http://blog.csdn.net/lf0814/article/details/58587835
 
