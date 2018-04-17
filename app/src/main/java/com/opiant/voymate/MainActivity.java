package com.opiant.voymate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout frameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private static MainActivity instance;
    Toolbar toolbar;
    TextView userName,userEmail;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent dataIntent = getIntent();
        Bundle bundle = dataIntent.getExtras();
        if (bundle!=null) {
            Email = bundle.getString("Email");
        }
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*Intent in = getIntent();
        //Getting bundle
        Bundle b2 = in.getExtras();
        if (b2!=null) {
            String Key = in.getStringExtra("Key");

            if (Key.equals("atm")){
                Bundle b1 = new Bundle();
                b1.putString("Key",Key);
                Fragment exploreScreen = new HomeScreen();
                exploreScreen.setArguments(b1);
                FragmentTransaction exploreScreenTransaction = getSupportFragmentManager().beginTransaction();
                exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
                exploreScreenTransaction.addToBackStack(null);
                exploreScreenTransaction.commit();

            }

            Toast.makeText(getApplicationContext(), Key, Toast.LENGTH_LONG).show();
        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frameLayout = (FrameLayout) findViewById(R.id.containerView1);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.containerView1, new HomeScreen());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

        // Calling HeaderView Item and Making Click Listener on That
        SharedPreferences IdShared = getSharedPreferences("VoyMate", MODE_PRIVATE);
        Email= IdShared.getString("Email", "");
        View headerview = navigationView.getHeaderView(0);
        userName = (TextView) headerview.findViewById(R.id.name);
        userEmail = (TextView) headerview.findViewById(R.id.email);
        userEmail.setText(Email);

        Toast.makeText(getApplicationContext(), "Welcome:"+Email, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //finish();

            } else {
                getFragmentManager().popBackStack();
                //toolbar.clearFocus();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Fragment settings = new Settings();
            FragmentTransaction settingsTransaction = getSupportFragmentManager().beginTransaction();
            settingsTransaction.replace(R.id.containerView1, settings);
            settingsTransaction.addToBackStack(null);
            settingsTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_explore) {

            Fragment exploreScreen = new ExploreScreen();
            FragmentTransaction exploreScreenTransaction = getSupportFragmentManager().beginTransaction();
            exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
            exploreScreenTransaction.addToBackStack(null);
            exploreScreenTransaction.commit();

        } else if (id == R.id.nav_book) {


        } else if (id == R.id.nav_nearby) {

            Fragment nearby = new NearBy();
            FragmentTransaction nearbyTransaction = getSupportFragmentManager().beginTransaction();
            nearbyTransaction.replace(R.id.containerView1, nearby);
            nearbyTransaction.addToBackStack(null);
            nearbyTransaction.commit();


        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download My App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Android app:-\n https://play.google.com/store/apps/details?id=Orion.Soft \n\n");
            sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, "YouTube Video:-\n https://youtu.be/VkfchLM144Q");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_tool) {

            Fragment tools = new Tools();
            FragmentTransaction toolsTransaction = getSupportFragmentManager().beginTransaction();
            toolsTransaction.replace(R.id.containerView1, tools);
            toolsTransaction.addToBackStack(null);
            toolsTransaction.commit();
        }
        else if (id == R.id.nav_help){
            Fragment help = new Help();
            FragmentTransaction helpTransaction = getSupportFragmentManager().beginTransaction();
            helpTransaction.replace(R.id.containerView1, help);
            helpTransaction.addToBackStack(null);
            helpTransaction.commit();

        }
        else if(id==R.id.nav_about){
            Fragment about = new About();
            FragmentTransaction aboutTransaction = getSupportFragmentManager().beginTransaction();
            aboutTransaction.replace(R.id.containerView1, about);
            aboutTransaction.addToBackStack(null);
            aboutTransaction.commit();

        }

        else if (id==R.id.nav_logout){
            SharedPreferences sp = getSharedPreferences("VoyMate", MODE_PRIVATE);
            SharedPreferences.Editor ueditor = sp.edit();
            ueditor.putString("Email","");
            ueditor.apply();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}