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
    private ArrayList<Contact> contacts;
    private SharedPreferencesManagerImpl sharedPreferencesManager;
    private ConnectivityManager connectivityManager;

    MainPresenterImpl(ContactsRepositoryImpl networkManager,
                      SharedPreferencesManagerImpl sharedPreferencesManager,
                      ConnectivityManager connectivityManager){
        this.contactsRepository = networkManager;
        this.sharedPreferencesManager=sharedPreferencesManager;
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
    public void initContacts() {
        contacts=new ArrayList<Contact>();
        if (view != null) {
            view.setContactsList(contacts);
        }
    }

    @Override
    public void loadContacts() {
        if(view!=null) view.showProgressBar();
        contactsRepository.getContacts(new RepositoryCallback() {
            @Override
            public void onSuccess(ArrayList<Contact> con) {
                contacts.clear();
                contacts.addAll(con);
                displaySeen();
                view.hideProgressBar();
                view.showContacts();
            }

            @Override
            public void onError(Throwable t) {
                view.hideProgressBar();
                Log.d("Presenter",t.getMessage());
                view.showFailView("Can't connect to server. Presented data can be outdated.\nServer response: "+t.getMessage());

            }
        });
    }

    private void displaySeen(){
        for(int i=0;i<contacts.size();i++){
            if(sharedPreferencesManager.checkIsSeen(contacts.get(i).getId())) {
                contacts.get(i).setSeen(true);
            }
        }
    }

    @Override
    public void onContactClicked(int position) {
        if (view != null) {
            sharedPreferencesManager.setSeen(contacts.get(position).getId(),true);
            contacts.get(position).setSeen(true);
            view.showContacts();
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
