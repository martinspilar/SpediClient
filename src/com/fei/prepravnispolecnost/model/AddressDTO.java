/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei.prepravnispolecnost.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Spilda
 */
public class AddressDTO  implements java.io.Serializable, Parcelable {


     private Integer id;
     private String city;
     private String street;
     private int streetnum;
     private int psc;
     private String gpsLon;
     private String gpsLat;

    public AddressDTO() {
    }

	
    public AddressDTO(Integer id, String city, String street, int streetnum, int psc) {
        this.city = city;
        this.street = street;
        this.streetnum = streetnum;
        this.psc = psc;
        this.id = id;
    }
    public AddressDTO(Integer id, String city, String street, int streetnum, int psc, String gpsLon, String gpsLat) {
       this.city = city;
       this.street = street;
       this.streetnum = streetnum;
       this.psc = psc;
       this.gpsLon = gpsLon;
       this.gpsLat = gpsLat;
       this.id = id;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    public int getStreetnum() {
        return this.streetnum;
    }
    
    public void setStreetnum(int streetnum) {
        this.streetnum = streetnum;
    }
    public int getPsc() {
        return this.psc;
    }
    
    public void setPsc(int psc) {
        this.psc = psc;
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
    
    @Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.id);
		dest.writeString(this.city);
		dest.writeString(this.street);
		dest.writeInt(this.streetnum);
		dest.writeInt(this.psc);
		dest.writeString(this.gpsLon);
		dest.writeString(this.gpsLat);
	}
	
	public AddressDTO(Parcel in){
        this.id = in.readInt();
        this.city = in.readString();
        this.street = in.readString();
        this.streetnum = in.readInt();
        this.psc = in.readInt();
        this.gpsLon = in.readString();
        this.gpsLat = in.readString();
    }
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AddressDTO createFromParcel(Parcel in) {
            return new AddressDTO(in); 
        }

        public AddressDTO[] newArray(int size) {
            return new AddressDTO[size];
        }
    };
}


