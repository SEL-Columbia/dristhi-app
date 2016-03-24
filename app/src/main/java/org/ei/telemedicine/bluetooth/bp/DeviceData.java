package org.ei.telemedicine.bluetooth.bp;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class DeviceData  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public int m_iYear;
	public int m_iMonth;
	public int m_iDay;
	public int m_iHour;
	public int m_iMinute;
	public int m_nSys;
	public int m_nMap;
	public int m_nDia;
	public int m_nHR;
	public int m_nTC;
	public Date mSaveDate;
	public int[] mDate;
	public static ArrayList<DeviceData> m_deviceDataList = new ArrayList<DeviceData>();

	public DeviceData(byte[] pack) {
	}
	public void setSaveDate() {
		mSaveDate = new Date(m_iYear,m_iMonth,m_iDay,m_iHour,m_iMinute);
	}
	public static void printDatas ()
	{
		for (int i = 0; i < m_deviceDataList.size(); i++) {
			DeviceData _DeviceData = m_deviceDataList.get(i);
			Log.i("com.testbluetooth", "����ʱ�䣺"+_DeviceData.m_iYear+"-"+_DeviceData.m_iMonth+"-"+_DeviceData.m_iDay
					+" "+_DeviceData.m_iHour+":00");
			Log.i("com.testbluetooth","��ݣ���ѹ��"+_DeviceData.m_nSys+"��ѹ��"+_DeviceData.m_nDia+"ƽ��ѹ��"+_DeviceData.m_nMap );
		}
	}
}
