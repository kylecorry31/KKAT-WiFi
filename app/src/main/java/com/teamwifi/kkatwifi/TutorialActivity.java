package com.teamwifi.kkatwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamwifi.kkatwifi.MainActivity;
import com.teamwifi.kkatwifi.R;
import com.teamwifi.kkatwifi.tutorial.AnalysisTutorialContent;
import com.teamwifi.kkatwifi.tutorial.DeadzoneTutorialContent;
import com.teamwifi.kkatwifi.tutorial.MainTutorialContent;
import com.teamwifi.kkatwifi.tutorial.OMINNTutorialContent;
import com.teamwifi.kkatwifi.ui.DotsPageIndicator;
import com.teamwifi.kkatwifi.util.Settings;

/**
 * Created by kyle on 9/6/15.
 */
public class TutorialActivity extends FragmentActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;

    private TextView skip, done;

    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_tutorial);
        getWindow().setBackgroundDrawable(null);
        DotsPageIndicator progressDots = (DotsPageIndicator) findViewById(R.id.progressDots);
        skip = (TextView) findViewById(R.id.skip);
        done = (TextView) findViewById(R.id.done);
        next = (ImageButton) findViewById(R.id.next);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.setFirst(getApplicationContext(), false);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.setFirst(getApplicationContext(), false);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ContentPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });
        progressDots.setViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {

                    skip.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                } else if (position == 3) {

                    skip.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class ContentPageAdapter extends FragmentStatePagerAdapter {
        public ContentPageAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainTutorialContent();
                case 1:
                    return new AnalysisTutorialContent();
                case 2:
                    return new OMINNTutorialContent();
                case 3:
                    return new DeadzoneTutorialContent();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}