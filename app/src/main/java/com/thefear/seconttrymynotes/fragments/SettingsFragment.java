package com.thefear.seconttrymynotes.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.thefear.seconttrymynotes.MainActivity;
import com.thefear.seconttrymynotes.R;

public class SettingsFragment extends Fragment {

    MaterialButton themesButton;
    MaterialButton cashButton;
    MaterialButton aboutButton;
    MaterialButton menuButton;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        themesButton = view.findViewById(R.id.themes_button);
        themesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Активирован вызов перехода на выбор темы", Toast.LENGTH_SHORT).show();
            }
        });
        cashButton = view.findViewById(R.id.cash_button);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Активирован вызов перехода на экран кэша", Toast.LENGTH_SHORT).show();
            }
        });
        aboutButton = view.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Активирован вызов перехода на экран сведений о приложении", Toast.LENGTH_SHORT).show();
            }
        });
        menuButton = view.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Активирован вызов перехода на главное меню", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
