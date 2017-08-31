package com.example.pandamove.yatzy.fragments;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pandamove.yatzy.*;
import com.example.pandamove.yatzy.controllers.OnButtonClickedListener;
import com.example.pandamove.yatzy.dice.Dice;

import java.lang.ref.WeakReference;
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
    private ArrayList<ImageButton> diceSelectedButtons;
    private Button buttonThrow;
    private ArrayList<ThrowThread> throwRunnables;
    private ThrowThread throwRunnable;
    boolean doneShaking = false;
    private Random rand = new Random();


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
        diceSelectedButtons = new ArrayList<>();

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.ingame_page, container, false);
        sound_id = dice_sound.load(inflater.getContext(),R.raw.shake_dice,1);
        throwRunnables = new ArrayList<>();
        buttonThrow = (Button) view.findViewById(R.id.throwButton);
        this.initializeDiceSurface(view);
        this.initializeDiceSelectedButtons(view);
        this.setListenerForButton();
        //Initialize dices in default mode
        for(int i = 0; i < diceList.size(); i++){
            Dice dice = new Dice(true,0,i);
            dices.add(dice);
        }
        //Initialize throw threads
        for(int i = 0; i < diceList.size(); i++){
            ThrowThread throwThread = new ThrowThread(diceList.get(i), dices.get(i));
            int random = rand.nextInt((5)+1);
            diceList.get(i).changePositionOfDice(random);
            dices.get(i).setScore(random);
            throwRunnables.add(throwThread);
        }

        throwRunnable = new ThrowThread(diceList.get(0),dices.get(1));
        System.out.println("le score?: " + diceList.get(0).getCurrentDiceNumber());

        diceSelectedButtons.get(2).setVisibility(View.VISIBLE);

        handler = new Handler(callback);

        buttonThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < throwRunnables.size(); i++){
                    if(i+1 < throwRunnables.size()) {
                        diceList.get(0).queueEvent(throwRunnables.get(i + 1));
                        if (throwRunnables.get(i + 1).getRunning()) {
                            throwRunnables.get(i + 1).endThis();
                        } else {
                            throwRunnables.get(i + 1).start();
                        }
                    }

                }
            }
        });
        System.out.println("waddup??");
        return view;
    }
    /**
     *  Insert all diceSurface into a list
     *
     * @param view the view to get buttons from xml
     * */
    public void initializeDiceSurface(View view){
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
    }
    /**
     * Insert all Dice buttons into a list
     *
     * @param v the view to get buttons from xml
     * */
    public void initializeDiceSelectedButtons(View v){
        diceSelectedButtons.add(
                (ImageButton) v.findViewById(R.id.s_diceOne)
        );
        diceSelectedButtons.add(
                (ImageButton) v.findViewById(R.id.s_diceTwo)
        );
        diceSelectedButtons.add(
                (ImageButton) v.findViewById(R.id.s_diceThree)
        );
        diceSelectedButtons.add(
                (ImageButton) v.findViewById(R.id.s_diceFour)
        );
        diceSelectedButtons.add(
                (ImageButton) v.findViewById(R.id.s_diceFive)
        );

    }
    /**
     * Set listener for a dice buttons inside
     * diceSelectedButtons
     * */
    public void setListenerForButton(){
        for(int i = 0; i < diceSelectedButtons.size(); i++){
            diceSelectedButtons.get(i).setOnClickListener(
                    new DiceSelectedListener()
            );
        }
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
            switch (rng.nextInt(6)+1){
                case 1:
                   // diceSurface.changePositionOfDice(0);
                    //getDiceSurfaceMethod("faceScore", 0, 0);
                    diceList.get(diceNumber).changePositionOfDice(0);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
                    break;
                case 2:
                    //diceSurface.changePositionOfDice(1);
                    //getDiceSurfaceMethod("faceScore", 0, 1);
                    diceList.get(diceNumber).changePositionOfDice(1);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
                    break;
                case 3:
                    //diceSurface.changePositionOfDice(2);
                    //getDiceSurfaceMethod("faceScore", 0, 2);
                    diceList.get(diceNumber).changePositionOfDice(2);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
                    break;
                case 4:
                    //diceSurface.changePositionOfDice(3);
                    //getDiceSurfaceMethod("faceScore", 0, 3);
                    diceList.get(diceNumber).changePositionOfDice(3);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
                    break;
                case 5:
                    //diceSurface.changePositionOfDice(4);
                    //getDiceSurfaceMethod("faceScore", 0, 4);
                    diceList.get(diceNumber).changePositionOfDice(4);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
                    break;
                case 6:
                    //diceSurface.changePositionOfDice(5);
                    //getDiceSurfaceMethod("faceScore", 0, 5);
                    diceList.get(diceNumber).changePositionOfDice(5);
                    dices.get(diceNumber).setScore(
                            diceList.get(diceNumber).getCurrentDiceNumber()
                    );
                    System.out.println("jaman score: " +  dices.get(diceNumber).getScore());
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

    public class  ThrowThread implements Runnable{
        private volatile boolean running = false;
        private Thread thread;
        private DiceSurfaceView diceSurface;
        private Dice dice;
        private DiceSurfaceView surface;
        boolean reversedX = false;
        boolean reversedY = false;
        boolean positiveAngleX = false;
        boolean positiveAngleY = false;
        boolean isRotationXDone = false;
        boolean isRotationYDone = false;
        int[] currentAngle = null;
        int xAngle = 0;
        int yAngle = 0;

        public ThrowThread(DiceSurfaceView diceSurface, Dice dice){
            this.surface = diceSurface;
            this.dice = dice;
            currentAngle = surface.getAngleOfDice(
                    this.dice.getScore()
            );
            xAngle = currentAngle[0];
            yAngle = currentAngle[1];
        }
        @Override
        public void run(){
            while(running){
                try{
                    /*X rotation*/
                    surface.rotationOnDice(
                            this, currentAngle, xAngle,
                            reversedX, positiveAngleX,"x", isRotationXDone
                    );
                    /*Y rotation*/
                    surface.rotationOnDice(
                            this, currentAngle, yAngle,
                            reversedY, positiveAngleY,"y", isRotationYDone
                    );
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    System.out.println("interrupted e");
                }

            }
        }
        synchronized public void start(){
            running = true;
            this.resetValues();
            if (thread == null) {
                thread = new Thread(this);
                thread.start();
            }
        }
        synchronized public void resetValues(){
            currentAngle = surface.getAngleOfDice(dice.getScore());
            xAngle = currentAngle[0];
            yAngle = currentAngle[1];
            reversedX = false;
            reversedY = false;
            positiveAngleX = false;
            positiveAngleY = false;
            isRotationXDone = false;
            isRotationYDone = false;
        }
        synchronized public void endThis() {
            if (thread != null) {
               // try {
                running = false;
                thread = null;
                /*}catch (InterruptedException e){

                    System.out.println("interrupt failure");
                }*/
            }
        }
        public synchronized void setXAngle(int value){
            System.out.println(value);
            xAngle = value;
        }

        public synchronized void setYAngle(int value){
            System.out.println(value);
            yAngle = value;
        }

        public synchronized void setReversedX(boolean reversedX) {
            this.reversedX = reversedX;
        }

        public synchronized void setReversedY(boolean reversedY) {
            this.reversedY = reversedY;
        }

        public synchronized void setPositiveAngleX(boolean positiveAngleX) {
            this.positiveAngleX = positiveAngleX;
        }

        public synchronized void setPositiveAngleY(boolean positiveAngleY) {
            this.positiveAngleY = positiveAngleY;
        }

        public synchronized void setRotationXDone(boolean rotationXDone) {
            isRotationXDone = rotationXDone;
        }

        public synchronized void setRotationYDone(boolean rotationYDone) {
            isRotationYDone = rotationYDone;
        }
        public boolean isRotationXDone() {
            return isRotationXDone;
        }

        public boolean isRotationYDone() {
            return isRotationYDone;
        }

        public Boolean getRunning(){
            return running;
        }
    }


}
