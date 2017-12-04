package pl.arkadius.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.MainView,ContactsAdapter.Listener{
    @BindView(R.id.recycler_cons)  RecyclerView rv;
    private ContactsAdapter contactsAdapter;
    private MainContract.MainPresenter presenter;
    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    @BindView(R.id.fail_text) TextView failText;
    @BindView(R.id.refresh_button) Button refreshButton;

    public static final String EXTRA_CONTACT="contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Creating recycler view
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //Creeating view
        hideFailView();
        //Connecting with presenter
        Realm.init(this);
        presenter = new MainPresenterImpl(NetworkManagerImpl.getInstance(),
                new SharedPreferencesManagerImpl(this.getSharedPreferences(SharedPreferencesManagerImpl.PREF_NAME, Context.MODE_PRIVATE)),
                new RealmManagerImpl(Realm.getDefaultInstance()));
        presenter.attach(this);
        presenter.initContacts();
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

    @OnClick(R.id.refresh_button)
    public void refresh(){
        presenter.onRefreshButtonClick();
    }

    @Override
    public void openContactDetails(Contact contact) {
        Intent toSec = new Intent(this,ContactDetailsActivity.class);
        toSec.putExtra(EXTRA_CONTACT,contact);
        startActivity(toSec);
    }

    @Override
    public void deleteContact() {
        contactsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.notify_deleted,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFailView(String text) {
        failText.setVisibility(View.VISIBLE);
        failText.setText(text);
        refreshButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFailView() {
        failText.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
    }

    @Override
    public void setContactsList(ArrayList<Contact> contacts) {
        contactsAdapter = new ContactsAdapter(contacts,this);
        contactsAdapter.setListener(this);
        rv.setAdapter(contactsAdapter);
    }

    @Override
    public void showContacts() {
        contactsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
