package pl.arkadius.testapp;

import java.util.ArrayList;

/**
 * Created by arkadius on 21.12.17.
 */

public interface ContactsAdapter {
    void addContacts(ArrayList<Contact> contacts);
    void removeContact(int position);
    void clearContacts();
    void setContactSeen(int position,boolean seen);
    Contact getContact(int position);
}
