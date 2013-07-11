/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;


/**
 *
 * @author Spilda
 */
public class OrderTypeDTO implements java.io.Serializable {

    private Integer id;
    private String orderType;
    private double price;

    public OrderTypeDTO() {
    }

    public OrderTypeDTO(Integer id, String orderType, double price) {
        this.orderType = orderType;
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
