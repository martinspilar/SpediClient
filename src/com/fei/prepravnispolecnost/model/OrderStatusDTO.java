/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class OrderStatusDTO implements java.io.Serializable {

    private Integer id;
    private String orderStatus;

    public OrderStatusDTO() {
    }

    public OrderStatusDTO(Integer id, String orderStatus) {
        this.orderStatus = orderStatus;
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
