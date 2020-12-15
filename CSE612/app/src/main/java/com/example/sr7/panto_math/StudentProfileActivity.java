package com.example.sr7.panto_math;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileActivity extends AppCompatActivity {

    private EditText address,mobile,institution,clas,group;
    private Button update;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private  Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        address=(EditText)findViewById(R.id.address);
        mobile=(EditText)findViewById(R.id.mobile);
        institution=(EditText)findViewById(R.id.institution);
        clas=(EditText)findViewById(R.id.clas);
        group=(EditText)findViewById(R.id.group);
        update=(Button)findViewById(R.id.update);

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference("students").child(mAuth.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                address.setText(student.getAddress().toString());
                mobile.setText(student.getMobile().toString());
                institution.setText(student.getInstitution().toString());
                clas.setText(student.getClas().toString());
                group.setText(student.getGroup().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(address)) {
                    student.setAddress(address.getText().toString());
                }
                if(!isEmpty(mobile)){
                    student.setMobile(mobile.getText().toString());
                }
                if(!isEmpty(group)){
                    student.setGroup(group.getText().toString());
                }
                if(!isEmpty(clas)){
                    student.setClas(clas.getText().toString());
                }
                if(!isEmpty(institution)){
                    student.setInstitution(institution.getText().toString());
                }
                myRef.setValue(student);
            }
        });

    }
    private boolean isEmpty(EditText editText){
        if(editText.getText().toString().trim().length() >0){
            return  false;
        }
        return true;
    }
}
