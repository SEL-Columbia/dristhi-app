package org.ei.telemedicine.bluetooth.blood;

public class CallBack {
	public BloodBuf m_mtbuf;
	public ICallBack m_icall;

	public CallBack(BloodBuf mtbuf, ICallBack icall) {

		m_mtbuf = mtbuf;
		m_icall = icall;
	}

	public void call() {
		m_icall.call();
	}
}
