package pl.arkadius.testapp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactDetailsActivity extends AppCompatActivity implements ContactDetailsContract.ContactDetailsView {
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_website) TextView tvWebsite;
    @BindView(R.id.tv_phoneno) TextView tvPhone;
    @BindView(R.id.photo) ImageView ivPhoto;
    private ContactDetailsContract.ContactDetailsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //Setting presenter
        presenter=new ContactsDetailsPresenterImpl();
        presenter.attach(this);
        //Getting contact
        Contact contact = getIntent().getParcelableExtra(MainActivity.EXTRA_CONTACT);
        presenter.receiveContact(contact);
        //ActionBar
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
        ActionBar ab=getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitleEnabled(true);
        ctl.setTitle(contact.getFullName());
        //Setting view
        ButterKnife.bind(this);
        presenter.setContact();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void displayContact(Contact contact) {
        tvName.setText(contact.getFullName());
        tvEmail.setText(contact.getEmail());
        tvPhone.setText(contact.getPhoneNo());
        tvWebsite.setText(contact.getWebsite());
        Picasso.with(this).load(contact.getPicURL()).into(ivPhoto);
    }

    @Override
    public void showExitingToast() {
        Toast.makeText(this,R.string.close_toast,Toast.LENGTH_SHORT).show();
    }
}
