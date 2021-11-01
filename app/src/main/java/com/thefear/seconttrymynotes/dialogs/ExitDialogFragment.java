package com.thefear.seconttrymynotes.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import com.thefear.seconttrymynotes.R;

public class ExitDialogFragment extends DialogFragment {

    public static final String TAG = "ExitDialogFragment";
    public static final String ARG_TITLE = "ExitDialogFragment";
    public static final String ARG_MESSAGE = "MESSAGE";
    public static final String KEY_RESULT = "KEY_RESULT";
    public static final String ARG_CHOICE = "ARG_CHOICE";

    public static ExitDialogFragment newInstance(int title, int Message) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, Message);
        ExitDialogFragment fragment = new ExitDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        @StringRes
        int title = requireArguments().getInt(ARG_TITLE);
        @StringRes
        int message = requireArguments().getInt(ARG_MESSAGE);

        Dialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.positive_putton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ARG_CHOICE, i);
                        getParentFragmentManager().setFragmentResult(KEY_RESULT, bundle);
                    }
                })
                .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ARG_CHOICE, i);
                        getParentFragmentManager().setFragmentResult(KEY_RESULT, bundle);
                    }
                })
                .create();

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }
}
