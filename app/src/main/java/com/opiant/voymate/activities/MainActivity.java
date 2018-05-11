package com.opiant.voymate.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opiant.voymate.Constant;
import com.opiant.voymate.R;
import com.opiant.voymate.fragments.About;
import com.opiant.voymate.fragments.ExploreScreen;
import com.opiant.voymate.fragments.Help;
import com.opiant.voymate.fragments.HomeScreen;
import com.opiant.voymate.fragments.MyProfile;
import com.opiant.voymate.fragments.NearBy;
import com.opiant.voymate.fragments.Settings;
import com.opiant.voymate.fragments.Tools;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout frameLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private static MainActivity instance;
    Toolbar toolbar;
    Menu menu;
    ImageView imageView;
    TextView userName,userEmail,placeName;
    String Email,Name,Image,cityName;
    SharedPreferences IdShared;
    private static final int MENU_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        whiteNotificationBar(toolbar);
        runPermission();
        initViews();



    }

    public void initViews(){
        Intent dataIntent = getIntent();
        Bundle bundle = dataIntent.getExtras();
        if (bundle!=null) {
            Email = bundle.getString("email");
            Name = bundle.getString("myname");
        }
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //int ID = navigationView.getMenu().findItem()



        frameLayout = findViewById(R.id.containerView1);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.containerView1, new ExploreScreen());
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();


        // Calling HeaderView Item and Making Click Listener on That
        IdShared = getSharedPreferences("VoyMate", MODE_PRIVATE);
        Email= IdShared.getString("email", "");
        Name= IdShared.getString("myname", "");
        /*String latitude = IdShared.getString("Lat","");
        String longitude = IdShared.getString("Lng","");
        Double lat = 28.4816551;
        Double lng = 77.1872857;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            cityName = addresses.get(0).getAddressLine(0);

            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            String Name = addresses.get(0).getLocality();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        View headerview = navigationView.getHeaderView(0);
        userName = headerview.findViewById(R.id.name);
        userEmail = headerview.findViewById(R.id.email);
        imageView = headerview.findViewById(R.id.imageView);
        userEmail.setText(Email);
        userName.setText(Name);
        //placeName = (TextView) headerview.findViewById(R.id.location);
        //placeName.setText(cityName);

        //Toast.makeText(getApplicationContext(), "Welcome:"+ Email,Toast.LENGTH_LONG).show();

       /* Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;*/

        //addMenuItemInNavMenuDrawer();
    }

    private void addMenuItemInNavMenuDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        Menu submenu = menu.addSubMenu("New Super SubMenu");

        submenu.add("Super Item1");
        submenu.add("Super Item2");
        submenu.add("Super Item3");

        navView.invalidate();
    }

    public void runPermission(){

        // Get runtime permissions for Android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.VIBRATE,

                }, 0);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        updateMenuTitles();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menu.clear();
        //menu.add(0, MENU_EDIT, Menu.NONE, getString(R.string.haddress)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //menu.add(0, MENU_DELETE, Menu.NONE, getString(R.string.menu_action_delete)).setIcon(R.drawable.ic_action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onPrepareOptionsMenu(menu);
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

         if (id == R.id.nav_explore) {

             //item.setTitle("लॉग इन करे");
            Fragment exploreScreen = new ExploreScreen();
            FragmentTransaction exploreScreenTransaction = getSupportFragmentManager().beginTransaction();
            exploreScreenTransaction.replace(R.id.containerView1, exploreScreen);
            exploreScreenTransaction.addToBackStack(null);
            exploreScreenTransaction.commit();

        }
        else if (id == R.id.nav_nearby) {

            Fragment nearby = new NearBy();
            FragmentTransaction nearbyTransaction = getSupportFragmentManager().beginTransaction();
            nearbyTransaction.replace(R.id.containerView1, nearby);
            nearbyTransaction.addToBackStack(null);
            nearbyTransaction.commit();


        }
        else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_CONTENT);
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_with)));
            //startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }
        else if (id == R.id.nav_tool) {

            Fragment tools = new Tools();
            FragmentTransaction toolsTransaction = getSupportFragmentManager().beginTransaction();
            toolsTransaction.replace(R.id.containerView1, tools);
            toolsTransaction.addToBackStack(null);
            toolsTransaction.commit();
        }
        else if (id == R.id.nav_profile){
            Fragment myProfile = new MyProfile();
            FragmentTransaction myProfileTransaction = getSupportFragmentManager().beginTransaction();
            myProfileTransaction.replace(R.id.containerView1, myProfile);
            myProfileTransaction.addToBackStack(null);
            myProfileTransaction.commit();

        }
        else if(id==R.id.nav_about){
            Fragment about = new About();
            FragmentTransaction aboutTransaction = getSupportFragmentManager().beginTransaction();
            aboutTransaction.replace(R.id.containerView1, about);
            aboutTransaction.addToBackStack(null);
            aboutTransaction.commit();

        }
       else if(id==R.id.help){
            Fragment help = new Help();
            FragmentTransaction searchTransaction = getSupportFragmentManager().beginTransaction();
            searchTransaction.replace(R.id.containerView1, help);
            searchTransaction.addToBackStack(null);
            searchTransaction.commit();

        }

        else if (id==R.id.nav_logout){
            SharedPreferences sp = getSharedPreferences("VoyMate", MODE_PRIVATE);
            SharedPreferences.Editor ueditor = sp.edit();
            ueditor.putString("email","");
            ueditor.apply();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateMenuTitles() {
        String language = IdShared.getString("language","");
        MenuItem Settings = menu.findItem(R.id.action_settings);

        if (language.equals("hi")) {
            Settings.setTitle(R.string.hisettings);
            toolbar.setTitle(R.string.app_name_hi);

        }
        else if (language.equals("fr")){

            Settings.setTitle(R.string.fsetting);
            toolbar.setTitle(R.string.app_name);
        }
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.parseColor("#11A2fb"));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(event);
    }
}
