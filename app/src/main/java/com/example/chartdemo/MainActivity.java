package com.example.chartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private LineDataSet set;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineChart = findViewById(R.id.lineChart);
        Description description = new Description();
        description.setText("猴哥图表描述");
        mLineChart.setDescription(description);
        mLineChart.setDrawBorders(false);//禁止绘制图表边框的线
        mLineChart.animateXY(2000, 2000);
        //后台绘制
        mLineChart.setDrawGridBackground(true);

        //获取此图表的x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        // 设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(6);
        xAxis.setAxisMaximum(24);
        xAxis.setAxisMinimum(0);
        xAxis.setTextColor(Color.BLACK);//设置轴标签文字的颜色



        DecimalFormat df = new DecimalFormat("00");
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String numStr = df.format(value) + ":00";
                return numStr;
            }
        });


        /**
         * Y轴默认显示左右两个轴线
         */
        //获取右边的轴线
        YAxis rightAxis = mLineChart.getAxisRight();
        YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //设置网格线为虚线效果
        leftAxis.setDrawGridLines(false);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(50);
        leftAxis.setDrawAxisLine(false);//是否绘制轴线
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int)value+"%";
            }
        });

        // 设置x轴的LimitLine
        LimitLine yLimitLine1 = new LimitLine(36f,"最高正常值");
        yLimitLine1.setLineWidth(1.5f);
        yLimitLine1.setLineColor(Color.GREEN);
        yLimitLine1.setTextColor(Color.GREEN);

        LimitLine yLimitLine2 = new LimitLine(14f,"最低正常值");
        yLimitLine2.setLineWidth(1.5f);
        yLimitLine2.setLineColor(Color.parseColor("#FF313131"));
        yLimitLine2.setTextColor(Color.parseColor("#FF313131"));

        leftAxis.addLimitLine(yLimitLine1);
        leftAxis.addLimitLine(yLimitLine2);


        //图例
        Legend legend = mLineChart.getLegend();
        legend.setEnabled(false);
//        // 设置legend显示位置
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setTextSize(10f);//设置文字大小
//        legend.setForm(Legend.LegendForm.LINE);//正方形，圆形或线
//        legend.setFormSize(10f); // 设置Form的大小
//        legend.setFormLineWidth(2f);
//        legend.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
//        //设置图例下、中对齐
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//
        mLineChart.setNoDataText("没有数据");//没有数据时显示的文字
        mLineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色

        initData();

        mLineChart.setDragEnabled(true);// 是否可以拖拽

        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });


    }

    private void initData() {
        /**
         * Entry 坐标点对象  构造函数 第一个参数为x点坐标 第二个为y点
         */
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0, 10));
        values.add(new Entry(10, 26));
        values.add(new Entry(15, 30));
        values.add(new Entry(24, 20));

        //设置数据
        setData(values);
        //刷新
        mLineChart.invalidate();
    }

    private void setData(ArrayList<Entry> values) {
        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set = new LineDataSet(values, "实时监测");

            //设置线的颜色
            set.setColor(Color.parseColor("#3A76FE"));
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); // 设置折线类型，这里设置为水平贝塞尔曲线
            set.setCubicIntensity(0.3f);//设置曲线的Mode强度，0-1

            //设置坐标点属性
            set.setCircleColor(Color.parseColor("#3A76FE"));
            set.setDrawCircleHole(true);
            set.setLineWidth(2f);
            set.setCircleRadius(4f);
            set.setValueTextSize(14f);
            set.setDrawFilled(true);
            set.setFillDrawable(getDrawable(R.drawable.chart_fill));


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            //添加数据集
            dataSets.add(set);
            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            //更新数据
            mLineChart.setData(data);
        }
    }


}

