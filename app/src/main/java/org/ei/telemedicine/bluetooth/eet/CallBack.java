package org.ei.telemedicine.bluetooth.eet;

public class CallBack {
	public EETBuf m_mtbuf;
	public ICallBack m_icall;

	public CallBack(EETBuf mtbuf, ICallBack icall) {

		m_mtbuf = mtbuf;
		m_icall = icall;
	}

	public void call() {
		m_icall.call();
	}
}
