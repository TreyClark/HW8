package edu.ualr.recyclerviewasignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.data.DataViewModel;
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
        mAdapter.setOnDetailOpenListener(new DeviceListAdapter.OnDetailOpenListener() {
            @Override
            public void onOpen() {
                createDeviceDetail();
            }
        });
        mRecyclerView = findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mDataViewModel = new DataViewModel();
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

    public void createDeviceDetail(){
        final DeviceDetail device = new DeviceDetail();
        device.mSaveButtonListener = new DeviceDetail.OnSaveClickedListener() {
            @Override
            public void onClick() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(device);
                ft.commit();
            }
        };

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, device);
// or ft.add(R.id.your_placeholder, new FooFragment());
        ft.commit();

    }

}
