package com.example.sr7.panto_math;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_final extends AppCompatActivity {

    private EditText Username, Password, Fullname, Address,Institution,Foi,Quaification,Mobile,Group,Clas;
    private Button regButton;
    //private TextView user_name, pass_word, full_name, add_ress;
    private LinearLayout student_spec,teacher_spec;
    private FirebaseAuth firebaseAuth;
    private String email,name,address,qualification,foi,institution,mobile,gender,uid,clas,group;
    private boolean stdnt=false,tchr=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_final);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( validate() ) {
                   ///upload data to the db
                   final String usernm = Username.getText().toString().trim();
                   String userps = Password.getText().toString().trim();

                   firebaseAuth.createUserWithEmailAndPassword(usernm, userps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()) {
                               Toast.makeText(Sign_up_final.this, "Registration successful", Toast.LENGTH_SHORT).show();

                               uid=task.getResult().getUser().getUid();
                               email=usernm;
                               name=Fullname.getText().toString();
                               address=Address.getText().toString();
                               qualification=Quaification.getText().toString();
                               foi=Foi.getText().toString();
                               institution=Institution.getText().toString();
                               mobile=Mobile.getText().toString();
                               clas=Clas.getText().toString();
                               group=Group.getText().toString();

                               if(stdnt){
                                   Student student =new Student(name,institution,clas,group,uid,address,mobile,0,0);
                                   DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("students");
                                   myRef.child(uid).setValue(student);
                               }
                               if(tchr){
                                   Teacher teacher =new Teacher(name,qualification,foi,address,mobile,institution,gender,uid,0,0);
                                   DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("teachers");
                                   myRef.child(uid).setValue(teacher);
                               }


                           }
                           else{
                               toastMsg(task.getException().getMessage());
                               Toast.makeText(Sign_up_final.this, "Registration failed", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(Sign_up_final.this, LoginActivity.class));
                           }
                       }
                   });

                   startActivity(new Intent(Sign_up_final.this, LoginActivity.class));
               }
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // Pirates are the best
                    gender="male";
                    break;
            case R.id.radio_female:
                if (checked)
                    // Ninjas rule
                    gender="female";
                    break;
        }
    }
    public void onRadioButtonClicked1(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_student:
                if (checked)
                    student_spec.setVisibility(View.VISIBLE);
                    teacher_spec.setVisibility(View.GONE);
                    stdnt=true;
                    tchr=false;
                    break;

            case R.id.radio_teacher:
                if (checked)
                    student_spec.setVisibility(View.GONE);
                    teacher_spec.setVisibility(View.VISIBLE);
                    stdnt=false;
                    tchr=true;
                    break;
        }
    }
    private void setupUIViews() {
        Username = (EditText) findViewById(R.id.etUsername);
        Password = (EditText) findViewById(R.id.etPassword);
        Fullname = (EditText) findViewById(R.id.etFullname);
        Address  = (EditText) findViewById(R.id.etAddress);
        Mobile  = (EditText) findViewById(R.id.mobile);
        Foi  = (EditText) findViewById(R.id.foi);
        Institution  = (EditText) findViewById(R.id.institute);
        Quaification  = (EditText) findViewById(R.id.qualification);
        Group  = (EditText) findViewById(R.id.group);
        Clas  = (EditText) findViewById(R.id.clas);
        teacher_spec  = (LinearLayout) findViewById(R.id.teacher_spec);
        student_spec  = (LinearLayout) findViewById(R.id.student_spec);

        teacher_spec.setVisibility(View.GONE);
        student_spec.setVisibility(View.GONE);

        regButton = (Button) findViewById(R.id.btnRegister);
        //user_name = (TextView) findViewById(R.id.tvUsername);
        //pass_word = (TextView) findViewById(R.id.tvPassword);
        //full_name = (TextView) findViewById(R.id.tvFullname);
        //add_ress  = (TextView) findViewById(R.id.tvAddress);
    }

    private boolean validate() {
        boolean result = false;

        String name = Username.getText().toString();
        String pass = Password.getText().toString();
        String f_name = Fullname.getText().toString();
        String adrs = Address.getText().toString();

        if(name.isEmpty() || pass.isEmpty() || f_name.isEmpty() || adrs.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }
    private void toastMsg(String str){
        Toast.makeText(Sign_up_final.this, str, Toast.LENGTH_SHORT).show();
    }
}
