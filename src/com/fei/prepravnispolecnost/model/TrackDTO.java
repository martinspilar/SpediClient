/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Spilda
 */
public class TrackDTO implements java.io.Serializable {


	private Integer id;
    private UserDTO users;
    private DepoDTO depoByIdDepoEnd;
    private TrackTypeDTO trackType;
    private TrackStatusDTO trackStatus;
    private DepoDTO depoByIdDepoStart;
    private Date dateTrack;
    private double length;
    private List<OrderDTO> orderses = new ArrayList<OrderDTO>();

   public TrackDTO() {
   }
   
   public TrackDTO(Integer id, UserDTO users, DepoDTO depoByIdDepoEnd, TrackTypeDTO trackType, 
           TrackStatusDTO trackStatus, DepoDTO depoByIdDepoStart, 
           Date dateTrack,double length, List<OrderDTO> orderses) {
      this.users = users;
      this.depoByIdDepoEnd = depoByIdDepoEnd;
      this.trackType = trackType;
      this.trackStatus = trackStatus;
      this.depoByIdDepoStart = depoByIdDepoStart;
      this.dateTrack = dateTrack;
      this.orderses = orderses;
      this.id = id;
      this.length = length;
   }
  
   public Integer getId() {
       return this.id;
   }
   
   public void setId(Integer id) {
       this.id = id;
   }
   public UserDTO getUsers() {
       return this.users;
   }
   
   public void setUsers(UserDTO users) {
       this.users = users;
   }
   public DepoDTO getDepoByIdDepoEnd() {
       return this.depoByIdDepoEnd;
   }
   
   public void setDepoByIdDepoEnd(DepoDTO depoByIdDepoEnd) {
       this.depoByIdDepoEnd = depoByIdDepoEnd;
   }
   public TrackTypeDTO getTrackType() {
       return this.trackType;
   }
   
   public void setTrackType(TrackTypeDTO trackType) {
       this.trackType = trackType;
   }
   public TrackStatusDTO getTrackStatus() {
       return this.trackStatus;
   }
   
   public void setTrackStatus(TrackStatusDTO trackStatus) {
       this.trackStatus = trackStatus;
   }
   public DepoDTO getDepoByIdDepoStart() {
       return this.depoByIdDepoStart;
   }
   
   public void setDepoByIdDepoStart(DepoDTO depoByIdDepoStart) {
       this.depoByIdDepoStart = depoByIdDepoStart;
   }
   public Date getDateTrack() {
       return this.dateTrack;
   }
   
   public void setDateTrack(Date dateTrack) {
       this.dateTrack = dateTrack;
   }
   public List<OrderDTO> getOrderses() {
       return this.orderses;
   }
   
   public void setOrderses(List<OrderDTO> orderses) {
       this.orderses = orderses;
   }

   public double getLength() {
       return length;
   }

   public void setLength(double length) {
       this.length = length;
   }
    
}
