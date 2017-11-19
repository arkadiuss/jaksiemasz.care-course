package pl.arkadius.testapp;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //Creating contact
        Contact contact = new Contact("Apple","Corporation","kiepskafirma.com","kiepska@firma.com","123456789","http://cdn.androidbeat.com/wp-content/uploads/2014/09/Android_eating_Apple_evil.jpg");
        //ActionBar
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(t);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout abl = (AppBarLayout) findViewById(R.id.app_bar);
        ctl.setTitleEnabled(true);
        ctl.setTitle("anything");
        ctl.setCollapsedTitleTextAppearance(R.style.TextAppearance_App_Title_Collapsed);
        ctl.setExpandedTitleTextAppearance(R.style.TextAppearance_App_Title_Expanded);

        //Setting view
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(contact.getName()+contact.getSurname());
        TextView tvEmail = (TextView) findViewById(R.id.tv_email);
        tvEmail.setText(contact.getEmail());
        TextView tvPhone = (TextView) findViewById(R.id.tv_phoneno);
        tvPhone.setText(contact.getPhoneNo());
        TextView tvWebsite = (TextView) findViewById(R.id.tv_website);
        tvWebsite.setText(contact.getWebsite());
        ImageView ivPhoto = (ImageView) findViewById(R.id.photo);
        Picasso.with(this).load(contact.getPicURL()).into(ivPhoto);
    }

    public void closeButton(View v){
        finish();
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
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,R.string.close_toast,Toast.LENGTH_SHORT).show();
    }
}
