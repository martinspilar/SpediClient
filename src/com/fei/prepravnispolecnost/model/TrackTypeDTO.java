/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

/**
 *
 * @author Spilda
 */
public class TrackTypeDTO implements java.io.Serializable {

    private Integer id;
    private String trackType;

    public TrackTypeDTO() {
    }

    public TrackTypeDTO(Integer id, String trackType) {
        this.trackType = trackType;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackType() {
        return this.trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }
}
