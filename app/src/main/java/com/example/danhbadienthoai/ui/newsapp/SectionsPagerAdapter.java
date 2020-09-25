package com.example.danhbadienthoai.ui.newsapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.danhbadienthoai.R;
import com.example.danhbadienthoai.ui.usnews.UsNewsFragment;
import com.example.danhbadienthoai.ui.japanesenews.JapaneseNewsFragment;
import com.example.danhbadienthoai.ui.sportnews.SportsNewsFragment;
import com.example.danhbadienthoai.ui.trangchunews.HomeNewsFragment;

import org.jetbrains.annotations.NotNull;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4,
            R.string.tab_text_5, R.string.tab_text_6, R.string.tab_text_7, R.string.tab_text_8, R.string.tab_text_9, R.string.tab_text_10};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new HomeNewsFragment();
                break;
            case 1:
                fragment = new SportsNewsFragment();
                break;
            case 2:
                fragment = new UsNewsFragment();
                break;
            case 3:
                fragment = new JapaneseNewsFragment();
                break;
            case 4:
                fragment = new JapaneseNewsFragment();
                break;
            case 5:
                fragment = new JapaneseNewsFragment();
                break;
            case 6:
                fragment = new JapaneseNewsFragment();
                break;
            case 7:
                fragment = new JapaneseNewsFragment();
                break;
            case 8:
                fragment = new JapaneseNewsFragment();
                break;
            case 9:
                fragment = new JapaneseNewsFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

}