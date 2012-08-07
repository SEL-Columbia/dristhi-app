package org.ei.drishti.domain;

public class ANM {
    private final String name;
    private final long eligibleCoupleCount;
    private final long ancCount;
    private final long pncCount;
    private final long childCount;

    public ANM(String name, long eligibleCoupleCount, long ancCount, long pncCount, long childCount) {
        this.name = name;
        this.eligibleCoupleCount = eligibleCoupleCount;
        this.ancCount = ancCount;
        this.pncCount = pncCount;
        this.childCount = childCount;
    }

    public String name() {
        return name;
    }

    public long ancCount() {
        return ancCount;
    }

    public long pncCount() {
        return pncCount;
    }

    public long childCount() {
        return childCount;
    }

    public long ecCount() {
        return eligibleCoupleCount;
    }
}
