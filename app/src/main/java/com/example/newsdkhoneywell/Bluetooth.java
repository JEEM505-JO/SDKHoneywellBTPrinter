package com.example.newsdkhoneywell;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bluetooth extends ViewModel {

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    MutableLiveData<Boolean> enable;
    MutableLiveData<List<String>> name;
    ArrayList<String> _name;
    MutableLiveData<List<String>> mac;
    ArrayList<String> _mac;

    private static final int REQUEST_ENABLE_BT = 100;


    public Bluetooth() {
        enable = new MutableLiveData<Boolean>();
        name = new MutableLiveData<>();
        mac = new MutableLiveData<>();
        init();
    }
    public MutableLiveData<List<String>> getDevice(){
        return name;
    }
    public void init(){
        paredDevice();
        getstatus();
        name.setValue(_name);
        mac.setValue(_mac);
    }


    @SuppressLint("MissingPermission")
    public boolean getstatus() {
        if (adapter.isEnabled()) {
            paredDevice();
            Intent enableint = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enable.postValue(true);
            return adapter.disable();
        }else{
            adapter.isEnabled();
            return false;
        }
    }

    public void paredDevice() {

        @SuppressLint("MissingPermission")
        Set<BluetoothDevice> parendevices = adapter.getBondedDevices();
        if (parendevices.size() > 0) {
            for (BluetoothDevice device : parendevices) {
                @SuppressLint("MissingPermission")
                String devicenamebl = device.getName();
                String deviceHardwareAddress = device.getAddress();// MAC address
                name.observeForever(new Observer<List<String>>() {
                                        @Override
                                        public void onChanged(List<String> strings) {
                                            strings.add(devicenamebl);
                                        }
                                    }

                );

                mac.observeForever(new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        strings.add(deviceHardwareAddress);
                    }
                });
            }
        }
    }
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);



@SuppressLint("MissingPermission")
private final BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    name.observeForever(new Observer<List<String>>() {
                                            @Override
                                            public void onChanged(List<String> strings) {
                                                strings.add(deviceName);
                                            }
                                        }
                    );
                    mac.observeForever(new Observer<List<String>>() {
                        @Override
                        public void onChanged(List<String> strings) {
                            strings.add(deviceHardwareAddress);
                        }
                    });
                }
            }
        };


    }



