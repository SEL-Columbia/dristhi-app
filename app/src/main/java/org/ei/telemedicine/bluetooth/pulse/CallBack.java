package org.ei.telemedicine.bluetooth.pulse;

public class CallBack {
	public PulseBuf m_mtbuf;
	public ICallBack m_icall;

	public CallBack(PulseBuf mtbuf, ICallBack icall) {

		m_mtbuf = mtbuf;
		m_icall = icall;
	}

	public void call() {
		m_icall.call();
	}
}
