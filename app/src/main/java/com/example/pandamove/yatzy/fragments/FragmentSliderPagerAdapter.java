package com.example.pandamove.yatzy.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.pandamove.yatzy.controllers.GameActivityInterface;
import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rallmo on 2017-04-05.
 */
public class FragmentSliderPagerAdapter extends FragmentStatePagerAdapter{
    private String tabTitles[] = new String[]{"Board", "Score"};
    private Context context;
   // private InGameFragment inGameFragment;
    private ScoreFragment scoreFragment;
    private HashMap<String, Integer> listOfPossibleScores;
    private SparseArray<Fragment> fragments;
    private ArrayList<Dice> dices;
    private ArrayList<Player> players;
    private int[] imageResId = {

    };

    public FragmentSliderPagerAdapter(FragmentManager fm,
                                      SparseArray<Fragment> fragments, ArrayList<Dice> dices,
                                      HashMap<String, Integer> listOfPossibleScores,
                                     ArrayList<Player> players){
        super(fm);
        this.fragments = fragments;
        this.dices = dices;
        this.listOfPossibleScores = listOfPossibleScores;
        this.players = players;
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
                return inGameFragment.newInstance(
                        1,
                       listOfPossibleScores,
                        dices
                );
            case 2:
                return ScoreFragment.newInstance(
                        2,
                        dices,
                        players
                );
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
