package edu.ualr.recyclerviewasignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.data.DataViewModel;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceDetail;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();

    }


    private void initRecyclerView(){
        mAdapter = new DeviceListAdapter(this);
        mDataViewModel = new DataViewModel();
        mAdapter.setOnDetailOpenListener(new DeviceListAdapter.OnDetailOpenListener() {
            @Override
            public void onOpen(int position) {
                createDeviceDetail(position);
            }
        });
        mRecyclerView = findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mDataViewModel.mDataSetChangeListener= new DataViewModel.dataSetChangeListener() {
            @Override
            public void OnNotifyDataSetChange() {
                mAdapter.notifyDataSetChanged();
            }
        };

        mAdapter.dataViewModel = mDataViewModel;
        mAdapter.dataViewModel.addAll(DataGenerator.getDevicesDataset(5));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


    }

    public void onOpen(){

    }

    public void createDeviceDetail(int position){

        Device device = new Device(mAdapter.getDevice(position));

        final DeviceDetail deviceDetail = new DeviceDetail(device);

        deviceDetail.mSaveButtonListener = new DeviceDetail.OnSaveClickedListener() {
            @Override
            public void onClick() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(deviceDetail);
                ft.commit();
            }

            @Override
            public void returnData(){

            }
        };

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, deviceDetail);
// or ft.add(R.id.your_placeholder, new FooFragment());
        ft.commit();

    }

}
