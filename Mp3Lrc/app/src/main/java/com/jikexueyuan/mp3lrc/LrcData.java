package com.jikexueyuan.mp3lrc;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class LrcData {

    private int startTime;
    private int endTime;
    private String lrc;

    public LrcData() {
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }
}
