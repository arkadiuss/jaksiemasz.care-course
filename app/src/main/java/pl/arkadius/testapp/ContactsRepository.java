package pl.arkadius.testapp;

import java.util.ArrayList;

import retrofit2.Callback;

/**
 * Created by arkadius on 01.12.17.
 */

public interface ContactsRepository {
    void getContacts(RepositoryCallback callback);
    void setSeen(String id, boolean seen);
}
