package com.NHC.Discount;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class feedActivity extends AppCompatActivity {

    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    ArrayList<String> userimageFromFB;
    ArrayList<String> usercommentFromFB;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_post){

            Intent intent = new Intent(getApplicationContext(), uploadActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        listView = findViewById(R.id.listview1);
        useremailFromFB = new ArrayList<String>();
        usercommentFromFB = new ArrayList<String>();
        userimageFromFB = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        adapter = new PostClass(useremailFromFB, usercommentFromFB, userimageFromFB, this);
        listView.setAdapter(adapter);

        getDataFromFirebase();

    }


    public void getDataFromFirebase(){

        DatabaseReference newReference = firebaseDatabase.getReference("Posts");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //System.out.println("FBV children: " + dataSnapshot.getChildren()); //com.google.firebase.database.DataSnapshot$1@c4d2b0f
                //System.out.println("FBV key: " + dataSnapshot.getKey()); //Posts
                //System.out.println("FBV value: " + dataSnapshot.getValue()); //{9da4b8da-2d65-4c92-999a-2c0c67927b99={downloadURL=https://firebasestorage.googleapis.com/v0/b/instaclonefirebasejava-89fe7.appspot.com/o/images%2Ff69a5ff5-1fb5-4e0c-ad2c-678843084bd9.jpg?alt=media&token=2c63e75d-bd8c-4eeb-a6ce-82d484340364, usercomment=test comment, useremail=cemilecerenerdem@gmail.com}}
                    //Value = bütün veriler
                    //json formatında geliyor
                //System.out.println("FBV priority: " + dataSnapshot.getPriority()); //null

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    //System.out.println("FBV value: " + ds.getValue()); //bütün childler içerisinde bir loopa girilir. sırayla okuma yapar

                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    useremailFromFB.add(hashMap.get("useremail")); //kolon adı
                    usercommentFromFB.add(hashMap.get("usercomment"));
                    userimageFromFB.add(hashMap.get("downloadURL"));
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
