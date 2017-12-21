package pl.arkadius.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

public class MainActivity extends AppCompatActivity implements MainContract.MainView,ContactsAdapterImpl.Listener{

    @BindView(R.id.recycler_cons)  RecyclerView rv;
    private ContactsAdapterImpl contactsAdapterImpl;
    private MainContract.MainPresenter presenter;
    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    @BindView(R.id.fail_text) TextView failText;
    @BindView(R.id.refresh_button) Button refreshButton;
    private ActionBar actionBar;
    private BroadcastReceiver networkReceiver;
    public static final String EXTRA_CONTACT="contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Creating recycler view
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        contactsAdapterImpl = new ContactsAdapterImpl(this);
        contactsAdapterImpl.setListener(this);
        rv.setAdapter(contactsAdapterImpl);
        //Creeating view
        actionBar=getSupportActionBar();
        hideFailView();
        //Connecting with presenter
        Realm.init(this);
        presenter = new MainPresenterImpl(
                new ContactsRepositoryImpl(Realm.getDefaultInstance(),this.getSharedPreferences(ContactsRepositoryImpl.PREF_NAME,Context.MODE_PRIVATE)),
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        presenter.attach(this);
        presenter.checkConnectivity();
        presenter.loadContacts();
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.checkConnectivity();
            }
        };
        registerReceiver(networkReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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
    public void addContactsToAdapter(ArrayList<Contact> contacts) {
        contactsAdapterImpl.addContacts(contacts);
    }

    @Override
    public void removeContactsFromAdapter(int position) {
        contactsAdapterImpl.removeContact(position);
    }

    @Override
    public void clearContactsFromAdapter() {
        contactsAdapterImpl.clearContacts();
    }

    @Override
    public void setContactSeen(int position,boolean seen) {
        contactsAdapterImpl.setContactSeen(position,seen);
    }

    @Override
    public void updateContactView() {
        contactsAdapterImpl.notifyDataSetChanged();
    }

    @Override
    public Contact getContactFromAdapter(int position) {
        return contactsAdapterImpl.getContact(position);
    }

    @Override
    public void openContactDetails(Contact contact) {
        Intent toSec = new Intent(this,ContactDetailsActivity.class);
        toSec.putExtra(EXTRA_CONTACT,contact);
        startActivity(toSec);
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
    public void setActionBarColor(int color) {
        actionBar.setBackgroundDrawable(getResources().getDrawable(color));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
        presenter.detach();
    }
}
