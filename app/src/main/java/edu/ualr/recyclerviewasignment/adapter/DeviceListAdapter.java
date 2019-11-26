package edu.ualr.recyclerviewasignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewasignment.R;
import edu.ualr.recyclerviewasignment.data.DataViewModel;
import edu.ualr.recyclerviewasignment.data.DeviceDataFormatTools;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.model.DeviceSection;

/**
 * Created by irconde on 2019-10-04.
 */
public class DeviceListAdapter extends RecyclerView.Adapter {

    private static final int DEVICE_VIEW = 0;
    private static final int SECTION_VIEW = 1;

    private Context mContext;
    public DataViewModel dataViewModel;

    private DeviceListAdapter.OnDetailOpenListener mOnDetailOpenListener;
    public interface OnDetailOpenListener  {
        public void onOpen(int position);
    }

    public void setOnDetailOpenListener (OnDetailOpenListener pListener) {
        mOnDetailOpenListener = pListener;
    }

    public DeviceListAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == DEVICE_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
            vh = new DeviceViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_header, parent, false);
            vh = new SectionViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeviceListItem item = dataViewModel.getDeviceListItem(position);
        if (holder instanceof DeviceViewHolder) {
            DeviceViewHolder view = (DeviceViewHolder) holder;
            Device device = (Device) item;
            view.name.setText(device.getName());
            view.elapsedTimeLabel.setText(DeviceDataFormatTools.getTimeSinceLastConnection(mContext, device));
            DeviceDataFormatTools.setDeviceStatusMark(mContext, view.statusMark, device);
            DeviceDataFormatTools.setDeviceThumbnail(mContext, view.image, device);
            DeviceDataFormatTools.setConnectionBtnLook(mContext, view.connectBtn, device.getDeviceStatus());
        } else {
            SectionViewHolder view = (SectionViewHolder) holder;
            DeviceSection section = (DeviceSection) item;
            view.title_section_label.setText(section.getLabel());
        }
    }


    public int getItemCount() {
        return dataViewModel.getItemsSize();
    }

    @Override
    public int getItemViewType(int position) {
        return dataViewModel.getDeviceListItem(position).isSection() ? SECTION_VIEW : DEVICE_VIEW;
    }

    public Device getDevice( int position){
        return (Device) dataViewModel.getDeviceListItem(position);
    }

    private class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title_section_label;

        public SectionViewHolder(View v) {
            super(v);
            title_section_label = v.findViewById(R.id.title_section_label);
        }
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout image;
        private ImageView statusMark;
        private TextView name;
        private TextView elapsedTimeLabel;
        private ImageButton connectBtn;
        private RelativeLayout parent;

        public DeviceViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            statusMark = v.findViewById(R.id.status_mark);
            name = v.findViewById(R.id.name);
            elapsedTimeLabel = v.findViewById(R.id.elapsed_time);
            connectBtn = v.findViewById(R.id.device_connect_btn);
            connectBtn.setOnClickListener(this);
            parent = v.findViewById(R.id.device_item_container);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDetailOpenListener.onOpen(getAdapterPosition());
                }
            });
        }




        @Override
        public void onClick(View view) {

            toggleConnection();
        }

        public void getItemInformation(){
            if(!dataViewModel.getDeviceListItem(getAdapterPosition()).isSection()) {
               Device device = (Device) dataViewModel.getDeviceListItem(getAdapterPosition());
            }
        }



        private void toggleConnection() {

            if(!dataViewModel.getDeviceListItem(getAdapterPosition()).isSection()) {
                Device device = (Device) dataViewModel.getDeviceListItem(getAdapterPosition());
                Device.DeviceStatus deviceStatus = device.getDeviceStatus();
                if (deviceStatus == Device.DeviceStatus.Connected) {
                    device.setDeviceStatus(Device.DeviceStatus.Ready);
                    device.setLastConnection(new Date());
                } else if (deviceStatus == Device.DeviceStatus.Ready) {
                    device.setDeviceStatus(Device.DeviceStatus.Connected);
                }
                dataViewModel.updateList(getAdapterPosition(), device);
            }
        }
    }
}
