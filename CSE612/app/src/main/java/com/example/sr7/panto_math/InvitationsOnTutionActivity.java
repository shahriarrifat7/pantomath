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

public class InvitationsOnTutionActivity extends AppCompatActivity {

    teacher_list_recycleView adapter;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<String> tutorsList=new ArrayList<>();
    private  List<Teacher> TeacherList;
    private RecyclerView mRecyclerView;
    public static String tid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations_on_tution);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycle_teacher);
        mAuth= FirebaseAuth.getInstance();
        TeacherList=new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent recievedIntent= getIntent();
        tid = recievedIntent.getStringExtra("tid");

        mAuth= FirebaseAuth.getInstance();
        toastMsg(tid);
        myRef = FirebaseDatabase.getInstance().getReference("tuition_tutors").child(tid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot Tid:dataSnapshot.getChildren()){
                    tutorsList.add(Tid.getKey());
                }
                populateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void populateList() {

        myRef = FirebaseDatabase.getInstance().getReference("teachers");
        adapter = new teacher_list_recycleView(getApplicationContext(),TeacherList);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot teacher : dataSnapshot.getChildren()) {

                    Teacher teacher1 = teacher.getValue(Teacher.class);
                    if(tutorsList.contains(teacher1.getUid())) {
                        TeacherList.add(teacher1);
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

    private void toastMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
