package com.himanshu.smartcanister;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.himanshu.smartcanister.models.Canister;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.smartcanister.models.MessageEvent;
import com.himanshu.smartcanister.utils.CartActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseOats;
    TextView addSmartCanisterText;
    CanisterAdapter canisterAdapter;
    ArrayList<Canister> canisterList=new ArrayList<>();
    private static ArrayList<Canister> cannisterListTemp1;
    private static int count=0;
    RecyclerView canisterRecycleView;
    long noOats;
    float distance1,distance2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Your canisters");
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String curr = mAuth.getCurrentUser().getUid().toString();

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Canisters").child(curr);



        EventBus.getDefault().register(this);

        addSmartCanisterText=(TextView)findViewById(R.id.addSmartCanisterText);

        canisterRecycleView=(RecyclerView) findViewById(R.id.canisterRecyclerView);
        canisterAdapter = new CanisterAdapter(this,canisterList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        canisterRecycleView.setLayoutManager(mLayoutManager);
        canisterRecycleView.setItemAnimator(new DefaultItemAnimator());
        canisterRecycleView.setAdapter(canisterAdapter);

        //canisterList.add(new Canister("Sugar","70%",""));
        //canisterAdapter.notifyDatasetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth= FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         mDatabaseOats=mDatabase.child("oats");
        DatabaseReference mDatabaseIdOats=mDatabase.child("oats");
        mDatabaseIdOats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 noOats=dataSnapshot.getChildrenCount();
                Toast.makeText(MainActivity.this,noOats+"",Toast.LENGTH_SHORT).show();
                mDatabaseOats.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        count++;
                        if(noOats==1)
                        {
                            canisterList.add(new Canister("Oats",String.valueOf(dataSnapshot.getValue(Float.class)),
                                    "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/61BBuz1CBWL._SX522_.jpg?alt=media&token=986b1571-4aa3-486d-b91e-bec5e97b4acd"));
                            canisterAdapter.notifyDatasetChanged();
                            Toast.makeText(MainActivity.this,"Your Consumed "+0+"with respect to your previous consumption", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(count==noOats-1) {
                            //if(count==dataSnapshot.getChildrenCount()-1) {
                            distance1 = dataSnapshot.getValue(Float.class);
                        }
                        //else if(count==dataSnapshot.getChildrenCount())
                        //{
                        else if(count==noOats){
                            distance2 = dataSnapshot.getValue(Float.class);
                            float consumption=distance1-distance2;
                            Toast.makeText(MainActivity.this,consumption+"", Toast.LENGTH_SHORT).show();
                            String percentage=String.valueOf(distance2);
                            Canister canisterTemp=new Canister("Oats",percentage,
                                    "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/61BBuz1CBWL._SX522_.jpg?alt=media&token=986b1571-4aa3-486d-b91e-bec5e97b4acd");
                            canisterList.add(canisterTemp);
                            canisterAdapter.notifyDatasetChanged();
                            getNotification();
                        }
                        //}









                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.add_canister_dialog);
                dialog.setTitle("Instructions:");
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Spinner dialogspinner = (Spinner) dialog.findViewById(R.id.contentspinner);
                List<String> spinnerArray =  new ArrayList<String>();
                spinnerArray.add("Select");
                spinnerArray.add("Oats");
                spinnerArray.add("Almonds");
                spinnerArray.add("Dal");
                spinnerArray.add("Rice");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialogspinner.setAdapter(adapter);
                dialogspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(position==0)
                        {
                            return;
                        }
                        else if(position==1)
                        {
                            enterIntoFirebase("oats");

                            //canisterList.add(new Canister("Oats","100%", "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/61BBuz1CBWL._SX522_.jpg?alt=media&token=986b1571-4aa3-486d-b91e-bec5e97b4acd"));
                        }
                        else if(position==2)
                        {
                            canisterList.add(new Canister("Almonds", "100%",
                                    "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/almonds.jpg?alt=media&token=4634044e-7ee6-4212-b8aa-a5c0a5a9a75a"));
                        }
                        else if(position==3)
                        {
                            canisterList.add(new Canister("Dal", "100%",
                                    "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/dal-fry-recipe1.jpg?alt=media&token=b3f86301-e16c-4671-beee-a31a947d1547"));
                        }
                        else if(position==4)
                        {
                            canisterList.add(new Canister("Rice","100%",
                                    "https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/rice-625_625x350_71426749881.jpg?alt=media&token=5b2e0c61-72bc-4cf6-88a6-365cba998326"));
                        }
                        canisterAdapter.notifyDatasetChanged();
                        dialog.dismiss();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        final CircleImageView profile = (CircleImageView)hView.findViewById(R.id.iv_image);
        final TextView uemail = (TextView)hView.findViewById(R.id.tv_email);
        final TextView uname= (TextView) hView.findViewById(R.id.tv_name);

        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser current = mAuth.getCurrentUser();
        String currentuser = current.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference().child("Users").child(currentuser);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                String image = dataSnapshot.child("Image").getValue().toString();
                String email=dataSnapshot.child("Email").getValue().toString();
                Picasso.with(MainActivity.this).load(image).into(profile);
                uname.setText(name);
                uemail.setText(email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Database Error",Toast.LENGTH_LONG).show();
            }


        });

    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getTitle().equals("Lifestyle"))
        {

        }
        else if(item.getTitle().equals("Recipes"))
        {

        }
        else if(item.getTitle().equals("My Cart"))
        {
            startActivity(new Intent(MainActivity.this, CartActivity.class));

        }
        else if(item.getTitle().equals("Log Out"))
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            return true;
        }
        else if(item.getTitle().equals("Developers"))
        {

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event)
    {
        if(event.message.equals("visibilitylogic"))
        {
            addSmartCanisterText.setVisibility(event.visibility);
        }
    }

    public void enterIntoFirebase(String ingredient)
    {


                        mDatabase.child(ingredient).push().setValue(100.0).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this,"Added..",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this,"Seems..there is a Problem With your Internet",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


    }

    public void addToCart()
    {
        FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).push().setValue("OATS").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,task.getException()+"",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getNotification() {

        android.support.v4.app.NotificationCompat.Builder builder;
        Intent notificationIntent;
        PendingIntent contentIntent;
        NotificationManager manager;
        if (distance2 <=20 && distance2 > 10) {

            builder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_menu_manage)
                            .setContentTitle("Smart Canister Notification")
                            .setContentText("Low Level.Time to Replenish. Only 20% left!");

            notificationIntent = new Intent(this, MainActivity.class);
            contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }


            else
                {
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_manage)
                        .setContentTitle("Smart Canister Notification")
                        .setContentText("Low Level.Time to Replenish. Only 10% left!....Item Added to Cart");

                notificationIntent = new Intent(this, MainActivity.class);
                contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
                addToCart();

        }

    }
}
