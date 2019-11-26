package edu.ualr.recyclerviewasignment.model;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.ualr.recyclerviewasignment.R;

public class DeviceDetail extends Fragment {

    private MaterialButton saveButton;
    private ImageView icon;
    private ImageView statusMark;
    private TextView deviceName;
    private TextView deviceStatus;
    private Spinner spinner;

    public OnSaveClickedListener mSaveButtonListener;
    public interface OnSaveClickedListener {
        public void onClick();
        public void returnData();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        saveButton = view.findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButtonListener.returnData();
                mSaveButtonListener.onClick();
            }
        });

        icon = view.findViewById(R.id.image_icon);
        statusMark = view.findViewById(R.id.detail_status_mark);
        deviceName = view.findViewById(R.id.detail_device_name_edittext);
        deviceStatus = view.findViewById(R.id.detail_device_status);
        spinner = view.findViewById(R.id.device_type_spinner);

    }

    public DeviceDetail(Device device){
        System.out.println("HIT");
        //deviceName.setText(device.getName());
    }


}
