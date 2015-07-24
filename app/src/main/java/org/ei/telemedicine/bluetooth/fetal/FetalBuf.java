package org.ei.telemedicine.bluetooth.fetal;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.contec.jar.fhr01.DeviceCommand;
import com.contec.jar.fhr01.DeviceData;
import com.contec.jar.fhr01.DeviceDatas;
import com.contec.jar.fhr01.DevicePackManager;

import org.ei.telemedicine.bluetooth.Constants;
import org.ei.telemedicine.bluetooth.OnBluetoothResult;


public class FetalBuf {
    private static final String TAG = "com.testBlueTooth.FetalBuf";
    public static Vector<Integer> m_buf = null;
    private DevicePackManager m_DevicePackManager = new DevicePackManager();
    public static byte[] fetalData = new byte[20];
    OnBluetoothResult onResult;

    public FetalBuf() {
        m_buf = new Vector<Integer>();
    }

    public synchronized int Count() {
        return m_buf.size();
    }

    int mSettimeCount = 0;

    public synchronized void write(byte[] buf, int count,
                                   final OutputStream pOutputStream, Context context,
                                   final int deviceNum) throws Exception {
        if (context instanceof OnBluetoothResult) {
            onResult = (OnBluetoothResult) context;
        }

        int _receiveNum = m_DevicePackManager.arrangeMessage1(buf, count);
        // Log.e("Receive Num", _receiveNum + "");
        switch (_receiveNum) {
            case 0x0A:
                Log.e(TAG, "0x0A");
                byte[] rEQUEST_DATA = DeviceCommand.REQUEST_DATA;
                pOutputStream.write(rEQUEST_DATA);

                break;

            case 0x91:
                Log.e(TAG, "0x91");

                DeviceDatas deviceDatas11 = m_DevicePackManager.mDeviceDatas;
                Log.e(TAG, "Count =" + deviceDatas11.m_Data_Count);
                ArrayList<DeviceData> deviceDataList11 = deviceDatas11.mDatas;
                int size = deviceDataList11.size();

                DeviceData deviceData1 = deviceDataList11.get(size - 1);
                ArrayList<byte[]> result = deviceData1.mDatas;
                Log.e(TAG, "Result Size " + result.size());
                byte[] resultData = (result.size() == 0) ? null : result.get(result
                        .size() - 1);
                onResult.onResult(resultData, Constants.FET_DEVICE_NUM);
//			pOutputStream.write(DeviceCommand.DELETE_DATA);
                break;

        }
    }
}
