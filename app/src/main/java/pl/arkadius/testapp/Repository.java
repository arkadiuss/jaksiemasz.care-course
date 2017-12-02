package pl.arkadius.testapp;

import java.util.ArrayList;

import retrofit2.http.GET;

/**
 * Created by arkadius on 01.12.17.
 */

public interface Repository {
    ArrayList<Contact> getHardcodedData();
}
