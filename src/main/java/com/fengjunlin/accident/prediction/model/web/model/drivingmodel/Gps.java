package com.fengjunlin.accident.prediction.model.web.model.drivingmodel;

/**
 * @Description
 * @Author fengjl
 * @Date 2019/6/20 14:33
 * @Version 1.0
 **/
public class Gps {
    private double wgLat;
    private double wgLon;
    public Gps(double wgLat, double wgLon) {
        setWgLat(wgLat);
        setWgLon(wgLon);
    }
    public double getWgLat() {
        return wgLat;
    }
    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }
    public double getWgLon() {
        return wgLon;
    }
    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }
    @Override
    public String toString() {
        return wgLat + "," + wgLon;
    }
}
