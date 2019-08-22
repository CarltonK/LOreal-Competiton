package com.eshop.lorealnaturale;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Naturalista extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_PROFILE = 0;
    private static final int POS_HAIRDIARY = 1;
    private static final int POS_FIND = 2;
    private static final int POS_CHAT = 3;
    private static final int POS_TUTORIALS = 4;
    private static final int POS_SHOP = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hairdresser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_PROFILE).setChecked(true),
                createItemFor(POS_HAIRDIARY),
                createItemFor(POS_FIND),
                createItemFor(POS_CHAT),
                createItemFor(POS_TUTORIALS),
                createItemFor(POS_SHOP),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_PROFILE);

    }

    @Override
    public void onItemSelected(int position) {
        android.support.v4.app.Fragment fragment = null;

        switch (position){

            case 0:
                fragment = new NaturalistaProfile();
                break;

            case 1:
                fragment = new NaturalistaCalendar();
                break;

            case 2:
                fragment = new DresserFind();
                break;

            case 3:
                startActivity(new Intent(Naturalista.this, Users.class));
                finish();
                break;

            case 4:
                fragment = new DresserTutorial();
                break;

            case 5:
                fragment = new DresserShop();
                break;

            case 7:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_action_logout)
                        .setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Naturalista.this, MainActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No",null).show();

                break;

            default:
                break;

        }

        slidingRootNav.closeMenu();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("").commit();


        } else {
            Log.e("GLAM", "Error in creating fragment");
        }
    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }


    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Naturalista.this, MainActivity.class));
        finish();
    }
}
