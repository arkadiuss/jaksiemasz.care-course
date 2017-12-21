package pl.arkadius.testapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private ContactsRepositoryImpl contactsRepository;
    private ConnectivityManager connectivityManager;

    MainPresenterImpl(ContactsRepositoryImpl networkManager,
                      ConnectivityManager connectivityManager){
        this.contactsRepository = networkManager;
        this.connectivityManager=connectivityManager;
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
    public void checkConnectivity() {
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        if(network==null||!network.isConnectedOrConnecting()) {
            view.setActionBarColor(R.color.visited);
            //TODO Reload contacts
            view.showFailView("You don't have network connection. Presented data can be outdated");
        }else {
            view.setActionBarColor(R.color.colorPrimary);
            view.hideFailView();
        }
    }


    @Override
    public void loadContacts() {
        if(view!=null) view.showProgressBar();
        contactsRepository.getContacts(new RepositoryCallback() {
            @Override
            public void onSuccess(ArrayList<Contact> con) {
                view.clearContactsFromAdapter();
                view.addContactsToAdapter(con);
                view.hideProgressBar();
                view.updateContactView();
            }

            @Override
            public void onError(Throwable t) {
                view.hideProgressBar();
                Log.d("Presenter",t.getMessage());
                view.showFailView("Can't connect to server. Presented data can be outdated.\nServer response: "+t.getMessage());

            }
        });
    }


    @Override
    public void onContactClicked(int position) {
        if (view != null) {
            contactsRepository.setSeen(view.getContactFromAdapter(position).getId(),true);
            view.setContactSeen(position,true);
            view.updateContactView();
            view.openContactDetails(view.getContactFromAdapter(position));
        }
    }

    @Override
    public void onLongContactClicked(int position) {
        view.removeContactsFromAdapter(position);
        view.updateContactView();
    }

    @Override
    public void onRefreshButtonClick() {
        loadContacts();
        if (view != null) {
            view.hideFailView();
        }
    }
}
