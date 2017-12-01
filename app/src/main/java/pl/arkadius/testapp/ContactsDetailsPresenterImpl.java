package pl.arkadius.testapp;

import android.support.annotation.Nullable;

/**
 * Created by arkadius on 02.12.17.
 */

public class ContactsDetailsPresenterImpl implements ContactDetailsContract.ContactDetailsPresenter {
    @Nullable
    private ContactDetailsContract.ContactDetailsView view;
    private Contact contact;
    @Override
    public void attach(ContactDetailsContract.ContactDetailsView v) {
        this.view=v;
    }

    @Override
    public void detach() {
        if (view != null) {
            view.showExitingToast();
        }
        view=null;
    }

    @Override
    public void receiveContact(Contact contact) {
        this.contact=contact;
    }

    @Override
    public void setContact() {
        if (view != null) {
            view.loadContact(contact);
        }
    }
}
