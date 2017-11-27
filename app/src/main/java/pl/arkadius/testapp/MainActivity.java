package pl.arkadius.testapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.Listener{
    private RecyclerView rv;
    private ArrayList<Contact> cons= new ArrayList<Contact>();
    private ContactsAdapter adCon;

    public static final String EXTRA_CONTACT="contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Creating list
        cons.add(new Contact("Google","Corporation","www.google.com","spoko@firma.com","123456789","https://www.google.pl/doodle4google/images/splashes/featured.png"));
        cons.add(new Contact("Apple","Corporation","www.kiepskafirma.com","kiepska@firma.com","123456789","http://cdn.androidbeat.com/wp-content/uploads/2014/09/Android_eating_Apple_evil.jpg"));
        cons.add(new Contact("Microsoft","Corporation","www.ujdziewtlumie.com","niebieski@ekran.com","123456789","http://gfx.antyradio.pl/var/antyradio/storage/images/technologia/komputery/niebieski-ekran-smierci-bardziej-przyjazny-uzytkownikom-i-hakerom-7869/627011-1-pol-PL/Niebieski-Ekran-Smierci-bardziej-przyjazny-uzytkownikom-i-hakerom_article.png"));
        cons.add(new Contact("Linux","Foundation","www.jedynyslusznysystem.com","spoko@firma.com","123456789","https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2015/10/1444835774tux.jpg"));
        //Creating recycler view
        rv=(RecyclerView) findViewById(R.id.recycler_cons);
        adCon = new ContactsAdapter(cons,this);
        adCon.setListener(this);
        rv.setAdapter(adCon);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

    }

    @Override
    public void onItemClick(int position) {
        Intent toSec = new Intent(this,Activity2.class);
        toSec.putExtra(EXTRA_CONTACT,cons.get(position));
        startActivity(toSec);
    }

    @Override
    public void onLongItemClick(int position) {
        cons.remove(position);
        adCon.notifyDataSetChanged();
        Toast.makeText(this, R.string.notify_deleted,Toast.LENGTH_SHORT).show();
    }
}
