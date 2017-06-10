package com.example.pandamove.yatzy.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class FragmentSliderPagerAdapter extends FragmentStatePagerAdapter{
    private String tabTitles[] = new String[]{"Board", "Score"};
    private Context context;
   // private InGameFragment inGameFragment;
    private ScoreFragment scoreFragment;
    private OnButtonClickedListener onButtonClickedListener;
    private SparseArray<Fragment> fragments;
    private int[] imageResId = {

    };

    public FragmentSliderPagerAdapter(FragmentManager fm, OnButtonClickedListener onButtonClickedListener,
                                      SparseArray<Fragment> fragments){
        super(fm);
        this.onButtonClickedListener = onButtonClickedListener;
        this.fragments = fragments;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public Fragment getItem(int position){

        switch (position+1) {
            case 1:
                InGameFragment inGameFragment = new InGameFragment();
                return inGameFragment.newInstance(1, onButtonClickedListener);
            case 2:
                return ScoreFragment.newInstance(2);
            default:
        }
        return null;
    }

    @Override
    public int getItemPosition(Object item){
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragment(int position){
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
