package com.himanshu.smartcanister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartList;
    ArrayList<String> cartarraylist= new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList=(ListView) findViewById(R.id.cartList);
       // getData1();

        cartarraylist.add("oats");
        adapter=new ArrayAdapter(CartActivity.this,android.R.layout.simple_list_item_1,cartarraylist);
        cartList.setAdapter(adapter);



    }

    public  void getData1()
    {
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String item=dataSnapshot.getValue(String.class);
                cartarraylist.add(item);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter=new ArrayAdapter(CartActivity.this,android.R.layout.simple_list_item_1,cartarraylist);
        cartList.setAdapter(adapter);
    }

}
