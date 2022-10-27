package com.tsh.chart.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tsh.chart.R;
import com.tsh.chart.bar.LineBarChartBase;
import com.tsh.chart.data.ChartData;
import com.tsh.chart.data.SimpleChartData;
import com.tsh.chart.data.PercentChartEntry;
import com.tsh.chart.line.LineChart;
import com.tsh.chart.line.LineChartBase;
import com.tsh.chart.percent.CirclePercentChart;
import com.tsh.chart.percent.LinePercentGroupChart;
import com.tsh.chart.percent.LinePieChart;
import com.tsh.chart.pie.PieChart;
import com.tsh.chart.pie.PieView;
import com.tsh.chart.render.SimpleLineChartMarkRender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ChartActivity extends AppCompatActivity {
    PieChart pieChart, pieChart2;
    LinePercentGroupChart linePercentGroupChart;
    LinePieChart linePieChart;
    LineBarChartBase<XAxis> lineBarChart;
    LineChart<LineChartXAxis, LineChartEntry> lineChart;
    com.github.mikephil.charting.charts.LineChart mpLineChart;
    ViewGroup circlePercentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        pieChart = findViewById(R.id.pie_chart);
        initPieChartData();
        circlePercentGroup = findViewById(R.id.circle_percent_chart_group);
        pieChart2 = findViewById(R.id.pie_chart_2);
        initPieChart2Data();
        linePercentGroupChart = findViewById(R.id.line_distribution_chart);
        initLineDistributionChartData();
        linePieChart = findViewById(R.id.line_pie_chart);
        initLinePieChartData();
        lineBarChart = findViewById(R.id.line_bar_chart);
        initLineBarChart();
        lineChart = findViewById(R.id.line_chart);
        initLineChart();
        mpLineChart = findViewById(R.id.mp_line_chart);
        initMPLineChart();

    }

    private void initMPLineChart() {
        List<ILineDataSet> dataSets = new ArrayList<>();
        List<Entry> entries1 = new ArrayList<>();
//        List<Entry> entries2 = new ArrayList<>();

        int count = 10000;
        long now = System.currentTimeMillis();
        List<LineChartXAxis> xValues = new ArrayList<>();
        for (int i = count - 1; i >= 0 ; i--) {
            Date date = new Date(now - (24L * 60 * 60 * 1000) * i);
            xValues.add(new LineChartXAxis(SDF.format(date)));
            entries1.add(new Entry(i, random.nextFloat() * (random.nextBoolean() ? 50 : 100)));
//            entries2.add(new Entry(i, random.nextFloat() * (random.nextBoolean() ? 50 : 100)));
        }
        LineDataSet set1 = new LineDataSet(entries1, "set 1");
//        LineDataSet set2 = new LineDataSet(entries2, "set 2");
        set1.setColor(getColor(R.color.chart_active_color));
//        set2.setColor(Color.parseColor("#01B07D"));
        dataSets.add(set1);
//        dataSets.add(set2);
        mpLineChart.setMaxVisibleValueCount(10);
        LineData lineData = new LineData(set1);
        mpLineChart.setData(lineData);
        mpLineChart.invalidate();
    }

    public static class XAxis implements LineBarChartBase.XAxis {

        private final int month;

        public XAxis(int month) {
            this.month = month;
        }

        @Override
        public CharSequence toReadable() {
            return month + "月";
        }
    }

    public static class LineChartXAxis implements LineChartBase.XAxis {

        private final String readableData;

        public LineChartXAxis(String readableData) {
            this.readableData = readableData;
        }

        @Override
        public CharSequence getLabel() {
            return readableData;
        }
    }


    Random random = new Random();
    final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);

    float randomPercent() {
        return random.nextFloat() * (random.nextBoolean() ? 1 : -1);
    }

    public static class LineChartEntry extends SimpleLineChartMarkRender.AbsEntry {

        String event;
        String eventDetail;
        boolean isSell;
        boolean isBuy;

        public LineChartEntry(int index, float value) {
            super(index, value);
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public boolean isSell() {
            return isSell;
        }

        @Override
        public String getEventDetail() {
            return eventDetail;
        }

        public void setEventDetail(String eventDetail) {
            this.eventDetail = eventDetail;
        }

        public void setSell(boolean sell) {
            isSell = sell;
        }

        public boolean isBuy() {
            return isBuy;
        }

        public void setBuy(boolean buy) {
            isBuy = buy;
        }
    }

    private void initLineChart() {
        lineChart.getLineChartBase().setMarksRender(new SimpleLineChartMarkRender<>(lineChart.getLineChartBase()));
        ChartData<LineChartXAxis, LineChartBase.EntrySet<LineChartEntry>> data = new ChartData<>();
        int count = 15000;
        long now = System.currentTimeMillis();
        List<LineChartXAxis> xValues = new ArrayList<>();
        List<LineChartEntry> entries1 = new ArrayList<>();
        List<LineChartEntry> entries2 = new ArrayList<>();
        for (int i = count - 1; i >= 0 ; i--) {
            Date date = new Date(now - (24L * 60 * 60 * 1000) * i);
            xValues.add(new LineChartXAxis(SDF.format(date)));
            LineChartEntry entry1 = new LineChartEntry(count - 1  - i, random.nextFloat() * (random.nextBoolean() ? 50 : 100));
            if (i % 5 == 0) {
                entry1.setBuy(true);
            }
            if (i % 7 == 0) {
                entry1.setSell(true);
            }
            if (i % 9 == 0) {
                entry1.setEvent("红");
                entry1.setEventDetail("以便以后单击按钮时");
            }
            entries1.add(entry1);
            entries2.add(new LineChartEntry(i, random.nextFloat() * (random.nextBoolean() ? 50 : 100)));
        }
        data.setxValues(xValues);
        LineChartBase.EntrySet<LineChartEntry> set1 = new LineChartBase.EntrySet<>("Set 1", Color.parseColor("#01B07D"), entries1);
        set1.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1f, getResources().getDisplayMetrics()));
        set1.setHighlightWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1f, getResources().getDisplayMetrics()));
        set1.setHighlightColor(Color.YELLOW);
        set1.setHighlightPointOuterColor(Color.GRAY);
        set1.setHighlightPointInnerColor(Color.GREEN);
        set1.setHighlightPointInnerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4f, getResources().getDisplayMetrics()));
        set1.setHighlightPointOuterRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                7f, getResources().getDisplayMetrics()));

        LineChartBase.EntrySet<LineChartEntry> set2 = new LineChartBase.EntrySet<>("Set 2", getColor(R.color.chart_active_color), entries2);
        set2.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics()));
        data.setData(Arrays.asList(set1));
//        lineChart.getLineChartBase().setDefaultIndex(xValues.size());
        lineChart.getLineChartBase().setOnHighlightChangeListener((index, prev) -> {
            Log.i(TAG, "initLineChart: " + prev + ">>>" + index);
        });
        lineChart.setData(data);
    }

    private static final String TAG = "ChartActivity";

    private void initLineBarChart() {

        ChartData<XAxis, LineBarChartBase.EntrySet> data = new ChartData<>();
        List<XAxis> xVals = new ArrayList<>();
        List<LineBarChartBase.EntrySet> entrySets = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            xVals.add(new XAxis(i));
            entrySets.add(new LineBarChartBase.EntrySet("", Arrays.asList(
                    new PercentChartEntry(Color.parseColor("#9775E4"), randomPercent()),
                    new PercentChartEntry(Color.parseColor("#49D2C8"), randomPercent()),
                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent()),
                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent())
            )));
//            entrySets.add(new LineBarChartBase.EntrySet("", Arrays.asList(
//                    new PercentChartEntry(Color.parseColor("#9775E4"), randomPercent()),
////                    new PercentChartEntry(Color.parseColor("#49D2C8"), randomPercent()),
//                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent())
////                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent())
//            )));
//            entrySets.add(new LineBarChartBase.EntrySet("", Arrays.asList(
////                    new PercentChartEntry(Color.parseColor("#9775E4"), 0.3f),
//                    new PercentChartEntry(Color.parseColor("#49D2C8"), 0.7f),
////                    new PercentChartEntry(Color.parseColor("#01B07D"), -0.1f),
//                    new PercentChartEntry(Color.parseColor("#01B07D"), 0.2f)
//            )));
        }
        data.setxValues(xVals);
        data.setData(entrySets);
        lineBarChart.setData(data);
        lineBarChart.setOnSelectListener((index, subIndex) ->
                Toast.makeText(this, "Select: " + index + "-" + subIndex, Toast.LENGTH_LONG).show());
        lineBarChart.showAnimator();
    }

    private void initLinePieChartData() {
        SimpleChartData<PercentChartEntry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("盈利", "平衡", "亏损"));
        simpleChartData.setData(Arrays.asList(
                new PercentChartEntry(Color.parseColor("#9775E4"), 0.30f),
                new PercentChartEntry(Color.parseColor("#49D2C8"), 0.02f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.68f)
        ));
        linePieChart.setData(simpleChartData);
    }

    private void initLineDistributionChartData() {
        SimpleChartData<PercentChartEntry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("制造业", "消费品", "汽车", "一句话说不清的行业"));
        simpleChartData.setData(Arrays.asList(
                new PercentChartEntry(Color.parseColor("#9775E4"), 0.70f),
                new PercentChartEntry(Color.parseColor("#49D2C8"), 0.0001f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.9999f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.04f)
        ));
        linePercentGroupChart.setData(simpleChartData);
    }

    private void initPieChart2Data() {
        SimpleChartData<PieView.Entry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("股票做多", "期权做多", "股票做空", "期权做空"));
        simpleChartData.setData(Arrays.asList(
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4")),
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4")),
                new PieView.Entry(0.1500f, Color.parseColor("#01B07D")),
                new PieView.Entry(0.01f, Color.parseColor("#01B07D"))
        ));
        pieChart2.setData(simpleChartData);
    }

    private void initPieChartData() {
        SimpleChartData<PieView.Entry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("股票", "期权", "投顾", "数字货币"));
        simpleChartData.setData(Arrays.asList(
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4"), true),
                new PieView.Entry(0.2801f, Color.parseColor("#49D2C8")),
                new PieView.Entry(0.3955f, Color.parseColor("#01B07D")),
                new PieView.Entry(0.3955f, Color.parseColor("#01B07D"))
        ));
        pieChart.setData(simpleChartData);
    }

    private boolean isHide = false;

    public void onHideClick() {
        isHide = !isHide;
        pieChart.hideDetail(isHide);
        pieChart2.hideDetail(isHide);

        for (int i = 0; i < circlePercentGroup.getChildCount(); i++) {
            View childAt = circlePercentGroup.getChildAt(i);
            if (childAt instanceof CirclePercentChart) {
                CirclePercentChart chart = (CirclePercentChart) childAt;
                chart.hideDetail(isHide);
            }
        }

        linePercentGroupChart.hideDetail(isHide);

        linePieChart.hideDetail(isHide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Hide");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            onHideClick();
        }
        return super.onOptionsItemSelected(item);
    }
}