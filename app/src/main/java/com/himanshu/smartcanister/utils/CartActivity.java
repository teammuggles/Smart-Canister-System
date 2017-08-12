package com.himanshu.smartcanister.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.himanshu.smartcanister.CanisterAdapter;
import com.himanshu.smartcanister.CartCanisterAdapter;
import com.himanshu.smartcanister.R;
import com.himanshu.smartcanister.models.Canister;
import com.himanshu.smartcanister.models.CanisterCart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private CartCanisterAdapter myAdapter;
    private RecyclerView recyclerView;
    private static List<CanisterCart> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.cart);
        myAdapter=new CartCanisterAdapter (this,getData());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private static List<CanisterCart> getData () {
         data = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name=dataSnapshot.getValue(String.class);
                switch(name)
                {
                    case "oats":
                        data.add(new CanisterCart("Oats","https://firebasestorage.googleapis.com/v0/b/smart-canister.appspot.com/o/61BBuz1CBWL._SX522_.jpg?alt=media&token=986b1571-4aa3-486d-b91e-bec5e97b4acd"));
                }
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

        return data;
    }

}
