package pl.arkadius.testapp;

import android.os.Debug;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arkadius on 01.12.17.
 */

public class MainPresenterImpl implements MainContract.MainPresenter {
    @Nullable
    private MainContract.MainView view;
    private RepositoryImpl repository;
    private ArrayList<Contact> contacts;

    MainPresenterImpl(RepositoryImpl repository){
        this.repository=repository;
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
    public void initContacts() {
        contacts=new ArrayList<Contact>();
        if (view != null) {
            view.setContactsList(contacts);
        }
    }

    @Override
    public void loadContacts() {
        if (view != null) {
            view.showProgressBar();
        }

        repository.getContacts(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                contacts.clear();
                contacts.addAll(response.body());
                view.hideProgressBar();
                view.showContacts();
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                view.hideProgressBar();
                Log.d("Presenter",t.getMessage());
                view.showFailView("Can't connect to server. Nothing to show.\nServer response: "+t.getMessage());
            }
        });
    }

    @Override
    public void onContactClicked(int position) {
        if (view != null) {
            view.openContactDetails(contacts.get(position));
        }
    }

    @Override
    public void onLongContactClicked(int position) {
        contacts.remove(position);
        if (view != null) {
            view.deleteContact();
        }
    }

    @Override
    public void onRefreshButtonClick() {
        loadContacts();
        if (view != null) {
            view.hideFailView();
        }
    }
}
