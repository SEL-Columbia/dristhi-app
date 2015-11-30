package org.ei.telemedicine.domain;

import java.util.Map;

public class TestDomain {
	private String caseId;
	private String testName;
	private String testAge;
	private Map<String, String> details;

	public TestDomain(String caseId, String testName, String testAge,
			Map<String, String> details) {
		this.caseId = caseId;
		this.testName = testName;
		this.testAge = testAge;
		this.details = details;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestAge() {
		return testAge;
	}

	public void setTestAge(String testAge) {
		this.testAge = testAge;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getDetail(String name) {
		return details.get(name);
	}

}
