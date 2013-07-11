/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class VehicleDTO implements java.io.Serializable{
    private Integer id;
     private String vin;
     private String brand;
     private String vehicleModel;

    public VehicleDTO() {
    }
    
    public VehicleDTO(Integer id, String vin, String brand, String vehicleModel) {
        this.vin = vin;
        this.brand = brand;
        this.vehicleModel = vehicleModel;
        this.id = id;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVin() {
        return this.vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getVehicleModel() {
        return this.vehicleModel;
    }
    
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
}
