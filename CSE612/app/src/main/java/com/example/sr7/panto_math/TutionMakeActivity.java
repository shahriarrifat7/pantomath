package com.example.sr7.panto_math;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class TutionMakeActivity extends AppCompatActivity {

    private EditText Location,Clas,Sub,Group,Mobile;
    private String loc,clas,sub,group,mobile,status,sid,tid;
    private Button Done;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tution_make);
        Location=(EditText)findViewById(R.id.location);
        Clas=(EditText)findViewById(R.id.clas);
        Sub=(EditText)findViewById(R.id.subject);
        Group=(EditText)findViewById(R.id.group);
        Mobile=(EditText)findViewById(R.id.mobile);
        Done=(Button)findViewById(R.id.add_tution);


        mAuth = FirebaseAuth.getInstance();

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc=Location.getText().toString();
                clas=Clas.getText().toString();
                sub=Sub.getText().toString();
                group=Group.getText().toString();
                mobile=Mobile.getText().toString();
                tid = UUID.randomUUID().toString();
                status="AVAILABLE";
                sid=mAuth.getUid();

                Tution tuition =new Tution(tid,sid,status,loc,mobile,clas,sub,group,0,0);

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("tuitions");
                myRef.child(tid).setValue(tuition);
                myRef = FirebaseDatabase.getInstance().getReference("invitations").child(sid).child(tid);
                myRef.setValue("");


                Intent intent = new Intent(getApplicationContext(),StudentHomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
