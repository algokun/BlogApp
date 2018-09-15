package com.example.mohan.my_material_app;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    //Views
    CircleImageView acc_user_pic;
    ProgressBar acc_progressBar;
    TextView acc_user_name,acc_user_desc;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private String uid;
    public MenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //init views
        acc_progressBar = view.findViewById(R.id.progressBar);
        acc_user_pic = view.findViewById(R.id.acc_user_pic);
        acc_user_desc = view.findViewById(R.id.acc_user_desc);
        acc_user_name = view.findViewById(R.id.acc_user_name);
        //firebase init
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

        acc_progressBar.setVisibility(View.INVISIBLE);

        firestore.collection("profile_info").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    Glide.with(getContext()).load(task.getResult().getString("image")).into(acc_user_pic);
                    acc_user_name.setText(task.getResult().getString("name"));
                    acc_user_desc.setText(task.getResult().getString("status"));
                }
            }
        });

        return view;
    }
}
