

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

public class TeacherProfileActivity extends AppCompatActivity {
    private EditText address,mobile,institution,foi,qualification;
    private Button update;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private Teacher teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        address=(EditText)findViewById(R.id.address);
        mobile=(EditText)findViewById(R.id.mobile);
        institution=(EditText)findViewById(R.id.institution);
        foi=(EditText)findViewById(R.id.foi);
        qualification=(EditText)findViewById(R.id.qualification);
        update=(Button)findViewById(R.id.update);

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference("teachers").child(mAuth.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacher = dataSnapshot.getValue(Teacher.class);
                address.setText(teacher.getAddress().toString());
                mobile.setText(teacher.getMobile().toString());
                institution.setText(teacher.getInstitution().toString());
                qualification.setText(teacher.getQualification().toString());
                foi.setText(teacher.getFoi().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(address)) {
                    teacher.setAddress(address.getText().toString());
                }
                if(!isEmpty(mobile)){
                    teacher.setMobile(mobile.getText().toString());
                }
                if(!isEmpty(foi)){
                    teacher.setFoi(foi.getText().toString());
                }
                if(!isEmpty(qualification)){
                    teacher.setQualification(qualification.getText().toString());
                }
                if(!isEmpty(institution)){
                    teacher.setInstitution(institution.getText().toString());
                }
                myRef.setValue(teacher);
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
