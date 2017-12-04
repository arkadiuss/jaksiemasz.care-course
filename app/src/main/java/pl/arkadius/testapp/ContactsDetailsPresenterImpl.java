package pl.arkadius.testapp;

import android.support.annotation.Nullable;
import android.util.Log;

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
            view.displayContact(contact);
        }
    }

    @Override
    public Contact getContactToSave() {
        Contact tmpContact=null;
        if (view != null) {
            tmpContact=view.getContactDataFromFields();
            tmpContact.setPicURL(contact.getPicURL());
            tmpContact.setId(contact.getId());
        }
        return tmpContact;
    }
}
