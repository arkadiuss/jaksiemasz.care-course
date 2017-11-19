package pl.arkadius.testapp;

/**
 * Created by arkadius on 18.11.17.
 */

public class Contact {
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
}
