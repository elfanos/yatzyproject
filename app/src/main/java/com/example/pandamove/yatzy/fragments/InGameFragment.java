package com.example.pandamove.yatzy.fragments;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pandamove.yatzy.*;
import com.example.pandamove.yatzy.OpenGLClasses.DiceSurfaceView;
import com.example.pandamove.yatzy.controllers.CommunicationHandler;
import com.example.pandamove.yatzy.controllers.GameObjects;
import com.example.pandamove.yatzy.dice.Dice;
import com.example.pandamove.yatzy.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



/**
 * Created by Rallmo on 2017-04-05.
 *
 * The class InGameFragment contains all the values for
 * playing the game. All the dices on the playboard and keeps track
 * of the user current score and such.
 *
 * @author Rasmus Dahlkvist
 */
public class InGameFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound_id;		//Used to control sound stream return by SoundPool
    private Handler throwHandler;
    private HashMap listOfPossibleScores;
    private ArrayList<DiceSurfaceView> diceList;
    private SparseArray<Dice> hashDices;
    private Button buttonThrow;
    private ArrayList<ThrowThread> throwRunnables;
    private ThrowThread throwRunnable;
    private Random rand = new Random();
    public int numberOfThreadsDone = 0;
    public int numberOfThreadsStarted = 0;
    private boolean sendScores = false;


    /**
     * Constructor/NewInstance of the fragment
     *
     * */
    public static InGameFragment newInstance(int page,
                                             HashMap<String,Integer> listOfPossibleScores){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putSerializable("hashmap", listOfPossibleScores);
        InGameFragment fragment = new InGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * When the fragment is being created new values is
     * initialized.
     *
     * @param savedInstanceState keep track of old values
     * */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        listOfPossibleScores =
                (HashMap) getArguments().getSerializable("hashmap");
        diceList = new ArrayList<>();

    }
    /**
     * Recreated previous stored values
     *
     * @param savedInstanceState contains stored values
     * */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listOfPossibleScores = ((HashMap) savedInstanceState.getSerializable("scores"));
        }
    }
    /**
     * Store values inside a bundle
     *
     * @param outState keeps the value that is going to be stored
     * */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("scores", listOfPossibleScores);
    }

    /**
     * Pause all glSurfaceView when activity is inactive
     * */
    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0; i < diceList.size(); i+=1){
            if(diceList.get(i) != null){diceList.get(i).onPause();}
        }
    }

    /**
     * Resume glSurface view when activity is active again
     * */
    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i < diceList.size(); i+=1){
            if(diceList.get(i) != null){diceList.get(i).onResume();}
        }
    }



    /**
     *
     * Create a new view for the fragment with elements that's been
     * declared in the xml-file
     *
     * @param inflater inflate all the property to create a view
     * @param container which keep track of all xml values
     * @param savedInstanceState keep track of old states if
     *                           the application had an error or
     *                           is paused during the game
     *
     * */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.ingame_page, container, false);
        sound_id = dice_sound.load(inflater.getContext(),R.raw.shake_dice,1);
        throwRunnables = new ArrayList<>();
        hashDices = new SparseArray<>();
        buttonThrow = (Button) view.findViewById(R.id.throwButton);
        CommunicationHandler.getInstance().setInGameView(view);
        CommunicationHandler.getInstance().updateView(view);
        CommunicationHandler.getInstance().updateHighScore(view);
        this.initializeDiceSurface(view);
        this.initializeDices();
        throwHandler = new Handler(throwCallback);
        this.initializeThrowThreads();
        CommunicationHandler.getInstance().setPlayerView(view);
        buttonThrow.setOnClickListener(new ThrowListener(view));
        CommunicationHandler.getInstance().getThrows(view);
        return view;
    }
    /**
     * Initialize the throwthres for the opengl animation
     * */
    public void initializeThrowThreads(){
        if(CommunicationHandler.getInstance().getGameObjects().getGlScore().size() == 0) {
            for (int i = 0; i < diceList.size(); i++) {
                ThrowThread throwThread = new ThrowThread(diceList.get(i), hashDices.get(i),
                        hashDices, listOfPossibleScores, throwHandler);
                int random = rand.nextInt((5) + 1);
                diceList.get(i).changePositionOfDice(random + 1);
                hashDices.get(i).setScore(random + 1);
                throwRunnables.add(throwThread);
            }
            if(CommunicationHandler.getInstance().isFirstRound()) {
                this.sendMessageToHandler(hashDices);
            }
        }else{
            this.setPreviousValues();
        }
        throwRunnable = new ThrowThread(diceList.get(0),hashDices.get(1),
                hashDices,listOfPossibleScores,throwHandler);
    }
    public void sendMessageToHandler(SparseArray<Dice> dices){
        Message m = new Message();
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray("scoreArray", dices); // for example
        m.setData(bundle);
        throwHandler.sendMessage(m);
    }

    /**
     * Get the previous values from the dices which is stored
     * inside the savedInstanceState in the activity
     * */
    public void setPreviousValues(){
        ArrayList<Integer> dicesCurrentScores =
                CommunicationHandler.getInstance().getGameObjects().getGlScore();
        for (int i = 0; i < diceList.size(); i+=1) {
            ThrowThread throwThread = new ThrowThread(diceList.get(i), hashDices.get(i),
                    hashDices, listOfPossibleScores, throwHandler);
            diceList.get(i).changePositionOfDice(dicesCurrentScores.get(i));
            hashDices.get(i).setScore(dicesCurrentScores.get(i));
            throwRunnables.add(throwThread);
        }
    }

    /**
     * Initialize all the dices in the game
     * */
    public void initializeDices(){
        if(hashDices.size() == 0) {
            for (int i = 0; i < diceList.size(); i++) {
                Dice dice = new Dice(true, 1, i);
                hashDices.put(dice.getSurfaceIndex(), dice);
            }
        }
    }

    /**
     * Reset all dices to default value
     * */
    public void resetSelectedDices(){
        for(int i = 0; i < diceList.size(); i++){
            throwRunnables.get(i).getSurface().getRenderer().setDiceSelected(false);
            throwRunnables.get(i).getSurface().setSurfaceIsActive(true);
        }
    }

    /**
     * check if throw is still executing
     * */
    public boolean throwIsExcuting(){
        for(int i = 0; i < throwRunnables.size(); i++){
            if(throwRunnables.get(i).getRunning()){
                return true;
            }
        }
        return false;
    }
    /**
     * Start a roll animation
     * @param view the application view
     * */
    public void beginARollRound(View view){
        if(CommunicationHandler.getInstance().getCurrentPlayer().getNumberOfThrows() < 3) {
            if (!throwIsExcuting()) {
                CommunicationHandler.getInstance().setThrows();
                CommunicationHandler.getInstance().getThrows(view);
                dice_sound.play(sound_id, 0f, 1.0f, 0, 0, 1.0f);
                for (int i = 0; i < throwRunnables.size(); i++) {
                    if (i + 1 < throwRunnables.size()) {
                        //This is a hack, use one invisible open gl as surface thread
                        //and apply rotation on the other surface that's why zero
                        //is skipped.
                        if (throwRunnables.get(i + 1).getSurface().isSurfaceIsActive()) {
                            numberOfThreadsStarted = i;
                            diceList.get(0).queueEvent(throwRunnables.get(i + 1));
                            if (throwRunnables.get(i + 1).getRunning()) {
                                throwRunnables.get(i + 1).endThis();
                            } else {
                                throwRunnables.get(i + 1).start();
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Listener for the roll button
     * */
    private class ThrowListener implements View.OnClickListener{
        private View inGameView;

        public ThrowListener(View inGameView){
            this.inGameView = inGameView;
        }
        @Override
        public void onClick(View v){
          beginARollRound(inGameView);
        }
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
     * Communication with the throwthread thread to get scoresArray when the opengl
     * animation is running.
     * And send the sparsearray list to the  CommunicationHandler
     * */
    private Callback throwCallback = new Callback() {
        @Override
        public boolean handleMessage(Message message) {
            SparseArray<Dice> sparseArray = message.getData().getSparseParcelableArray("scoreArray");
            if(sparseArray != null) {
                for(int i = 0; i < sparseArray.size(); i++){
                    int key = sparseArray.keyAt(i);
                }
                CommunicationHandler.getInstance().onThrowPostPossibleScores(sparseArray);
                return true;
            }else{
                return false;
            }

        }
    };
    /**
     * Created by Rallmo on 2017-04-05.
     *
     * Class which executing and keep track of the
     * dice while throw animation is active.
     *
     * @author Rasmus Dahlkvist
     */
    public class  ThrowThread implements Runnable{
        private volatile boolean running = false;
        private Thread thread;
        private Dice dice;
        private SparseArray<Dice> dices;
        private DiceSurfaceView surface;
        boolean reversedX = false;
        boolean reversedY = false;
        boolean positiveAngleX = false;
        boolean positiveAngleY = false;
        boolean isRotationXDone = false;
        boolean isRotationYDone = false;
        boolean newAngleIsSetted = false;
        private Handler uiHandler;
        int[] currentAngle = null;
        int xAngle = 0;
        int yAngle = 0;

        /**
         * CONSTRUCTOR for the class ThrowThread
         * Class that starts and handle the animation
         * when the user press throw
         *
         * @param diceSurface which surface that the animation is executed
         *                    on
         * @param dice which dice the animation is executed with
         *
         * */
        public ThrowThread(DiceSurfaceView diceSurface,
                           Dice dice, SparseArray<Dice> dices, HashMap listOfScores,
                           Handler uiHandler){
            this.surface = diceSurface;
            this.dice = dice;
            this.dices = dices;
            this.uiHandler = uiHandler;
            currentAngle = surface.getAngleOfDice(
                    this.dice.getScore()
            );
            xAngle = currentAngle[0];
            yAngle = currentAngle[1];
            this.addDiceNumber(surface);
            this.checkSelected();
        }

        /**
         * Add a dice number to the glSurfaceContext to keep
         * track on which dice that is interacting with user
         * */
        public synchronized void addDiceNumber(DiceSurfaceView diceSurfaceView){
            diceSurfaceView.setCurrentDiceNumber(dice.getSurfaceIndex());
        }

        /**
         * Check any dice is checked if so it tells the glSurfaceView which
         * once that is checked
         * */
        public synchronized void checkSelected(){
            ArrayList<GameObjects.GlActive> gameObjects =
                    CommunicationHandler.getInstance().getGameObjects().getGlActive();
            if(gameObjects.size() != 0){
                for(int i = 0; i < gameObjects.size(); i+=1){
                    if(gameObjects.get(i).getIndex() == dice.getSurfaceIndex()){
                        if(!gameObjects.get(i).isActive()) {
                            surface.getRenderer().setDiceSelected(true);
                            surface.setSurfaceIsActive(false);
                        }
                    }
                }
            }
        }
        /**
         * Call rotation animation on the x-axis and
         * the y-axis. The throw animation is executed on
         * the opengl-thread and not the activity thread.
         * */
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
                    Thread.sleep(30);
                }catch (InterruptedException e){
                    System.out.println("interrupted e");
                }

            }
        }
        /**
         * Start a new thread
         * */
        public synchronized void start(){
            running = true;
            this.resetValues();
            if (thread == null) {
                thread = new Thread(this);
                thread.start();
            }
        }

        /**
         * Update the dicelist by setting a new
         * value inside the dice sparesArray
         * */
        public synchronized void updateDiceInList(){
            dices.setValueAt(dice.getSurfaceIndex(),dice);
        }

        /**
         * Set a new score for the dice object
         * */
        public synchronized void setGetScore(int diceNumber){
            int myVal = diceNumber+1;
            dice.setScore(myVal);
            this.updateDiceInList();
        }
        /**
         * Reset all values before starting
         * a new thread instance
         * */
        public synchronized void resetValues(){
            currentAngle = surface.getAngleOfDice(dice.getScore());
            xAngle = currentAngle[0];
            yAngle = currentAngle[1];
            reversedX = false;
            reversedY = false;
            positiveAngleX = false;
            positiveAngleY = false;
            isRotationXDone = false;
            isRotationYDone = false;
            newAngleIsSetted = false;
        }

        /**
         * Check if all thread is done if so the angle for
         * each dice score is setted
         * */
        public synchronized void checkIfAllThreadsIsDone(){
            int counter = 0;
            for(int i = 0; i < throwRunnables.size(); i++){
                if (!throwRunnables.get(i).getRunning()) {

                    this.setAngleAfterAnimation(
                            throwRunnables.get(i)
                    );
                    counter++;
                }
            }
            if(counter == 5){
                sendScores = true;
                this.setAllAngleToScore();
            }
        }

        /**
         * Tells to set angle after the animation is done for
         * each thread
         * */
        private void setAllAngleToScore(){
            for(int i = 0; i < throwRunnables.size(); i++){
                    this.setAngleAfterAnimation(
                            throwRunnables.get(i)
                    );
            }
        }
        /**
         * Set a new angle for the object based on the score
         * of the object
         * */
        private void setAngleAfterAnimation(ThrowThread object){
            int newAngle = object.getScoreDice().getScore();
            object.getSurface().changePositionOfDice(newAngle);
        }

        /**
         * Send the generated scores after the openGL animation is done
         * to the mainThread by using a bundle and callback function
         * */
        private synchronized void sendMessageToMainThread(){
            Message m = new Message();
            Bundle bundle = new Bundle();
            bundle.putSparseParcelableArray("scoreArray", dices); // for example
            m.setData(bundle);
            uiHandler.sendMessage(m);
        }
        /**
         * Shutdown the thread
         * */
       public void endThis() {
            numberOfThreadsDone++;
           this.checkIfAllThreadsIsDone();
           if(sendScores) {
               this.sendMessageToMainThread();
               sendScores = false;
           }
            if (thread != null) {
                running = false;
                thread = null;
            }
        }

        /**
         * Set new value for the xAngle on the x-axis
         *
         * @param value as new value for the xangle
         * */
        public synchronized void setXAngle(int value){
            xAngle = value;
        }

        /**
         * Set new value for the yAngle on the y-axis
         *
         * @param value as new value for the yangle
         * */
        public synchronized void setYAngle(int value){
            yAngle = value;
        }

        /**
         * Set reversed x true if the angle has rotated to 360 degree and
         * back to zero
         *
         * @param reversedX as boolean
         * */
        public synchronized void setReversedX(boolean reversedX) {
            this.reversedX = reversedX;
        }

        /**
         * Set reversed y true if the angle has rotated to 360 degree and
         * back to zero
         *
         * @param reversedY as boolean
         * */
        public synchronized void setReversedY(boolean reversedY) {
            this.reversedY = reversedY;
        }

        /**
         * Set the boolean if x-axis is a positive angle
         *
         * @param positiveAngleX as bool
         * */
        public synchronized void setPositiveAngleX(boolean positiveAngleX) {
            this.positiveAngleX = positiveAngleX;
        }

        /**
         * Set the boolean if y-axis is a positive angle
         *
         * @param positiveAngleY as bool
         * */
        public synchronized void setPositiveAngleY(boolean positiveAngleY) {
            this.positiveAngleY = positiveAngleY;
        }
        public synchronized void setCurrentAngle(int[] value){
            currentAngle = null;
            currentAngle = value;
        }

        public Dice getScoreDice(){
            return dice;
        }

        /**
         * Set true if rotations is done on x-axis
         *
         * @param rotationXDone as a boolean
         * */
        public synchronized void setRotationXDone(boolean rotationXDone) {
            isRotationXDone = rotationXDone;
        }

        /**
         * Set true if rotations is done on y-axis
         *
         * @param rotationYDone as boolean value
         * */
        public synchronized void setRotationYDone(boolean rotationYDone) {
            isRotationYDone = rotationYDone;
        }
        /**
         * Set true if new angle is setted
         *
         * @param newAngleIsSetted as an boolean
         * */
        public synchronized void setNewAngleIsSetted(boolean newAngleIsSetted) {
            this.newAngleIsSetted = newAngleIsSetted;
        }
        public DiceSurfaceView getSurface() {
            return surface;
        }
        /**
         *
         * @return newAngleIsSetted as bool
         * */
        public boolean getNewAngleIsSetted() {
            return newAngleIsSetted;
        }

        public Boolean getRunning(){
            return running;
        }
    }


}
