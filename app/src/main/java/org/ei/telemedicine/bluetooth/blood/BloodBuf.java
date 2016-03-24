package org.ei.telemedicine.bluetooth.blood;

import android.content.Context;
import android.util.Log;

import org.ei.telemedicine.bluetooth.Constants;
import org.ei.telemedicine.bluetooth.OnBluetoothResult;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import cn.com.contec.jar.cmssxt.CmssxtDataJar;
import cn.com.contec.jar.cmssxt.DeviceCommand;
import cn.com.contec.jar.cmssxt.DevicePackManager;


public class BloodBuf {
    private static final String TAG = "BloodBuf";
    public static Vector<Integer> m_buf = null;
    private DevicePackManager m_DevicePackManager = new DevicePackManager();
    OnBluetoothResult onResult;

    public BloodBuf() {
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

        int _receiveNum = m_DevicePackManager.arrangeMessage(buf, count);
        Log.e(TAG, _receiveNum + "");

        switch (_receiveNum) {
            case 1:
                String data = null;
                ArrayList<CmssxtDataJar> m_deviceDatas = m_DevicePackManager.m_DeviceDatas;
//                for (int i = 0; i < m_deviceDatas.size(); i++) {
                if (m_deviceDatas.size() != 0) {
                    CmssxtDataJar cmssxtDataJar = m_deviceDatas.get(0);
                    double m_data = cmssxtDataJar.m_data;
                    data = m_data + "";
//                    result[0] = (byte) cmssxtDataJar.m_data;
//                }
                }
                onResult.onResult(data != null ? data.getBytes() : new byte[0], Constants.BLOOD_DEVICE_NUM);
                break;
            case 2:

                break;
            case 3:
                pOutputStream.write(DeviceCommand.command_requestData());
                break;
            case 8:

                pOutputStream.write(DeviceCommand.command_VerifyTime());
                break;
            case 9:
                pOutputStream.write(DeviceCommand.command_VerifyTimeSS());
                break;
            default:
                onResult.onResult(null, Constants.NO_DEVICE);
                break;

        }
    }
}
