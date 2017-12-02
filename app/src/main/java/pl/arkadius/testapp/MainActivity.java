package pl.arkadius.testapp;

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

public class MainActivity extends AppCompatActivity implements MainContract.MainView,ContactsAdapter.Listener{
    @BindView(R.id.recycler_cons)  RecyclerView rv;
    private ContactsAdapter adCon;
    private MainContract.MainPresenter presenter;
    @BindView(R.id.progress_bar)  ProgressBar progressBar;
    @BindView(R.id.fail_text) TextView failText;
    @BindView(R.id.reconnect_button) Button reconnectButton;

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

    @OnClick(R.id.reconnect_button)
    public void reconnect(){
        presenter.onReconnectButtonClick();
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
        reconnectButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFailView() {
        failText.setVisibility(View.GONE);
        reconnectButton.setVisibility(View.GONE);
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
