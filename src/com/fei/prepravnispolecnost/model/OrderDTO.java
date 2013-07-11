/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Spilda
 */
public class OrderDTO implements java.io.Serializable,Parcelable {


     private Integer id;
     private OrderTypeDTO orderType;
     private CustomerDTO customerByIdReceiver;
     private DepoDTO depoByIdDepoEnd;
     private OrderStatusDTO orderStatus;
     private DepoDTO depoByIdDepoReceived;
     private CustomerDTO customerByIdSender;
     private DepoDTO depoByIdDepoActual;
     private Date received;
     private Date delivered;
     private int paid;
     private Date lastModified;
     private Integer trackOrderNumber;

    public OrderDTO() {
    }

	
    public OrderDTO(Integer id, OrderTypeDTO orderType, CustomerDTO customerByIdReceiver, 
            DepoDTO depoByIdDepoEnd, OrderStatusDTO orderStatus, DepoDTO depoByIdDepoReceived, 
            CustomerDTO customerByIdSender, Date received, int paid, Date lastModified) {
        this.orderType = orderType;
        this.customerByIdReceiver = customerByIdReceiver;
        this.depoByIdDepoEnd = depoByIdDepoEnd;
        this.orderStatus = orderStatus;
        this.depoByIdDepoReceived = depoByIdDepoReceived;
        this.customerByIdSender = customerByIdSender;
        this.received = received;
        this.paid = paid;
        this.lastModified = lastModified;
        this.id = id;
    }
    public OrderDTO(Integer id, OrderTypeDTO orderType, CustomerDTO customerByIdReceiver, 
            DepoDTO depoByIdDepoEnd, OrderStatusDTO orderStatus, DepoDTO depoByIdDepoReceived, 
            CustomerDTO customerByIdSender, DepoDTO depoByIdDepoActual, 
            Date received, Date delivered, int paid, Date lastModified, Integer trackOrderNumber) {
       this.orderType = orderType;
       this.customerByIdReceiver = customerByIdReceiver;
       this.depoByIdDepoEnd = depoByIdDepoEnd;
       this.orderStatus = orderStatus;
       this.depoByIdDepoReceived = depoByIdDepoReceived;
       this.customerByIdSender = customerByIdSender;
       this.depoByIdDepoActual = depoByIdDepoActual;
       this.received = received;
       this.delivered = delivered;
       this.paid = paid;
       this.lastModified = lastModified;
       this.trackOrderNumber = trackOrderNumber;
       this.id = id;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public OrderTypeDTO getOrderType() {
        return this.orderType;
    }
    
    public void setOrderType(OrderTypeDTO orderType) {
        this.orderType = orderType;
    }
    public CustomerDTO getCustomerByIdReceiver() {
        return this.customerByIdReceiver;
    }
    
    public void setCustomerByIdReceiver(CustomerDTO customerByIdReceiver) {
        this.customerByIdReceiver = customerByIdReceiver;
    }
    
    public DepoDTO getDepoByIdDepoEnd() {
        return this.depoByIdDepoEnd;
    }
    
    public void setDepoByIdDepoEnd(DepoDTO depoByIdDepoEnd) {
        this.depoByIdDepoEnd = depoByIdDepoEnd;
    }
    public OrderStatusDTO getOrderStatus() {
        return this.orderStatus;
    }
    
    public void setOrderStatus(OrderStatusDTO orderStatus) {
        this.orderStatus = orderStatus;
    }
    public DepoDTO getDepoByIdDepoReceived() {
        return this.depoByIdDepoReceived;
    }
    
    public void setDepoByIdDepoReceived(DepoDTO depoByIdDepoReceived) {
        this.depoByIdDepoReceived = depoByIdDepoReceived;
    }
    public CustomerDTO getCustomerByIdSender() {
        return this.customerByIdSender;
    }
    
    public void setCustomerByIdSender(CustomerDTO customerByIdSender) {
        this.customerByIdSender = customerByIdSender;
    }
    public DepoDTO getDepoByIdDepoActual() {
        return this.depoByIdDepoActual;
    }
    
    public void setDepoByIdDepoActual(DepoDTO depoByIdDepoActual) {
        this.depoByIdDepoActual = depoByIdDepoActual;
    }
    public Date getReceived() {
        return this.received;
    }
    
    public void setReceived(Date received) {
        this.received = received;
    }
    public Date getDelivered() {
        return this.delivered;
    }
    
    public void setDelivered(Date delivered) {
        this.delivered = delivered;
    }
    public int getPaid() {
        return this.paid;
    }
    
    public void setPaid(int paid) {
        this.paid = paid;
    }
    public Date getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    public Integer getTrackOrderNumber() {
        return this.trackOrderNumber;
    }
    
    public void setTrackOrderNumber(Integer trackOrderNumber) {
        this.trackOrderNumber = trackOrderNumber;
    }
    
    public String getSenderString(){
    	return id+": "+customerByIdSender.getName()+" "+customerByIdSender.getSurname();
    }
    
    public String getReceiverString(){
    	return id+": "+customerByIdReceiver.getName()+" "+customerByIdReceiver.getSurname();
    }
    
    public String getDepoEndString(){
    	return id+": Cílové depo - "+depoByIdDepoEnd.getAddress().getCity();
    }
    
    public OrderDTO(Parcel in){
        this.id = in.readInt();
        this.paid = in.readInt();
        this.trackOrderNumber = in.readInt();
        this.received = (Date) in.readSerializable();
        this.delivered = (Date) in.readSerializable();
        this.lastModified = (Date) in.readSerializable();
        this.customerByIdReceiver = (CustomerDTO) in.readSerializable();
        this.customerByIdSender = (CustomerDTO) in.readSerializable();
        this.depoByIdDepoActual = (DepoDTO) in.readSerializable();
        this.depoByIdDepoEnd = (DepoDTO) in.readSerializable();
        this.depoByIdDepoReceived = (DepoDTO) in.readSerializable();
        this.orderStatus = (OrderStatusDTO) in.readSerializable();
        this.orderType = (OrderTypeDTO) in.readSerializable();
    }
    
    @Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(paid);
		dest.writeInt(trackOrderNumber);
		dest.writeSerializable(received);
		dest.writeSerializable(delivered);
		dest.writeSerializable(lastModified);
    	dest.writeSerializable(customerByIdReceiver);
		dest.writeSerializable(customerByIdSender);
		dest.writeSerializable(depoByIdDepoActual);
		dest.writeSerializable(depoByIdDepoEnd);
		dest.writeSerializable(depoByIdDepoReceived);
		dest.writeSerializable(orderStatus);
		dest.writeSerializable(orderType);
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public OrderDTO createFromParcel(Parcel in) {
            return new OrderDTO(in); 
        }

        public OrderDTO[] newArray(int size) {
            return new OrderDTO[size];
        }
    };
}

