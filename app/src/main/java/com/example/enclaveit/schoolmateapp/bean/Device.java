package com.example.enclaveit.schoolmateapp.bean;

/**
 * Created by vuongluis on 3/25/2017.
 */

public class Device {
    private String name;
    private String board;
    private String cores;
    private String clockspeed;
    private String cpu;
    private String govemor;
    private String version;
    private String arm7;

    public Device(String name, String board, String cores, String clockspeed, String cpu, String govemor, String version, String arm7) {
        this.name = name;
        this.board = board;
        this.cores = cores;
        this.clockspeed = clockspeed;
        this.cpu = cpu;
        this.govemor = govemor;
        this.version = version;
        this.arm7 = arm7;
    }

    public String getName() {
        return name;
    }

    public String getBoard() {
        return board;
    }

    public String getCores() {
        return cores;
    }

    public String getClockspeed() {
        return clockspeed;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGovemor() {
        return govemor;
    }

    public String getVersion() {
        return version;
    }

    public String getArm7() {
        return arm7;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public void setClockspeed(String clockspeed) {
        this.clockspeed = clockspeed;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setGovemor(String govemor) {
        this.govemor = govemor;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setArm7(String arm7) {
        this.arm7 = arm7;
    }
}
