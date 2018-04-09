package com.example.micke.clone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.micke.clone.Fragments.Comments;
import com.example.micke.clone.Fragments.DetailView;
import com.example.micke.clone.Fragments.Extra;
import com.example.micke.clone.Utils.SectionsPagerAdapter;

public class HomeScreen extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        setupViewPager();
    }

    //Adds the Camera, Main and Messages fragments
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailView());//index 0
        adapter.addFragment(new Comments());//index 1
        adapter.addFragment(new Extra());//index 2
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        /*TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_insta);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_messages);*/
    }

}
