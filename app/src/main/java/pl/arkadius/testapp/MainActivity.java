package pl.arkadius.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.MainView,ContactsAdapter.Listener{
    private RecyclerView rv;
    private ContactsAdapter adCon;
    private MainContract.MainPresenter presenter;

    public static final String EXTRA_CONTACT="contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Creating recycler view
        rv=(RecyclerView) findViewById(R.id.recycler_cons);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //Connecting with presenter
        presenter = new MainPresenterImpl(RepositoryImpl.getInstance());
        presenter.attach(this);
        presenter.loadContacts();
    }

    @Override
    public void onItemClick(int position) {
        presenter.onContactClicked(position);
    }

    @Override
    public void onLongItemClick(int position) {
        presenter.onLongContactClicked(position);
    }

    @Override
    public void openContactDetails(Contact contact) {
        Intent toSec = new Intent(this,ContactDetailsActivity.class);
        toSec.putExtra(EXTRA_CONTACT,contact);
        startActivity(toSec);
    }

    @Override
    public void deleteContact() {
        adCon.notifyDataSetChanged();
        Toast.makeText(this, R.string.notify_deleted,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContacts(ArrayList<Contact> contacts) {
        adCon = new ContactsAdapter(contacts,this);
        adCon.setListener(this);
        rv.setAdapter(adCon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
