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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class teacher_invitation_recycleView extends RecyclerView.Adapter<teacher_invitation_recycleView.teacherviewHolder> {
    private Context context;
    private List<Tution> tutionList;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public teacher_invitation_recycleView(Context context, List<Tution> tutionList) {
        this.context = context;
        this.tutionList = tutionList;
    }


    @NonNull
    @Override
    public teacher_invitation_recycleView.teacherviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.tuition_card_inv,viewGroup,false);
        return  new teacherviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull teacher_invitation_recycleView.teacherviewHolder holder, final int position) {

        final Tution tution = tutionList.get(position);
        holder.tid.setText(tution.getTid());
        holder.address.setText(tution.getLocation());
        holder.clas.setText(tution.getClas());
        holder.mobile.setText(tution.getMobile());
        holder.subject.setText(tution.getSub());

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tution tution1 = tutionList.get(position);
                double lat=tution1.getLat();
                double lan=tution1.getLan();
                Intent intent =new Intent(context,MapsActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lan",lan);
                intent.putExtra("tid",tution1.getTid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tutionList.size();
    }
    class teacherviewHolder extends RecyclerView.ViewHolder{


        TextView tid,clas,group,address,subject,mobile;
        Button map;

        public teacherviewHolder(View itemView) {
            super(itemView);

            tid=itemView.findViewById(R.id.tid);
            address=itemView.findViewById(R.id.location);
            group=itemView.findViewById(R.id.group);
            clas=itemView.findViewById(R.id.clas);
            mobile=itemView.findViewById(R.id.mobile);
            subject=itemView.findViewById(R.id.subject);
            map=itemView.findViewById(R.id.map);
        }
    }
}
