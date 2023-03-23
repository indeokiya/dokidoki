package com.dokidoki.bid.common.codes;

import java.util.concurrent.TimeUnit;

public enum LockInfo {
    REALTIME("realtime", 2000, 3000, TimeUnit.MILLISECONDS);

    final private String lockName;
    final private long waitTime;
    final private long unlockTime;
    final private TimeUnit timeUnit;

    LockInfo(String lockName, long waitTime, long unlockTime, TimeUnit timeUnit) {
        this.lockName  = lockName;
        this.waitTime = waitTime;
        this.unlockTime = unlockTime;
        this.timeUnit = timeUnit;
    }

    public String getLockName() {
        return lockName;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public long getUnlockTime() {
        return unlockTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
