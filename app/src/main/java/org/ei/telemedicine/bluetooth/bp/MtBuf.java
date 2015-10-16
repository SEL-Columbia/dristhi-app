package org.ei.telemedicine.bluetooth.bp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.contec.jar.contec08a.DeviceCommand;
import com.contec.jar.contec08a.DevicePackManager;

import android.R.integer;
import android.content.Context;
import android.util.Log;

import org.ei.telemedicine.bluetooth.OnBluetoothResult;


public class MtBuf {
    private static final String TAG = "com.testBlueTooth.Mtbuf";
    public static Vector<Integer> m_buf = null;

    public static final int e_pack_pressure_back = 0x46;

    public MtBuf() {
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
                                   OutputStream pOutputStream, Context context, int deviceNum) throws Exception {

        int state = mPackManager.arrangeMessage(buf, count, mType);
        Log.e("Mtype", mType + "");
        int x = DevicePackManager.Flag_User;
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
                        pOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
                        break;
                    case 3:
                        mType = 1;

                        if (x == 0x11) {
                            mType = 7;
                        } else {
                            mType = 1;
                        }

                        pOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
                        break;
                }
                break;
            case 0x30:
                pOutputStream.write(DeviceCommand.Correct_Time());
                break;
            case 0x40:
                pOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
                break;
            case e_pack_pressure_back:
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ArrayList<byte[]> _dataList = mPackManager.mDeviceData.mData_blood;
                int _size = _dataList.size();
                DeviceData _mData = new DeviceData(null);
                byte[] result = null;
                for (int i = 0; i < _size; i++) {
                    byte[] _byte = _dataList.get(i);
                    byte[] _data = new byte[10];
                    result = _byte;
                    int hiPre = ((_byte[5] << 8) | (_byte[6] & 0xff)) & 0xffff;
                    int lowPre = _byte[7] & 0xff;
                    Log.e(TAG, "-------Pressur-------" + result[1] + "============low--------------" + result[2]);
                }
                if (context instanceof OnBluetoothResult) {
                    onResult = (OnBluetoothResult) context;
                    onResult.onResult(result, deviceNum);
                } else {
                    Log.e(TAG, "Error" + "Must Implement OnResult Interface");
                }
                if (_size == 0) {
//				pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

                    Log.e(TAG, "-------No Pressure-------");

                } else {
//				pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

                }


                break;
        }

    }

}
