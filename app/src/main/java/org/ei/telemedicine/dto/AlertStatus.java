package org.ei.telemedicine.dto;

public enum AlertStatus {
	upcoming("upcoming"), normal("normal"), inProcess("inProcess"), urgent(
			"urgent"), complete("complete");
	private String value;

	AlertStatus(String paramString) {
		this.value = paramString;
	}

	public static AlertStatus from(String paramString) {
		return valueOf(paramString);
	}

	public String value() {
		return this.value;
	}
}

// package org.ei.drishti.dto;
//
// public enum AlertStatus
// {
// private String value;
//
// static
// {
// AlertStatus normal = new AlertStatus("normal", 1, "normal");
// AlertStatus urgent = new AlertStatus("urgent", 2, "urgent");
// AlertStatus inProcess = new AlertStatus("inProcess", 3, "inProcess");
// AlertStatus complete = new AlertStatus("complete", 4, "complete");
// AlertStatus[] arrayOfAlertStatus = new AlertStatus[5];
// arrayOfAlertStatus[0] = upcoming;
// arrayOfAlertStatus[1] = normal;
// arrayOfAlertStatus[2] = urgent;
// arrayOfAlertStatus[3] = inProcess;
// arrayOfAlertStatus[4] = complete;
// $VALUES = arrayOfAlertStatus;
// }
//
// private AlertStatus(String paramString)
// {
// this.value = paramString;
// }
//
// public static AlertStatus from(String paramString)
// {
// return valueOf(paramString);
// }
//
// public String value()
// {
// return this.value;
// }
// }
//
//
//
// /* Location: C:\Users\appaji.r\Downloads\classes-dex2.jar
//
// * Qualified Name: org.ei.drishti.dto.AlertStatus
//
// * JD-Core Version: 0.7.0.1
//
// */