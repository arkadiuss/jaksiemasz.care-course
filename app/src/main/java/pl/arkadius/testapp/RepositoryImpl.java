package pl.arkadius.testapp;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by arkadius on 01.12.17.
 */

public class RepositoryImpl implements Repository {
    public static final String URL="https://jaksiemaszcare-training-mobile.herokuapp.com/";
    private static Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    static RepositoryImpl getInstance(){
        return new RepositoryImpl();
    }

    void getContacts(Callback<ArrayList<Contact>> callback){
        Call<ArrayList<Contact>> call = retrofit.create(APIClient.class).getContacts();
        call.enqueue(callback);
    }

    public interface APIClient {
        @GET("/api/")
        Call<ArrayList<Contact>> getContacts();
    }

    @Override
    public ArrayList<Contact> getHardcodedData() {
        ArrayList<Contact> cons=new ArrayList<Contact>();
        cons.add(new Contact("Google","Corporation","www.google.com",
                "spoko@firma.com","123456789",
                "https://www.google.pl/doodle4google/images/splashes/featured.png"));
        cons.add(new Contact("Apple","Corporation","www.kiepskafirma.com",
                "kiepska@firma.com","123456789",
                "http://cdn.androidbeat.com/wp-content/uploads/2014/09/Android_eating_Apple_evil.jpg"));
        cons.add(new Contact("Microsoft","Corporation","www.ujdziewtlumie.com",
                "niebieski@ekran.com","123456789",
                "http://gfx.antyradio.pl/var/antyradio/storage/images/technologia/komputery/niebieski-ekran-smierci-bardziej-przyjazny-uzytkownikom-i-hakerom-7869/627011-1-pol-PL/Niebieski-Ekran-Smierci-bardziej-przyjazny-uzytkownikom-i-hakerom_article.png"));
        cons.add(new Contact("Linux","Foundation","www.jedynyslusznysystem.com",
                "spoko@firma.com","123456789",
                "https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2015/10/1444835774tux.jpg"));
        return cons;
    }
}
