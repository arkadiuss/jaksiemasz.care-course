package pl.arkadius.testapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arkadius on 18.11.17.
 */

public class Contact extends RealmObject implements Parcelable {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("firstName")
    @Expose
    private String name;
    @SerializedName("lastName")
    @Expose
    private String surname;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String picURL;
    @SerializedName("phone")
    @Expose
    private String phoneNo;
    public boolean isSeen = false;

    public Contact(){

    }
    public Contact(String id, String name, String surname, String website, String email, String phoneNo, String picURL) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.website = website;
        this.phoneNo = phoneNo;
        this.email = email;
        this.picURL = picURL;
        isSeen=false;
    }
    public Contact(Parcel in){
        this.name = in.readString();
        this.surname = in.readString();
        this.website = in.readString();
        this.phoneNo = in.readString();
        this.email = in.readString();
        this.picURL = in.readString();
        this.isSeen = in.readByte() != 0;
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
    public String getId(){ return id;}

    public void setId(String id){this.id=id;}

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

    public void setSeen(boolean seen){
        this.isSeen=seen;
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
        dest.writeByte((byte) (isSeen ? 1:0));
    }
}
