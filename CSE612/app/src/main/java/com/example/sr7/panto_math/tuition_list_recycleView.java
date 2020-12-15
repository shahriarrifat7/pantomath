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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class tuition_list_recycleView extends RecyclerView.Adapter<tuition_list_recycleView.tuitionsviewHolder> {
    private Context context;
    private List<Tution> tutionList;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public tuition_list_recycleView(Context context, List<Tution> tutionList) {
        this.context = context;
        this.tutionList = tutionList;
    }
    
    @NonNull
    @Override
    public tuitionsviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.tuition_card,viewGroup,false);
        return  new tuitionsviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final tuition_list_recycleView.tuitionsviewHolder holder, final int position) {

        final Tution tution = tutionList.get(position);
        holder.tid.setText(tution.getTid());
        holder.loc.setText(tution.getLocation());
        holder.sub.setText(tution.getSub());
        holder.clas.setText(tution.getClas());
        holder.mobile.setText(tution.getMobile());

        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth= FirebaseAuth.getInstance();
                String uid=mAuth.getUid();
                myRef = FirebaseDatabase.getInstance().getReference("invitations").child(uid);
                myRef.child(holder.tid.getText().toString()).setValue("");
                myRef = FirebaseDatabase.getInstance().getReference("invitations").child(tution.getSid()).child(tution.getTid());
                myRef.child(uid).setValue("");
                myRef = FirebaseDatabase.getInstance().getReference("tuition_tutors").child(holder.tid.getText().toString());
                myRef.child(uid).setValue("");
                notifyItemRemoved(holder.getAdapterPosition());
                tutionList.remove(position);
                notifyItemRangeChanged(position,tutionList.size());
                tutionList.clear();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,InvitationsOnTutionActivity.class);
                Tution tution = tutionList.get(position);
                intent.putExtra("tid",tution.getTid());
                //Toast.makeText(context,tution.getTid()+" is tid", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tutionList.size();
        //return 0;
    }
    class tuitionsviewHolder extends RecyclerView.ViewHolder{


        TextView tid,loc,sub,clas,mobile;
        Button apply;

        public tuitionsviewHolder(View itemView) {
            super(itemView);

            tid=itemView.findViewById(R.id.tid);
            loc=itemView.findViewById(R.id.location);
            sub=itemView.findViewById(R.id.subject);
            clas=itemView.findViewById(R.id.clas);
            mobile=itemView.findViewById(R.id.mobile);
            apply=itemView.findViewById(R.id.apply);
            try {
                if (InvitaionActivity.user.equalsIgnoreCase("student")) {
                    apply.setVisibility(View.GONE);
                }
            }catch (Exception e){
                //
                apply.setVisibility(View.VISIBLE);
            }
        }
    }

}
