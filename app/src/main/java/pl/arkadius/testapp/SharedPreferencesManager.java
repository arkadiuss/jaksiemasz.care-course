package pl.arkadius.testapp;

/**
 * Created by arkadius on 04.12.17.
 */

public interface SharedPreferencesManager {
    boolean checkIsSeen(String id);
    void setSeen(String id, boolean seen);
}
