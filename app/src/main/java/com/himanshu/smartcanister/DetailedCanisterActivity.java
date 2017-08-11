package com.himanshu.smartcanister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class DetailedCanisterActivity extends AppCompatActivity
{
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_canister);
        pieChart=(PieChart)findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Carbohydrates"));
        entries.add(new PieEntry(26.7f, "Fats"));
        entries.add(new PieEntry(24.0f, "Proteins"));
        entries.add(new PieEntry(30.8f, "Calories"));

        PieDataSet set = new PieDataSet(entries, "Your Consumption");
        set.setColors(new int[]{R.color.red,R.color.yellow,R.color.blue,R.color.green},this);
        PieData data = new PieData(set);
        pieChart.setData(data);
        //pieChart.invalidate();
    }
}
