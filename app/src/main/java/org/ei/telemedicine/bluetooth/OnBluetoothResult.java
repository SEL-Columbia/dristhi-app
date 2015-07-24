package org.ei.telemedicine.bluetooth;

public interface OnBluetoothResult {
	public void onResult(byte[] resultData, int deviceNum);
}
