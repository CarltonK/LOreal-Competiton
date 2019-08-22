package com.eshop.lorealnaturale;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Hairdresser extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_PROFILE = 0;
    private static final int POS_APPOINTMENTS = 1;
    private static final int POS_CHAT = 2;
    private static final int POS_TUTORIALS = 3;
    private static final int POS_SHOP = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naturalist);

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
                createItemFor(POS_APPOINTMENTS),
                createItemFor(POS_CHAT),
                createItemFor(POS_TUTORIALS),
                createItemFor(POS_SHOP),
                new SpaceItem(40),
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
               fragment = new DresserProfile();
                break;

            case 1:
                fragment = new DresserDiary();
                break;

            case 2:
                startActivity(new Intent(Hairdresser.this, Users.class));
                finish();
                break;

            case 3:
                fragment = new DresserTutorial();
                break;

            case 4:
                fragment = new DresserShop();
                break;

            case 6:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_action_logout)
                        .setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Hairdresser.this, MainActivity.class));
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
        return getResources().getStringArray(R.array.ld_activityScreenHDTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenHDIcons);
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
        startActivity(new Intent(Hairdresser.this, MainActivity.class));
        finish();
    }

}
