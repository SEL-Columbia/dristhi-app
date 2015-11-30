package org.ei.telemedicine.bluetooth.eet;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import org.ei.telemedicine.bluetooth.Constants;
import org.ei.telemedicine.bluetooth.OnBluetoothResult;

import cn.com.contec.jar.eartemperture.DeviceCommand;
import cn.com.contec.jar.eartemperture.DevicePackManager;
import cn.com.contec.jar.eartemperture.EarTempertureDataJar;


public class EETBuf {
    private static final String TAG = "EETBuf";
    public static Vector<Integer> m_buf = null;
    private DevicePackManager m_DevicePackManager = new DevicePackManager();
    OnBluetoothResult onResult;

    public EETBuf() {
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
        Log.e(TAG, "Receive Num" + _receiveNum + "");

        switch (_receiveNum) {
            case 1:
                Log.i(TAG + "1",
                        "New Data" + m_DevicePackManager.m_DeviceDatas.size());
                // for (int i = 0; i < m_DevicePackManager.m_DeviceDatas.size();
                // i++) {
                // Log.e(m_DevicePackManager.m_DeviceDatas.toString() + "\n", "");
                // }
                break;
            case 2:
                Log.i(TAG + "2", "Coming to 2*************"
                        + m_DevicePackManager.m_DeviceDatas.size());
                String data = null;
                for (int i = 0; i < m_DevicePackManager.m_DeviceDatas.size(); i++) {
                    Log.e(TAG, "Data" + m_DevicePackManager.m_DeviceDatas.toString()
                            + "\n");
                    ArrayList<EarTempertureDataJar> earTempertureDataJar = m_DevicePackManager.m_DeviceDatas;
                    EarTempertureDataJar earTempertureDataJar2 = earTempertureDataJar
                            .get(i);
                    data = earTempertureDataJar2.m_data+ "";
                }

                Log.e(TAG, "Data ------" + data);
                byte[] byteData = data.getBytes();

                m_DevicePackManager.m_DeviceDatas.clear();
                pOutputStream.write(DeviceCommand.command_delData());

                onResult.onResult(byteData, Constants.EET_DEVICE_NUM);

                break;
            case 5:
                Log.i(TAG, "Comintg 5");
                pOutputStream.write(DeviceCommand.command_closeBluetooth());
                break;
            case 7:
                Log.i(TAG + "7", "No New Data");
                break;
            case 13:
                Log.i(TAG + "13", "Stop");
                break;
            case 3:
                Log.i(TAG + "3", "Coming to 3");
                byte[] command_requestOneData = DeviceCommand
                        .command_requestDataOlnyOne();
                pOutputStream.write(command_requestOneData);
                // try {
                // Thread.sleep(300);
                // } catch (InterruptedException e) {// The last command to prevent
                // // blood pressure device can not
                // // receive
                // e.printStackTrace();
                // }
                // ArrayList<EarTempertureDataJar> m_DeviceDatas1 =
                // m_DevicePackManager.m_DeviceDatas;
                // for (int i = 0; i < m_DeviceDatas1.size(); i++) {
                // Log.e("Device Data", m_DeviceDatas1.toString());
                // }
                //
                break;
            case 4:
                Log.i(TAG + "4", "failure");

                break;

            case 8:
                Log.i(TAG + "8", "8");

                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(100);
                            byte[] verifyTime = DeviceCommand.command_VerifyTime();
                            pOutputStream.write(verifyTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    ;
                }.start();
                break;

            default:
                onResult.onResult(null, Constants.NO_DEVICE);
                break;

        }
    }
}
