package pl.arkadius.testapp;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public interface MainContract {
    interface MainView{
        void addContactsToAdapter(ArrayList<Contact> contacts);
        void removeContactsFromAdapter(int position);
        void clearContactsFromAdapter();
        void setContactSeen(int position,boolean seen);
        void updateContactView();
        Contact getContactFromAdapter(int position);
        void openContactDetails(Contact contact);
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
        void loadContacts();
        void onContactClicked(int position);
        void onLongContactClicked(int position);
        void onRefreshButtonClick();
    }
}
