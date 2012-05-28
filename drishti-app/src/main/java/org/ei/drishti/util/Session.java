package org.ei.drishti.util;

import java.util.Date;

public class Session {
    private String password;
    private String repositoryName = "drishti.db";
    private long sessionExpiryTime = 0;

    public long lengthInMilliseconds() {
        return 30 * 60 * 1000;
    }

    public String password() {
        return password;
    }

    public String repositoryName() {
        return repositoryName;
    }

    public Session setPassword(String password) {
        this.password = password;
        return this;
    }

    public Session setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        return this;
    }

    public Session start(long numberOfMillisecondsAfterNowThatThisSessionEnds) {
        setSessionExpiryTimeTo(new Date().getTime() + numberOfMillisecondsAfterNowThatThisSessionEnds);
        return this;
    }

    public boolean hasExpired() {
        if (password == null) {
            return true;
        }

        long now = new Date().getTime();
        return now > sessionExpiryTime;
    }

    public void expire() {
        setSessionExpiryTimeTo(new Date().getTime() - 1);
    }

    private void setSessionExpiryTimeTo(long expiryTimeInMillisecondsSinceEpoch) {
        this.sessionExpiryTime = expiryTimeInMillisecondsSinceEpoch;
    }
}
