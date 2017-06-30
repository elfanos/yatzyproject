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
import com.example.pandamove.yatzy.dice.Dice;

import java.util.ArrayList;
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
    private static final int STOP_THRESHOLD = 60;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private OnButtonClickedListener onButtonClickedListener;
    private ArrayList<DiceSurfaceView> diceList;
    private ArrayList<Dice> dices;


    boolean doneShaking = false;


    public static InGameFragment newInstance(int page,
                                             OnButtonClickedListener onButtonClickedListener,
                                             ArrayList<Dice> dices){
        System.out.println("inside fragment XD");
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putSerializable("interface",onButtonClickedListener);
        args.putParcelableArrayList("dices", dices);
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
        dices = getArguments().getParcelableArrayList("dices");

        diceList = new ArrayList<>();

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.ingame_page, container, false);
        sound_id = dice_sound.load(inflater.getContext(),R.raw.shake_dice,1);
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface)
        );
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface2)
        );
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface3)
        );
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface4)
        );
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface5)
        );
        diceList.add(
                (DiceSurfaceView) view.findViewById(R.id.dicesurface6)
        );

        //Initialize dices in default mode
        for(int i = 0; i < diceList.size(); i++){
            Dice dice = new Dice(true,0,i);
            dices.add(dice);
        }



        handler = new Handler(callback);


        /*testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickedListener.onButtonClicked(v);
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
               // System.out.println("wait is: " + speed);
                if(speed > SHAKE_THRESHOLD){
                 //   System.out.println("SHAKING");
                    if (!rolling) {
                        doneShaking = false;
                        rolling = true;
                    }
                }

                if(!doneShaking) {
                    for(int i = 0; i<diceList.size(); i++){
                        if(diceList.get(i).isSurfaceIsActive()) {
                            diceList.get(i).setRotationOnShake(speed);
                        }
                    }

                }
                    //System.out.println("le speed: " + speed);
                if(rolling && speed < STOP_THRESHOLD){
                    rolling = false;
                    doneShaking = true;
                   // System.out.println("stop casting");
                    //dice_picture.setImageResource(R.mipmap.ic_whiteone);

                    dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);
                    for(int i = 0; i < diceList.size(); i++) {
                        if(diceList.get(i).isSurfaceIsActive()) {
                            timer.schedule(new Roll(i), 100);
                        }
                    }
                    //timer.schedule(new Roll(2), 100);
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
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // First starts (gets called before everything else)
        if(sensorManager == null) {
            return;
        }

        if(menuVisible) {
            this.registerSensorListener();
        } else {
            this.unregisterSensorListener();
        }
    }
    private void registerSensorListener() {

        sensorManager.registerListener(this, sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_FASTEST);
    }
    public void getDiceSurfaceMethod(String methodName, float f_value, int i_value){
        for(int i = 0; i < diceList.size(); i++) {
            switch (methodName) {
                case "shake":
                    diceList.get(i).setRotationOnShake(f_value);
                    break;
                case "faceScore":
                    diceList.get(i).changePositionOfDice(i_value);
                    break;
                default:
            }
        }
    }

    private void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }

    class Roll extends TimerTask {
        int diceNumber = 0;
        Bundle bundle;
        Message m;
        public Roll(int dNumber){
            this.diceNumber = dNumber;
            m = new Message();
            bundle = new Bundle();
            bundle.putInt("dicenumber", dNumber); // for example
            m.setData(bundle);
        }
        public void run() {
            handler.sendMessage(m);
        }
    }
    private Callback callback = new Callback() {
        public boolean handleMessage(Message msg){
            int diceNumber = msg.getData().getInt("dicenumber");
            System.out.println("what is dicenumbeR?: " + diceNumber);
            switch (rng.nextInt(6)+1){
                case 1:
                   // diceSurface.changePositionOfDice(0);
                    //getDiceSurfaceMethod("faceScore", 0, 0);
                    diceList.get(diceNumber).changePositionOfDice(0);
                    break;
                case 2:
                    //diceSurface.changePositionOfDice(1);
                    //getDiceSurfaceMethod("faceScore", 0, 1);
                    diceList.get(diceNumber).changePositionOfDice(1);
                    break;
                case 3:
                    //diceSurface.changePositionOfDice(2);
                    //getDiceSurfaceMethod("faceScore", 0, 2);
                    diceList.get(diceNumber).changePositionOfDice(2);
                    break;
                case 4:
                    //diceSurface.changePositionOfDice(3);
                    //getDiceSurfaceMethod("faceScore", 0, 3);
                    diceList.get(diceNumber).changePositionOfDice(3);
                    break;
                case 5:
                    //diceSurface.changePositionOfDice(4);
                    //getDiceSurfaceMethod("faceScore", 0, 4);
                    diceList.get(diceNumber).changePositionOfDice(4);
                    break;
                case 6:
                    //diceSurface.changePositionOfDice(5);
                    //getDiceSurfaceMethod("faceScore", 0, 5);
                    diceList.get(diceNumber).changePositionOfDice(5);
                    break;
                default:
            }
            rolling=false;
            return true;
        }

    };
    /*public void rollTheDices(){
        if (!rolling) {
            rolling = true;

            dice_picture.setImageResource(R.mipmap.ic_whiteone);

            dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);

            timer.schedule(new Roll(), 1000);
        }
    }*/

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
        for(int i = 0; i< diceList.size(); i++){
            diceList.get(i).onPause();
        }
       // mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i< diceList.size(); i++){
            diceList.get(i).onResume();
        }
       // mUnityPlayer.resume();
    }

}
