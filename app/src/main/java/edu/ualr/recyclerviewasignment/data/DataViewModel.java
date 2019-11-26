package edu.ualr.recyclerviewasignment.data;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;

public class DataViewModel extends ViewModel {

    private SortedList<DeviceListItem> mItems;

    public dataSetChangeListener mDataSetChangeListener;
    public interface dataSetChangeListener {
        public void OnNotifyDataSetChange();
    }

    public DataViewModel() {
        this.mItems = new SortedList<>(DeviceListItem.class, new SortedList.Callback<DeviceListItem>() {
            @Override
            public int compare(DeviceListItem o1, DeviceListItem o2) {

                if (o1.isSection() && !o2.isSection()) {
                    if (o1.getDeviceStatus().ordinal() <= o2.getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (!o1.isSection() && o2.isSection()) {
                    if (o1.getDeviceStatus().ordinal() < o2.getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if ((!o1.isSection() && !o2.isSection()) || (o1.isSection() && o2.isSection())) {
                    return o1.getDeviceStatus().ordinal() - o2.getDeviceStatus().ordinal();
                } else return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                mDataSetChangeListener.OnNotifyDataSetChange();
            }

            @Override
            public boolean areContentsTheSame(DeviceListItem oldItem, DeviceListItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(DeviceListItem item1, DeviceListItem item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {
               // notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
               // notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
               // notifyItemMoved(fromPosition, toPosition);
            }
        });

    }

    public void addAll(List<DeviceListItem> devices) {
        mItems.beginBatchedUpdates();
        for (int i = 0; i < devices.size(); i++) {
            mItems.add(devices.get(i));
        }
        mItems.endBatchedUpdates();
    }

    public DeviceListItem getDeviceListItem(int position){
        return mItems.get(position);
    }

    public int getItemsSize(){
        return mItems.size();
    }

    public void updateList(int position, Device device){
        mItems.updateItemAt(position, device);
    }



}
