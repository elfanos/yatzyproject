package com.example.pandamove.yatzy;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;
import android.test.ApplicationTestCase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.pandamove.yatzy.fragments.ScoreFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSensorManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by rallesport on 2017-06-01.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class ScoreFragmentTest extends ApplicationTestCase<Application> {

        private SensorManager sensorManager;
        private ShadowSensorManager shadow;
        public ScoreFragmentTest(){
                super(Application.class);
        }
        GameActivity gameActivity;
        ScoreFragment scoreFragmentFragment;

        @Before
        public void setUp(){

                gameActivity = Robolectric.setupActivity(GameActivity.class);
                scoreFragmentFragment = new ScoreFragment();
                startFragment(scoreFragmentFragment);
        }

        @Test
        public void testGameActivity(){
                Assert.assertNotNull(gameActivity);
        }

        @Test
        public void fragmentIsNotNull() {
                assertThat(scoreFragmentFragment).isNotNull();
        }
        @Test
        public void playerSize() {
            assertThat(scoreFragmentFragment.getPlayerListSize()).isEqualTo(4);
        }

       private void startFragment(Fragment fragment){
                FragmentManager fragmentManager = gameActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment,null);
                fragmentTransaction.commit();
        }

}
