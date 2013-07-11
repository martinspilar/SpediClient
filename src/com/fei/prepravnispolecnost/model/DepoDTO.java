/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class DepoDTO implements java.io.Serializable {

    private Integer id;
    private AddressDTO address;

    public DepoDTO() {
    }

    public DepoDTO(Integer id, AddressDTO address) {
        this.address = address;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AddressDTO getAddress() {
        return this.address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
