package pl.arkadius.testapp;

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
    public static final String URL="https://jaksiemaszcare-training-mobile.herokuapp.com/";
    private static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Realm realm;

    public ContactsRepositoryImpl(Realm realm){
        this.realm=realm;
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
                callback.onSuccess(response.body());
                clearRealm();
                saveOfflineContacts(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

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
}
