package com.example.marketmanager.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marketmanager.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wonderkiln.camerakit.CameraView;


public class HomeFragment extends Fragment {
    CameraView cameraView;
    Button btnScan, btnInvoice;
    AlertDialog waitingDialog;
    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity();
        btnScan = view.findViewById(R.id.btn_scan);
        btnInvoice = view.findViewById(R.id.btn_billprint);
        cameraView = view.findViewById(R.id.camera_view);

        IntentIntegrator  intentIntegrator = new IntentIntegrator(getActivity());

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    if(result != null) {
                        if(result.getContents() == null) {
                            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        });
    }



}