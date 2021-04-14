package com.nilcaream.activities;

import java.util.StringJoiner;

public class Window {

    private final String wid;

    private final int pid;

    private final String title;

    public Window(String wid, int pid, String title) {
        this.wid = wid;
        this.pid = pid;
        this.title = title;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(title)
                .add("(PID:" + pid)
                .add("WID:" + wid + ")")
                .toString();
    }

    public String getWid() {
        return wid;
    }

    public int getPid() {
        return pid;
    }

    public String getTitle() {
        return title;
    }
}
