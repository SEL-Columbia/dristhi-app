package org.ei.telemedicine.dto;

public enum BeneficiaryType {
	child("child"), mother("mother"), ec("ec");
	private String value;

	private BeneficiaryType(String paramString) {
		this.value = paramString;
	}

	public static BeneficiaryType from(String paramString) {
		return valueOf(paramString);
	}

	public String value() {
		return this.value;
	}
}

// package org.ei.drishti.dto;
//
// public enum BeneficiaryType
// {
// private String value;
//
// static
// {
// ec = new BeneficiaryType("ec", 2, "ec");
// BeneficiaryType[] arrayOfBeneficiaryType = new BeneficiaryType[3];
// arrayOfBeneficiaryType[0] = child;
// arrayOfBeneficiaryType[1] = mother;
// arrayOfBeneficiaryType[2] = ec;
// $VALUES = arrayOfBeneficiaryType;
// }
//
// private BeneficiaryType(String paramString)
// {
// this.value = paramString;
// }
//
// public static BeneficiaryType from(String paramString)
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
//