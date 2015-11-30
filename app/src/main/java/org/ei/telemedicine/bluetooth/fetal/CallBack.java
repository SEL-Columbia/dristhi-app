package org.ei.telemedicine.bluetooth.fetal;

public class CallBack {
	public FetalBuf m_mtbuf;
	public ICallBack m_icall;

	public CallBack(FetalBuf mtbuf, ICallBack icall) {

		m_mtbuf = mtbuf;
		m_icall = icall;
	}

	public void call() {
		m_icall.call();
	}
}
