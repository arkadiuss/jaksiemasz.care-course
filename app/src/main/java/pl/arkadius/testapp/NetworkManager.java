package pl.arkadius.testapp;

import java.util.ArrayList;

import retrofit2.Callback;

/**
 * Created by arkadius on 01.12.17.
 */

public interface NetworkManager {
    void getContacts(Callback<ArrayList<Contact>> callback);
}
