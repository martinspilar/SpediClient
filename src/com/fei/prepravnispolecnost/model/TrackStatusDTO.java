/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class TrackStatusDTO implements java.io.Serializable {

    private Integer id;
    private String trackStatus;

    public TrackStatusDTO() {
    }

    public TrackStatusDTO(Integer id, String trackStatus) {
        this.trackStatus = trackStatus;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackStatus() {
        return this.trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }
}
