package org.ei.telemedicine.bluetooth;

import java.util.UUID;

public interface Constants {

	public static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final int NO_DEVICE = 100;

	public static final String PULSE_DEVICE = "SpO2080999";
	public static final int PULSE_DEVICE_NUM = 101;

	public static final String BP_DEVICE = "NIBP042077";
	public static final int BP_DEVICE_NUM = 102;

	public static final String EET_DEVICE = "TEMP010344";
	public static final int EET_DEVICE_NUM = 103;

	public static final String FET_DEVICE = "FHR010093";
	public static final int FET_DEVICE_NUM = 104;

	public static final String BLOOD_DEVICE = "BG011081";
	public static final int BLOOD_DEVICE_NUM = 105;

	// Constants that indicate the current connection state
	public static final int STATE_NONE = 0; // we're doing nothing
	public static final int STATE_LISTEN = 1; // now listening for incoming
												// connections
	public static final int STATE_CONNECTING = 2; // now initiating an outgoing
													// connection
	public static final int STATE_CONNECTED = 3; // now connected to a remote
													// device

}
