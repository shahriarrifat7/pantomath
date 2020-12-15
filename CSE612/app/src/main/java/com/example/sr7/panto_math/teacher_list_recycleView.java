package com.example.sr7.panto_math;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class teacher_list_recycleView extends RecyclerView.Adapter<teacher_list_recycleView.teacherviewHolder> {
    private Context context;
    private List<Teacher> teacherList;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public teacher_list_recycleView(Context context, List<Teacher> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public teacherviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.teacher_card,viewGroup,false);
        return  new teacherviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final teacher_list_recycleView.teacherviewHolder holder, final int position) {

        final Teacher teacher = teacherList.get(position);
        holder.name.setText(teacher.getName());
        holder.address.setText(teacher.getAddress());
        holder.qualification.setText(teacher.getQualification());
//        holder.institution.setText(teacher.getInstitution());
        holder.mobile.setText(teacher.getMobile());
        holder.gender.setText(teacher.getGender());
        holder.foi.setText(teacher.getFoi());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MapsActivity.class);


                intent.putExtra("teacherId",teacherList.get(position).getUid());
                intent.putExtra("tid",InvitationsOnTutionActivity.tid);
                //Toast.makeText(context,InvitationsOnTutionActivity.tid+" "+teacherList.get(position).getUid(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher teacher=teacherList.get(position);
                mAuth= FirebaseAuth.getInstance();
                myRef = FirebaseDatabase.getInstance().getReference("tuition_tutors").child(InvitationsOnTutionActivity.tid).child(teacher.uid);
                myRef.removeValue();
                notifyItemRemoved(holder.getAdapterPosition());
                teacherList.remove(position);
                notifyItemRangeChanged(position,teacherList.size());
                teacherList.clear();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
        //return 0;
    }
    class teacherviewHolder extends RecyclerView.ViewHolder{


        TextView name,foi,institution,qualification,mobile,address,gender;
        Button accept,reject;

        public teacherviewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            gender=itemView.findViewById(R.id.gender);
            address=itemView.findViewById(R.id.address);
            foi=itemView.findViewById(R.id.foi);
            qualification=itemView.findViewById(R.id.qualification);
            mobile=itemView.findViewById(R.id.mobile);
            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);
        }
    }

}
