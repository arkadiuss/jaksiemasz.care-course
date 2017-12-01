package pl.arkadius.testapp;

import java.util.ArrayList;

/**
 * Created by arkadius on 01.12.17.
 */

public class RepositoryImpl implements Repository {

    static RepositoryImpl getInstance(){
        return new RepositoryImpl();
    }

    @Override
    public ArrayList<Contact> getData() {
        //Creating list
        ArrayList<Contact> cons=new ArrayList<Contact>();
        cons.add(new Contact("Google","Corporation","www.google.com","spoko@firma.com","123456789","https://www.google.pl/doodle4google/images/splashes/featured.png"));
        cons.add(new Contact("Apple","Corporation","www.kiepskafirma.com","kiepska@firma.com","123456789","http://cdn.androidbeat.com/wp-content/uploads/2014/09/Android_eating_Apple_evil.jpg"));
        cons.add(new Contact("Microsoft","Corporation","www.ujdziewtlumie.com","niebieski@ekran.com","123456789","http://gfx.antyradio.pl/var/antyradio/storage/images/technologia/komputery/niebieski-ekran-smierci-bardziej-przyjazny-uzytkownikom-i-hakerom-7869/627011-1-pol-PL/Niebieski-Ekran-Smierci-bardziej-przyjazny-uzytkownikom-i-hakerom_article.png"));
        cons.add(new Contact("Linux","Foundation","www.jedynyslusznysystem.com","spoko@firma.com","123456789","https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2015/10/1444835774tux.jpg"));
        return cons;
    }
}
