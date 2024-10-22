package com.example;

public class EVAUnit {
    private int unitID;
    private String pilotName;
    private int syncRate;
    private String weaponry;
    private String status;

    public EVAUnit(int unitID, String pilotName, int syncRate, String weaponry, String statuString) {
        this.unitID = unitID;
        this.pilotName = pilotName;
        this.syncRate = syncRate;
        this.weaponry = weaponry;
        this.status = status;
    }

    public int getUnitID() {
        return unitID;
    }

    public String getPilotName() {
        return pilotName;
    }

    public int getSyncRate() {
        return syncRate;
    }

    public String getWeaponry() {
        return weaponry;
    }

    public String getStatus() {
        return status;
    }

    public void setUnitID() {

        this.unitID = unitID;

    }

    public void setPilotName() {

        this.pilotName = pilotName;

    }

    public void setSyncRate() {
        this.syncRate = syncRate;
    }

    public void setWeaponry() {
        this.weaponry = weaponry;
    }

    public void setStatus() {
        this.status = status;
    }

}
