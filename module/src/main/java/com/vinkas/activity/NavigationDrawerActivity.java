package com.vinkas.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import io.vinkas.Account;
import com.vinkas.library.R;

public abstract class NavigationDrawerActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            onFabClick(v);
        }
    }

    public abstract void onFabClick(View v);

    private FloatingActionButton fab;
    private Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        setSupportActionBar(this.toolbar);
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton floatingActionButton) {
        this.fab = floatingActionButton;
        fab.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        setToolbar((Toolbar) findViewById(R.id.toolbar));
        setFab((FloatingActionButton) findViewById(R.id.fab));
        setDrawer((DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View content;
    private int navigationMenu, menu, layout;

    public View getContent() {
        return content;
    }

    public void setContent(View content) {
        this.content = content;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    protected ActionBarDrawerToggle toggle;
    public void setDrawer(DrawerLayout drawerLayout) {
        this.drawer = drawerLayout;
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_upload);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //toggle.onConfigurationChanged(newConfig);
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
        ViewStub viewStub = (ViewStub) findViewById(R.id.contentPanel);
        viewStub.setLayoutResource(this.layout);
        setContent(viewStub.inflate());
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int resID) {
        this.menu = resID;
    }

    public int getNavigationMenu() {
        return navigationMenu;
    }

    public void setNavigationMenu(int resID) {
        this.navigationMenu = resID;
        setNavigationView((NavigationView) findViewById(R.id.nav_view));
    }

    private View header;

    public View getHeader() {
        return header;
    }

    public void setHeader(View view) {
        header = view;
        TextView tvName = (TextView) header.findViewById(R.id.name);
        TextView tvEmail = (TextView) header.findViewById(R.id.email);
        Account account = getApp().getAccounts().getAccount();
        tvName.setText(account.getName());
        tvEmail.setText(account.getEmail());
    }

    public void setNavigationView(NavigationView view) {
        navigationView = view;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateMenu(getNavigationMenu());
        setHeader(navigationView.getHeaderView(0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenu(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
