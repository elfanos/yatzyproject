package com.example.pandamove.yatzy.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to keep track of fragment state using an adapter
 *
 * @author Rasmus Dahlkvist
 */
public class FragmentSliderPagerAdapter extends FragmentStatePagerAdapter{
    private String tabTitles[] = new String[]{"Board", "Score"};
    private HashMap<String, Integer> listOfPossibleScores;
    private SparseArray<Fragment> fragments;
    private ArrayList<Dice> dices;
    private ArrayList<Player> players;


    /**
     * Constructor for fragmensliderpageerAdapter
     *
     * @param fm a fragmentmanager
     * @param fragments a sparse array of all the fragment initialized
     * @param dices all the dices in the game in a arraylist
     * @param listOfPossibleScores list of all the possibles score for testing purpose
     * @param players all the players in the game
     * */
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

    /**
     * getNumber of fragments
     * @return number of fragment not used
     * */
    @Override
    public int getCount(){
        return 2;
    }

    /**
     * Get a speciofic fragment
     *
     * @return fragment new instance of the fragment
     * */
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
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    /**
     * @param container all the views
     * @param position the position of the fragment
     * @param object specific objects the class name
     * */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * @param position which fragment in the tabTitles list
     * @return the tabtitle of a fragment
     * */
    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
