package pl.arkadius.testapp;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by arkadius on 01.12.17.
 */

public class ContactsRepositoryImpl implements ContactsRepository {
    private final String URL="https://jaksiemaszcare-training-mobile.herokuapp.com/";
    public static final String PREF_NAME="CONTACTS_SH";
    private Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Realm realm;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ContactsRepositoryImpl(Realm realm,SharedPreferences sharedPreferences){
        this.realm=realm;
        this.sharedPreferences=sharedPreferences;
        editor=sharedPreferences.edit();
    }

    public interface APIClient {
        @GET("/api/")
        Call<ArrayList<Contact>> getContacts();
    }

    public void getContacts(final RepositoryCallback callback){
        callback.onSuccess(getContactsFromRealm());
        Call<ArrayList<Contact>> call = retrofit.create(APIClient.class).getContacts();
        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                ArrayList<Contact> cons = response.body();
                for(int i=0;i<cons.size();i++)
                    cons.get(i).setSeen(checkIsSeen(cons.get(i).getId()));
                callback.onSuccess(cons);
                clearRealm();
                saveOfflineContacts(cons);
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    //Realm
    //TODO extract

    private void saveOfflineContacts(ArrayList<Contact> contacts) {
        realm.beginTransaction();
        for(int i=0;i<contacts.size();i++)
            realm.insert(contacts.get(i));
        realm.commitTransaction();
    }

    private ArrayList<Contact> getContactsFromRealm() {
        RealmResults<Contact> results=realm.where(Contact.class).findAll();
        ArrayList<Contact> contacts=new ArrayList<Contact>();
        for(Contact con : results){
            contacts.add(plainContact(con));
            contacts.get(contacts.size()-1).setSeen(checkIsSeen(con.getId()));
        }
        return contacts;
    }
    private Contact plainContact(Contact con){
        Contact c = new Contact(con.getId(),con.getName(),con.getSurname(),con.getWebsite(),con.getEmail(),con.getPhoneNo(),con.getPicURL());
        c.setSeen(false);
        return c;
    }

    private void clearRealm() {
        realm.beginTransaction();
        RealmResults<Contact> toDel =realm.where(Contact.class).findAll();
        toDel.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //SharedPreferences
    //TODO extract
    private boolean checkIsSeen(String id) {
        return sharedPreferences.getBoolean(id,false);
    }

    @Override
    public void setSeen(String id,boolean seen) {
        editor.putBoolean(id,seen).apply();
    }
}
