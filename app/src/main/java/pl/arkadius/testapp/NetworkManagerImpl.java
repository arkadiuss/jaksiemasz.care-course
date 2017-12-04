package pl.arkadius.testapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by arkadius on 01.12.17.
 */

public class NetworkManagerImpl implements NetworkManager {
    public static final String URL="https://jaksiemaszcare-training-mobile.herokuapp.com/";
    private static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    static NetworkManagerImpl getInstance(){
        return new NetworkManagerImpl();
    }

    public void getContacts(Callback<ArrayList<Contact>> callback){
        Call<ArrayList<Contact>> call = retrofit.create(APIClient.class).getContacts();
        call.enqueue(callback);
    }

    public interface APIClient {
        @GET("/api/")
        Call<ArrayList<Contact>> getContacts();
    }
}
