package com.himanshu.smartcanister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.himanshu.smartcanister.models.Canister;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView addSmartCanisterText;
    CanisterAdapter canisterAdapter;
    ArrayList<Canister> canisterList=new ArrayList<>();
    RecyclerView canisterRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        addSmartCanisterText=(TextView)findViewById(R.id.addSmartCanisterText);

        canisterRecycleView=(RecyclerView) findViewById(R.id.canisterRecyclerView);
        canisterAdapter = new CanisterAdapter(canisterList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        canisterRecycleView.setLayoutManager(mLayoutManager);
        canisterRecycleView.setItemAnimator(new DefaultItemAnimator());
        canisterRecycleView.setAdapter(canisterAdapter);

        canisterList.add(new Canister("Sugar","70%",""));
        canisterList.add(new Canister("Oats","55%",""));
        canisterAdapter.notifyDataSetChanged();
    }
}
