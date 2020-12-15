package com.example.sr7.panto_math;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText Username, Password;
    private TextView User_name, Pass_word, Sign_up;
    private Button Login_btn;
    private FirebaseAuth mAuth;
    private String password,email;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InvitaionActivity.user=null;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        setupUIViews();

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sign_up_final.class);
                startActivity(intent);
                finish();
            }
        });

        toastMsg(mAuth.getUid());

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate())
                    onPause();
                else {
                    password = Password.getText().toString();
                    email = Username.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser fuser = mAuth.getCurrentUser();
                            String uid = fuser.getUid();
                               myRef = FirebaseDatabase.getInstance().getReference("teachers").child(uid);

                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            Intent intent = new Intent(getApplicationContext(), TeacherMainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            chksudent();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                            else{
                                toastMsg(task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });



    }

    private void setupUIViews() {
        Username = (EditText) findViewById(R.id.etlUsername);
        Password = (EditText) findViewById(R.id.etlPassword);

        Login_btn = (Button) findViewById(R.id.btnLogin);
       // User_name = (TextView) findViewById(R.id.tvlUsername);
      //  Pass_word = (TextView) findViewById(R.id.tvlPassword);
        Sign_up   = (TextView) findViewById(R.id.tvlsignup);

    }


    private boolean validate() {
        boolean result = false;

        String name = Username.getText().toString();
        String pass = Password.getText().toString();


        if(name.isEmpty() || pass.isEmpty() ) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    private void chksudent(){
        myRef = FirebaseDatabase.getInstance().getReference("students").child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Intent intent = new Intent(getApplicationContext(),StudentHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void toastMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
