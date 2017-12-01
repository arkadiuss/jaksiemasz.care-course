package pl.arkadius.testapp;

/**
 * Created by arkadius on 02.12.17.
 */

public interface ContactDetailsContract {
    interface ContactDetailsView{
        void loadContact(Contact contact);
        void showExitingToast();
    }
    interface ContactDetailsPresenter{
        void attach(ContactDetailsView v);
        void detach();
        void receiveContact(Contact contact);
        void setContact();
    }
}
