package org.ei.telemedicine.bluetooth.bp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.contec.jar.contec08a.DeviceCommand;
import com.contec.jar.contec08a.DevicePackManager;

import org.ei.telemedicine.bluetooth.OnBluetoothResult;


public class BPBuf {
    private static final String TAG = "com.testBlueTooth.Mtbuf";
    public static Vector<Integer> m_buf = null;

    public static final int e_pack_pressure_back = 0x46;

    public BPBuf() {
        m_buf = new Vector<Integer>();
    }

    public synchronized int Count() {
        return m_buf.size();
    }

    DevicePackManager mPackManager = new DevicePackManager();
    private DeviceData mDeviceData;
    public static final int e_pack_hand_back = 0x48;
    public static final int e_pack_oxygen_back = 0x47;
    private int mType = 0;
    OnBluetoothResult onResult;

    public synchronized void write(byte[] buf, int count,
                                   OutputStream pOutputStream, Context context, int deviceNum)
            throws Exception {

        int state = mPackManager.arrangeMessage(buf, count, mType);
        int x = DevicePackManager.Flag_User;
        Log.e("BP Buf", mType + "");
        switch (state) {
            case e_pack_hand_back:
                switch (mType) {
                    case 9:
                        mType = 5;
                        pOutputStream.write(DeviceCommand.DELETE_BP());
                        break;
                    case 0:
                        mType = 3;
                        pOutputStream.write(DeviceCommand.correct_time_notice);
                        break;
                    case 1:
                        mType = 2;
                        pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
                        break;
                    // case 7:
                    // mType = 8;
                    // pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
                    // break;
                    // case 2:
                    // mType = 5;
                    // pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
                    // break;
                    // case 8:
                    // mType = 5;
                    // pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
                    // break;
                    case 3:
                        mType = 1;

                        if (x == 0x11) {
                            mType = 7;// Three user
                        } else {
                            mType = 1;// Single-user
                        }
                        pOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
                        break;
                }
                break;
            case 0x30:// Confirm proper correction time

                pOutputStream.write(DeviceCommand.Correct_Time());
                break;
            case 0x40:// Correction time correctly

                pOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                break;
            case e_pack_pressure_back:

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {// The last command to prevent
                    // blood pressure device can not
                    // receive
                    e.printStackTrace();
                }
                ArrayList<byte[]> _dataList = mPackManager.mDeviceData.mData_blood;
                ArrayList<byte[]> _dataLists = mPackManager.mDeviceData.mData_normal_blood;
                ArrayList<byte[]> _dataList2s = mPackManager.mDeviceData.mData_oxygen;
                for (int ii = 0; ii < _dataLists.size(); ii++) {
                    Log.e("Bp Data ++++ii", _dataLists.get(ii)[0] + "----" + _dataLists.get(ii)[1] + "-------" + _dataLists.get(ii)[2]);
                }
                for (int ij = 0; ij < _dataList2s.size(); ij++) {
                    Log.e("Bp Data //////ij", _dataLists.get(ij)[0] + "----" + _dataLists.get(ij)[1] + "-------" + _dataLists.get(ij)[2]);
                }
                byte[] result = null;
                for (int i = 0; i < _dataList.size(); i++) {

                    result = _dataList.get(i);
                    Log.e("Bp Data", result + "");
                    int i1 = (result[0] << 8 | result[1]) & 255;

                    Log.e(TAG, "blood sys--0--" + i + result[0] + "-------+++++" + i1);
                    Log.e(TAG, "blood sys--1--" + i + result[1] + "-------+++++");
                    Log.e(TAG, "blood Di" + i + result[2] + "");
                }
                if (context instanceof OnBluetoothResult) {
                    onResult = (OnBluetoothResult) context;
                    onResult.onResult(result, deviceNum);
                } else {
                    Log.e(TAG, "Error" + "Must Implement OnResult Interface");
                }
                int _size = _dataList.size();
//                DeviceData _mData = new DeviceData(null);
                for (int i = 0; i < _size; i++) {
                    byte[] _data = _dataList.get(i);
//                    byte[] _data = new byte[10];

                    int hiPre = ((_data[5] << 8) | (_data[6] & 0xff)) & 0xffff;// High
                    // pressure
                    int lowPre = _data[7] & 0xff;// Low pressure
                    Log.e(TAG, "-------Pressur-------" + hiPre + "============low" + lowPre);
                }
                if (_size == 0) {
                    pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());
                    Log.e(TAG, "-------Pressure-------");

                } else {
                    pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());
                }

                break;
        }

    }
}
