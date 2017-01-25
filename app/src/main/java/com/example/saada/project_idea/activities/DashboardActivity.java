package com.example.saada.project_idea.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saada.project_idea.DataBase.DataBaseHandler;
import com.example.saada.project_idea.adapters.MyPageAdapter;
import com.example.saada.project_idea.R;
import com.example.saada.project_idea.Utils.SessionManager;

import static com.example.saada.project_idea.R.id.toolbar;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    private boolean FAB_Status = false;
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    CoordinatorLayout rootLayout;
    SessionManager session;
    static String n = null;
    static String em = null;
    static String nu = null;
    TextView career;
    ViewPager viewPager;
    DataBaseHandler db;

    TabLayout tabLayout;
    SharedPreferences sharedPref;
    MyPageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        rootLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        sharedPref=getApplication().getSharedPreferences("Options", Context.MODE_PRIVATE);
        db=new DataBaseHandler(getBaseContext());

        session = new SessionManager(this);
        if (!session.loggedin()) {
            logout();
        }


        initToolbar();
        initViewPagerAndTabs();
        initNavigationDrawer();
        initFABButtons();
        initListeners();
    }

    private void logout() {
        session.setLoggedIn(false);
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }


    private void initToolbar() {

        mToolbar = (Toolbar) findViewById(toolbar);

        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

    }


    private void initViewPagerAndTabs() {


        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.SMS));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Call));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Contacts));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


         adapter = new MyPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                mToolbar.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }



    public void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        /*Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();*/
                        viewPager.setCurrentItem(0);
                        mToolbar.setTitle("SKED");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.sms:
                        Intent smsI=new Intent(DashboardActivity.this,AddSmsActivity.class);
                        startActivity(smsI);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.call:
                        Intent callI=new Intent(DashboardActivity.this,AddCallActivity.class);
                        startActivity(callI);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logut:
                        finish();
                        Toast.makeText(getApplicationContext(), "logged off", Toast.LENGTH_SHORT).show();
                        session.setLoggedIn(false);


                        break;

                    case R.id.nav_privacy_policy:
                        finish();
                        break;
                    case R.id.nav_about_us:
                        Uri uri = Uri.parse("https://www.linkedin.com/in/saad-ali-khan-360216116/"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });


        View view = navigationView.getHeaderView(0);

        TextView tv_name = (TextView) view.findViewById(R.id.name);
        TextView tv_nam = (TextView) view.findViewById(R.id.email);
        TextView tv_num = (TextView) view.findViewById(R.id.number);
        TextView tv_net = (TextView) view.findViewById(R.id.network);
        n=sharedPref.getString("nameo","");
        em=sharedPref.getString("emailo","");
        nu=sharedPref.getString("numbero","");
        tv_name.setText(n);
        tv_nam.setText(em);
        tv_num.setText(nu);
        TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = tManager.getNetworkOperatorName();
        tv_net.setText(carrierName);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    public void initListeners() {
        fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                if (FAB_Status == false) {

                    //Display FAB menu

                    expandFAB();

                    FAB_Status = true;

                } else {

                    //Close FAB menu

                    hideFAB();

                    FAB_Status = false;

                }

            }

        });
        fab1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent inte=new Intent(DashboardActivity.this,AddSmsActivity.class);
                startActivity(inte);
                fab.performClick();

            }

        });


        fab2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent calI=new Intent(DashboardActivity.this,AddCallActivity.class);
                startActivity(calI);
                fab.performClick();

            }

        });


        fab3.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Toast.makeText(getApplication(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
                fab.performClick();

            }

        });
    }

    public void initFABButtons() {
        fab = (FloatingActionButton) findViewById(R.id.fabButton);

        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);

        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);

        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);


        //Animations

        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);

        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);

        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);

        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);

        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);

        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
    }

    private void expandFAB() {


        //Floating Action Button 1

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();

        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);

        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);

        fab1.setLayoutParams(layoutParams);

        fab1.startAnimation(show_fab_1);

        fab1.setClickable(true);


        //Floating Action Button 2

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();

        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);

        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);

        fab2.setLayoutParams(layoutParams2);

        fab2.startAnimation(show_fab_2);

        fab2.setClickable(true);


        //Floating Action Button 3

        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);

        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);

        fab3.setLayoutParams(layoutParams3);

        fab3.startAnimation(show_fab_3);

        fab3.setClickable(true);

    }

    private void hideFAB() {


        //Floating Action Button 1

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();

        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);

        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);

        fab1.setLayoutParams(layoutParams);

        fab1.startAnimation(hide_fab_1);

        fab1.setClickable(false);


        //Floating Action Button 2

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();

        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);

        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);

        fab2.setLayoutParams(layoutParams2);

        fab2.startAnimation(hide_fab_2);

        fab2.setClickable(false);


        //Floating Action Button 3

        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);

        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);

        fab3.setLayoutParams(layoutParams3);

        fab3.startAnimation(hide_fab_3);

        fab3.setClickable(false);

    }



}

        /* Fragment fragment = null;
         
                //initializing the fragment object which is selected
                switch (itemId) {
                    case R.id.nav_menu1:
                        fragment = new Menu1();
                        break;
                    case R.id.nav_menu2:
                        fragment = new Menu2();
                        break;
                    case R.id.nav_menu3:
                        fragment = new Menu3();
                        break;
                }
         
                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }*/