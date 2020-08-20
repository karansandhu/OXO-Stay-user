package com.user.oxostay.screens;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.user.oxostay.R;
import com.user.oxostay.adapter.FavouriteAdapter;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    RelativeLayout rl_privacy,rl_terms,rl_change_pass,rl_logout,rl_wallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    public void initView(View view){

        rl_privacy = (RelativeLayout) view.findViewById(R.id.rl_privacy);
        rl_wallet = (RelativeLayout) view.findViewById(R.id.rl_wallet);
        rl_logout = (RelativeLayout) view.findViewById(R.id.rl_logout);
        rl_terms = (RelativeLayout) view.findViewById(R.id.rl_terms);
        rl_change_pass = (RelativeLayout) view.findViewById(R.id.rl_change_pass);
        rl_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),WalletActivity.class);
                startActivity(intent);
            }
        });
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logoutDialog();
            }
        });
        rl_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),ChangePassActivity.class);
                startActivity(intent);
            }
        });
        rl_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),TermsActivity.class);
                startActivity(intent);
            }
        });
        rl_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),PrivacyActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logoutDialog(){
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // Include dialog.xml file
        dialog.setContentView(R.layout.logout_dialog);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        TextView tv_no = (TextView) dialog.findViewById(R.id.tv_no);
        TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);
//        text.setText("Custom dialog Android example.");
//        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
//        image.setImageResource(R.drawable.image0);

        dialog.show();
        // if decline button is clicked, close the custom dialog
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }

}

