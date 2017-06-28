package com.example.pandamove.yatzy.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pandamove.yatzy.*;
import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Rallmo on 2017-04-05.
 */
public class InGameFragment extends Fragment implements SensorEventListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    ImageView dice_picture;		//reference to dice picture
    Random rng=new Random();	//generate random numbers
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound_id;		//Used to control sound stream return by SoundPool
    Handler handler;	//Post message to start roll
    Timer timer=new Timer();	//Used to implement feedback to user
    boolean rolling=false;		//Is die rolling?
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private static final int SHAKE_THRESHOLD = 1200;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private OnButtonClickedListener onButtonClickedListener;
    Cubesurfaceview diceSurface;


    public static InGameFragment newInstance(int page,
                                             OnButtonClickedListener onButtonClickedListener){
        System.out.println("inside fragment XD");
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putSerializable("interface",onButtonClickedListener);
        InGameFragment fragment = new InGameFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public int getShowIndex(){
        return this.getArguments().getInt("ARG_PAGE",0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        onButtonClickedListener =
                (OnButtonClickedListener) getArguments().getSerializable("interface");

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.ingame_page, container, false);
        sound_id = dice_sound.load(inflater.getContext(),R.raw.shake_dice,1);
        dice_picture = (ImageView) view.findViewById(R.id.imageButton);
        Button testButton = (Button) view.findViewById(R.id.testButton);

        diceSurface = (Cubesurfaceview) view.findViewById(R.id.surfaceviewdice);

        System.out.println("before handle");

        handler = new Handler(callback);


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickedListener.onButtonClicked(v);
            }

        });

       /* dice_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rolling) {
                    rolling = true;

                    dice_picture.setImageResource(R.mipmap.ic_whiteone);

                    dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);

                    timer.schedule(new Roll(), 500);
                }
            }
        });*/
        return view;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0], y = event.values[1];
        float z = event.values[2];
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long curTime = System.currentTimeMillis();
            if((curTime - lastUpdate) > 100){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if(speed > SHAKE_THRESHOLD){
                    System.out.println("SHAKING");
                    if (!rolling) {
                        rolling = true;

                        dice_picture.setImageResource(R.mipmap.ic_whiteone);

                        dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);

                        timer.schedule(new Roll(), 1000);
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    private void registerSensorListener() {

        sensorManager.registerListener(this, sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }

    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }
    private Callback callback = new Callback() {
        public boolean handleMessage(Message msg){

            switch (rng.nextInt(6)+1){
                case 1:
                    dice_picture.setImageResource(R.mipmap.ic_whiteone);
                    break;
                case 2:
                    dice_picture.setImageResource(R.mipmap.ic_whitetwo);
                    break;
                case 3:
                    dice_picture.setImageResource(R.mipmap.ic_whitethree);
                    break;
                case 4:
                    dice_picture.setImageResource(R.mipmap.ic_whitefour);
                    break;
                case 5:
                    dice_picture.setImageResource(R.mipmap.ic_whitefive);
                    break;
                case 6:
                    dice_picture.setImageResource(R.mipmap.ic_whitesix);
                    break;
                default:
            }
            rolling=false;
            return true;
        }

    };
    public void rollTheDices(){
        if (!rolling) {
            rolling = true;

            dice_picture.setImageResource(R.mipmap.ic_whiteone);

            dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);

            timer.schedule(new Roll(), 1000);
        }
    }

    // Quit Unity
    @Override
    public void onDestroy() {
        //  mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    public void onPause() {
        super.onPause();
        diceSurface.onPause();
       // mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    public void onResume() {
        super.onResume();
        diceSurface.onResume();
       // mUnityPlayer.resume();
    }

}
