package org.ei.telemedicine.bluetooth.pulse;

import java.io.OutputStream;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.contec.cms50dj_jar.DeviceCommand;
import com.contec.cms50dj_jar.DeviceData50DJ_Jar;
import com.contec.cms50dj_jar.DevicePackManager;

import org.ei.telemedicine.bluetooth.Constants;
import org.ei.telemedicine.bluetooth.OnBluetoothResult;

public class PulseBuf {
	private static final String TAG = "com.testBlueTooth.Mtbuf";
	public static Vector<Integer> m_buf = null;
	private DevicePackManager m_DevicePackManager = new DevicePackManager();
	public static byte[] pulseData = new byte[20];
	OnBluetoothResult onResult;

	public PulseBuf() {
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
		switch (_receiveNum) {
		case 1:
			Log.i(TAG + "1", "1");

			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						byte[] correctionDateTime = DeviceCommand
								.correctionDateTime();
						pOutputStream.write(correctionDateTime);

					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			break;
		case 2:
			Log.i(TAG + "2", "2");

			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						pOutputStream.write(DeviceCommand.setPedometerInfo(
								"175", "75", 0, 24, 10000, 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			break;
		case 3:
			Log.i(TAG + "3", "3");

			break;
		case 4:
			Log.i(TAG + "4", "4");
			break;
		case 5:
			Log.i(TAG + "5", "5");

			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						DeviceData50DJ_Jar _djData = m_DevicePackManager
								.getDeviceData50dj();
						for (int i = 0; i < _djData.getmSp02DataList().size(); i++) {
							byte[] _data = _djData.getmSp02DataList().get(i);
							pulseData = (_data.length == 0) ? new byte[20]
									: _data;
						}
						onResult.onResult(pulseData, deviceNum);
						pOutputStream.write(DeviceCommand
								.dayPedometerDataCommand());
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();

			break;
		case 6:
			Log.i(TAG + "6", "6");
			pOutputStream.write(DeviceCommand.dataUploadSuccessCommand());
			break;
		case 7:
			Log.i(TAG + "7", "7");
			pOutputStream.write(DeviceCommand.dayPedometerDataCommand());
			break;
		case 8:
			Log.i(TAG + "8", "8");
			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						pOutputStream.write(DeviceCommand.getDataFromDevice());
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();

			break;
		case 9:
			Log.i(TAG + "9", "9");

			new Thread() {
				public void run() {
					try {
						Thread.sleep(100);
						byte[] dataFromDevice = DeviceCommand
								.getDataFromDevice();
						int _erc = m_DevicePackManager.arrangeMessage(
								dataFromDevice, dataFromDevice.length);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			break;
		case 10:
			Log.i(TAG + "10", "10");
			pOutputStream.write(DeviceCommand.dayPedometerDataSuccessCommand());

			break;
		case 11:
			Log.i(TAG + "11", "11");

			break;
		case 12:
			Log.i(TAG + "12", "12");
			break;
		case 13:
			Log.i(TAG + "13", "13");

			break;
		case 14:
			Log.i(TAG + "14", "14");

			break;
		case 15:
			Log.i(TAG + "15", "15");
			break;
		case 16:
			Log.i(TAG + "16", "16");
			break;
		case 17:
			Log.i(TAG + "17", "17");
			break;
		case 18:
			Log.i(TAG + "18", "18");
			break;
		case 19:
			Log.i(TAG + "19", "19");
			break;
		case 20:
			Log.i(TAG + "20", "20");
			pOutputStream.write(DeviceCommand.getDataFromDevice());
			break;
		default:
			onResult.onResult(null, Constants.NO_DEVICE);
			break;

		}
	}

}
