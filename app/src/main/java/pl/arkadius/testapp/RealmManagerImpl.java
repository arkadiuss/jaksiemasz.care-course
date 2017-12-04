package pl.arkadius.testapp;

import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by arkadius on 04.12.17.
 */

public class RealmManagerImpl implements RealmManager {
    Realm realm;
    public RealmManagerImpl(Realm realm){
        this.realm=realm;
    }

    @Override
    public void addContacts(ArrayList<Contact> contacts) {
        realm.beginTransaction();
        for(int i=0;i<contacts.size();i++)
            realm.insert(contacts.get(i));
        realm.commitTransaction();
    }

    @Override
    public ArrayList<Contact> getContacts() {
        RealmResults<Contact> results=realm.where(Contact.class).findAll();
        ArrayList<Contact> contacts=new ArrayList<Contact>();
        for(Contact con : results){
            contacts.add(plainContact(con));
        }
        return contacts;
    }
    private Contact plainContact(Contact con){
        Contact c = new Contact(con.getId(),con.getName(),con.getSurname(),con.getWebsite(),con.getEmail(),con.getPhoneNo(),con.getPicURL());
        c.setSeen(false);
        return c;
    }
    @Override
    public void clear() {
        realm.beginTransaction();
        RealmResults<Contact> toDel =realm.where(Contact.class).findAll();
        toDel.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
