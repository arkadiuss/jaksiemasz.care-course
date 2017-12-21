package pl.arkadius.testapp;

import java.util.ArrayList;

/**
 * Created by arkadius on 20.12.17.
 */

interface RepositoryCallback {
    void onSuccess(ArrayList<Contact> contacts);
    void onError(Throwable t);
}
