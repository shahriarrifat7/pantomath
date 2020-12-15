package com.example.sr7.panto_math;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InvitaionActivity extends AppCompatActivity {
    List<Tution> TutionList;
    List<String>TuitionAd= new ArrayList<>();
    private RecyclerView mRecyclerView;
    tuition_list_recycleView adapter2;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitaion);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycle_ad_tution);


        Intent recievedIntent= getIntent();
        user = recievedIntent.getStringExtra("user");
        if(user==null){
            user="teacher";
        }
        mAuth= FirebaseAuth.getInstance();
        TutionList= new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //myRef = FirebaseDatabase.getInstance().getReference("invitations").child(mAuth.getUid());

        TutionList.clear();
        TuitionAd.clear();
        adapter2 = new tuition_list_recycleView(getApplicationContext(),TutionList);
        populateTuitions(mAuth.getUid());

    }

    private void populateTuitions(String uid){
        myRef = FirebaseDatabase.getInstance().getReference("invitations").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot tutions : dataSnapshot.getChildren()) {
                    TuitionAd.add(tutions.getKey());
                }
                populateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateList(){
        myRef = FirebaseDatabase.getInstance().getReference("tuitions");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot tutions : dataSnapshot.getChildren()) {

                    Tution tution = tutions.getValue(Tution.class);
                        if(TuitionAd.contains(tution.getTid())){
                           TutionList.add(tution);
                            adapter2.notifyDataSetChanged();
                        }

                }
                mRecyclerView.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
