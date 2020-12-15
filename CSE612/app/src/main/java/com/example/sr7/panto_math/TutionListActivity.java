package com.example.sr7.panto_math;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TutionListActivity extends AppCompatActivity {
    List<Tution> TutionList;
    List<String> AppliedList=new ArrayList<>();
    private RecyclerView mRecyclerView;
    tuition_list_recycleView adapter;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tution_list);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycle_tution);
        mAuth= FirebaseAuth.getInstance();
        TutionList= new ArrayList<>();



        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRef = FirebaseDatabase.getInstance().getReference("invitations").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tid : dataSnapshot.getChildren()){
                    AppliedList.add(tid.getKey());
                }

                populateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void toastMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void populateList(){
        myRef = FirebaseDatabase.getInstance().getReference("tuitions");
        adapter = new tuition_list_recycleView(getApplicationContext(),TutionList);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot tutions : dataSnapshot.getChildren()) {

                    Tution tution = tutions.getValue(Tution.class);
                    if(!AppliedList.contains(tution.getTid())) {
                        TutionList.add(tution);
                    }
                    //toastMsg(String.valueOf(TutionList.size()));

                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

}
