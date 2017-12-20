package pl.arkadius.testapp;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public interface MainContract {
    interface MainView{
        void setContactsList(ArrayList<Contact> contacts);
        void showContacts();
        void openContactDetails(Contact contact);
        void deleteContact();
        void showProgressBar();
        void hideProgressBar();
        void showFailView(String text);
        void hideFailView();
        void setActionBarColor(int color);
    }
    interface MainPresenter{
        void attach(MainView v);
        void detach();
        void checkConnectivity();
        void initContacts();
        void loadContacts();
        void onContactClicked(int position);
        void onLongContactClicked(int position);
        void onRefreshButtonClick();
    }
}
