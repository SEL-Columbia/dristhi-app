package org.ei.drishti.event;

public class CapturedPhotoInformation {
    private final String caseId;
    private final String photoPath;

    public CapturedPhotoInformation(String caseId, String photoPath) {
        this.caseId = caseId;
        this.photoPath = photoPath;
    }

    public String caseId() {
        return caseId;
    }

    public String photoPath() {
        return photoPath;
    }
}
