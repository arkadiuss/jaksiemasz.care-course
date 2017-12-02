package pl.arkadius.testapp;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public interface MainContract {
    public interface MainView{
        void showContacts(ArrayList<Contact> contacts);
        void openContactDetails(Contact contact);
        void deleteContact();
        void showProgressBar();
        void hideProgressBar();
        void showFailView(String text);
        void hideFailView();
    }
    public interface MainPresenter{
        void attach(MainView v);
        void detach();
        void loadContacts();
        void onContactClicked(int position);
        void onLongContactClicked(int position);
        void onReconnectButtonClick();
    }
}
