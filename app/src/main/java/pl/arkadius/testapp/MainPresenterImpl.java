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
    private RepositoryImpl.APIClient apiClient;
    private ArrayList<Contact> contacts;

    MainPresenterImpl(RepositoryImpl.APIClient apiClient){
        this.apiClient=apiClient;
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
        view.showProgressBar();

        Call<ArrayList<Contact>> call = apiClient.getContacts();
        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                contacts=response.body();
                view.hideProgressBar();
                view.showContacts(contacts);
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
        view.openContactDetails(contacts.get(position));
    }

    @Override
    public void onLongContactClicked(int position) {
        contacts.remove(position);
        view.deleteContact();
    }

    @Override
    public void onReconnectButtonClick() {
        loadContacts();
        view.hideFailView();
    }
}
