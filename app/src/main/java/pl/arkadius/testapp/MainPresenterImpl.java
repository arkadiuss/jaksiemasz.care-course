package pl.arkadius.testapp;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public class MainPresenterImpl implements MainContract.MainPresenter {
    @Nullable
    private MainContract.MainView view;
    private Repository rep;
    private ArrayList<Contact> contacts;

    MainPresenterImpl(Repository repository){
        this.rep=repository;
    }

    @Override
    public void attach(MainContract.MainView v) {
        this.view=v;
    }

    @Override
    public void detach() {
        view=null;
    }

    @Override
    public void loadContacts() {
        contacts=rep.getData();
        view.showContacts(contacts);
    }

    @Override
    public void onContactClicked(int position) {
        view.openContactDetails(contacts.get(position));
    }

    @Override
    public void onLongContactClicked(int position) {
        contacts.remove(position);
        view.deleteContact();
    }
}
