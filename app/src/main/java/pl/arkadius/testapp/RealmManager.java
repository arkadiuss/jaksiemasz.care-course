package pl.arkadius.testapp;

import java.util.ArrayList;

/**
 * Created by arkadius on 04.12.17.
 */

public interface RealmManager {
    void addContacts(ArrayList<Contact> contact);
    ArrayList<Contact> getContacts();
    void clear();
}
