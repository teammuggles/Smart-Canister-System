package com.himanshu.smartcanister;

import android.content.Intent;
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
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_canister);

        Intent i=getIntent();
        content=i.getStringExtra("content");

        pieChart=(PieChart)findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();

        if(content.equals("Oats"))
        {
            entries.add(new PieEntry(66f, "Carbohydrates"));
            entries.add(new PieEntry(7f, "Fats"));
            entries.add(new PieEntry(17f, "Proteins"));
            entries.add(new PieEntry(11f,"Fiber"));
        }
        if(content.equals("Almonds"))
        {
            entries.add(new PieEntry(19.74f, "Carbohydrates"));
            entries.add(new PieEntry(50.64f, "Fats"));
            entries.add(new PieEntry(21.26f, "Proteins"));
        }
        if(content.equals("Rice"))
        {
            entries.add(new PieEntry(27.9f, "Carbohydrates"));
            entries.add(new PieEntry(0.28f, "Fats"));
            entries.add(new PieEntry(2.66f, "Proteins"));
            entries.add(new PieEntry(11f,"Fiber"));

        }
        if(content.equals("Dal"))
        {
            entries.add(new PieEntry(87f, "Carbohydrates"));
            entries.add(new PieEntry(26.7f, "Fats"));
            entries.add(new PieEntry(24.0f, "Proteins"));
            entries.add(new PieEntry(11f,"Fiber"));

        }

        PieDataSet set = new PieDataSet(entries, "Your Consumption");
        set.setColors(new int[]{R.color.red,R.color.yellow,R.color.blue,R.color.green},this);
        PieData data = new PieData(set);
        pieChart.setData(data);
        //pieChart.invalidate();
    }
}
