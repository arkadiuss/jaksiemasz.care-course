package pl.arkadius.testapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arkadius on 18.11.17.
 */

public class Contact implements Parcelable {
    private String name,surname;
    private String website, email, picURL,phoneNo;

    public Contact(String name, String surname, String website, String email, String phoneNo, String picURL) {
        this.name = name;
        this.surname = surname;
        this.website = website;
        this.phoneNo = phoneNo;
        this.email = email;
        this.picURL = picURL;
    }
    public Contact(Parcel in){
        this.name = in.readString();
        this.surname = in.readString();
        this.website = in.readString();
        this.phoneNo = in.readString();
        this.email = in.readString();
        this.picURL = in.readString();
    }

    static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>(){
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName(){
        return getName()+" "+getSurname();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(website);
        dest.writeString(phoneNo);
        dest.writeString(email);
        dest.writeString(picURL);
    }
}
