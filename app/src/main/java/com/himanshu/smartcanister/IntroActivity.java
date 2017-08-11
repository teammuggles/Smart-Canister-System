package com.himanshu.smartcanister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.himanshu.smartcanister.utils.SharedPreferenceUtil;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

/**
 * Created by ritwick on 11/8/17.
 */

public class IntroActivity extends MaterialIntroActivity
{
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        context=getApplicationContext();

        if(!SharedPreferenceUtil.getNewUser(context))
        {
            Intent i=new Intent(IntroActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorSlide1)
                        .buttonsColor(R.color.colorSlide1B)
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("title")
                        .description("Description")
                        .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorSlide2)
                        .buttonsColor(R.color.colorSlide2B)
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("title")
                        .description("Description")
                        .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorSlide3)
                        .buttonsColor(R.color.colorSlide3B)
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("title")
                        .description("Description")
                        .build());

        SharedPreferenceUtil.saveNewUser(context,false);
    }

    @Override
    public void onFinish()
    {
        super.onFinish();
        startActivity(new Intent(IntroActivity.this,LoginActivity.class));
    }
}
