/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class DeviceDTO implements java.io.Serializable {

    private Integer id;
    private String serialNumber;
    private String brand;
    private String deviceModel;
    private String gpsLon;
    private String gpsLat;

    public DeviceDTO() {
    }

    public DeviceDTO(Integer id, String serialNumber, String brand, String deviceModel, String gpsLon, String gpsLat) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.deviceModel = deviceModel;
        this.gpsLon = gpsLon;
        this.gpsLat = gpsLat;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getGpsLon() {
        return this.gpsLon;
    }

    public void setGpsLon(String gpsLon) {
        this.gpsLon = gpsLon;
    }

    public String getGpsLat() {
        return this.gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }
}