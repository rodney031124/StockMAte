package com.stockmate.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.stockmate.app.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.card_about).setOnClickListener(v -> 
            Toast.makeText(getContext(), "About clicked", Toast.LENGTH_SHORT).show());
            
        view.findViewById(R.id.card_help).setOnClickListener(v -> 
            Toast.makeText(getContext(), "Help & Support clicked", Toast.LENGTH_SHORT).show());
            
        view.findViewById(R.id.card_privacy).setOnClickListener(v -> 
            Toast.makeText(getContext(), "Privacy clicked", Toast.LENGTH_SHORT).show());

        return view;
    }
}
