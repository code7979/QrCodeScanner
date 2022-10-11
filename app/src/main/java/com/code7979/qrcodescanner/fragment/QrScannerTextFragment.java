package com.code7979.qrcodescanner.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.code7979.qrcodescanner.MainViewModel;
import com.code7979.qrcodescanner.R;

public class QrScannerTextFragment extends Fragment {
    private static final CharSequence CLIPBOARD_LABEL = "com.code7979.qrcodescanner.COPY_QR_TEXT";
    private MainViewModel viewModel;
    private TextView textView;
    private Button copyButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        textView = view.findViewById(R.id.tv_result);
        copyButton = view.findViewById(R.id.btn_copy);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpToolBar(view);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), qrText -> textView.setText(qrText));

        copyButton.setOnClickListener(v -> copyText());

    }

    private void copyText() {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(CLIPBOARD_LABEL, textView.getText().toString());
        clipboard.setPrimaryClip(clipData);

        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show();
    }

    private void setUpToolBar(View view) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }
}
