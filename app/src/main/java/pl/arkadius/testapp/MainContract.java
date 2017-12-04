package pl.arkadius.testapp;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public interface MainContract {
    public interface MainView{
        void setContactsList(ArrayList<Contact> contacts);
        void showContacts();
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
        void initContacts();
        void loadContacts();
        void onContactClicked(int position);
        void onLongContactClicked(int position);
        void onRefreshButtonClick();
    }
}
