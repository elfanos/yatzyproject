package com.example.pandamove.yatzy.OpenGLClasses;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.pandamove.yatzy.fragments.InGameFragment;

import java.util.Random;


/**
 * Created by Rallmo on 2017-04-05.
 *
 * DiceSurfaceView class that handles all the change
 * on the GLSurfaceView of one dice.
 *
 * @author Rasmus Dahlkvist
 */
public class DiceSurfaceView extends GLSurfaceView {

	float touchedX = 0;
	float touchedY = 0;
	float lastPositionX = 0;
	float lastPositionY = 0;
	private boolean surfaceIsActive;
	private int currentDiceNumber;

	Cuberenderer renderer;

	/**
	 * Constructor
	 * Initialize a new GLSurfaceView and create a
	 * new cube using openGL.
	 *
	 * @param context the current context which the GLSurfaceView
	 *                is present in.
	 * @param attrs which attrs in the context has.
	 * */
	public DiceSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextClientVersion(2);
		this.setSurfaceIsActive(true);
		this.setCurrentDiceNumber(0);
		renderer = new Cuberenderer(this);
		setRenderer(renderer);
	}

	/**
	 * Onthouch event handle a rotation on dice while the user
	 * is touching the surface area.
	 *
	 * @param event keept track of the user finger movement on the
	 *              dice surface
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			touchedX = event.getX();
			touchedY = event.getY();
			System.out.println("yalla");
			if(!renderer.isDiceSelected()) {
				renderer.setDiceSelected(true);
				this.setSurfaceIsActive(false);
			}else{
				renderer.setDiceSelected(false);
				this.setSurfaceIsActive(true);
			}
		}
		return true;
		
	}
	public Cuberenderer getRenderer() {
		return renderer;
	}
	/**
	 * Rotate the dice until it reach 360 degree
	 *
	 * @param throwThread the dice state and thread
	 * @param angleValue the current angleValue
	 * @param reversed boolean to check if the angle reached 360 degree
	 * @param axis which axis the dice is being rotated on.
	 * */
	public void rotationNotReversed(InGameFragment.ThrowThread throwThread,
									int angleValue, boolean reversed, String axis){
		if(angleValue < 0 && !reversed){
			if(angleValue > -360) {
				angleValue = angleValue - 10;
				this.setRenderAngle(axis, angleValue);
				this.setAngleValue(axis,angleValue,throwThread);
			}else{
				this.setAngleReversed(axis, true, throwThread);
				this.setAnglePositive(axis, false, throwThread);
				this.setIfRotationIsDone(axis, true, throwThread);
				this.setAngleValue(axis,0,throwThread);
			}
		}
		else if(angleValue > 0 && !reversed){
			if(angleValue < 360) {
			//	System.out.println("inla loop positive " + angleValue);
				//angleValue++;
				angleValue = angleValue + 10;
				this.setRenderAngle(axis, angleValue);
				this.setAngleValue(axis,angleValue,throwThread);
			}else{
				this.setAngleReversed(axis, true, throwThread);
				this.setAnglePositive(axis, true, throwThread);
				this.setIfRotationIsDone(axis, true, throwThread);
				this.setAngleValue(axis,0,throwThread);
			}
		}else if(angleValue == 0){
			angleValue = angleValue - 10;
			this.setAngleValue(axis,angleValue,throwThread);
			this.rotationNotReversed(
					throwThread, angleValue,
					reversed, axis
			);
		}

	}
	/**
	 * Check which state on the rotation the dice currently is on
	 *
	 * @param throwThread the state handler class
	 * @param currentAngle the currentAngle of the dice both x-axis and y-axis
	 * @param angleValue of the x-axis or y-axis
	 * @param reversed boolean which declare if the angle have reached -360 or 360
	 * @param positiveAngle boolean which check if it is a positiveangle or not
	 * @param axis state which angle the dice is rotating on (y-axis or x-axis)
	 * @param rotationsIsDone boolean which keep track on if the rotations is done.
	 * */
	public void rotationOnDice(InGameFragment.ThrowThread throwThread,
							   int currentAngle[], int angleValue,
							   boolean reversed, boolean positiveAngle,
							   String axis, boolean rotationsIsDone){
		if(!reversed){
			this.rotationNotReversed(
					throwThread, angleValue,
					reversed, axis
			);
		}else if(!positiveAngle){
			if(angleValue == 0  || angleValue < 0) {
				this.rotateAtNegativeAngle(throwThread,currentAngle,
						angleValue,axis,rotationsIsDone);
			}
		}
		else if(angleValue > 0 || angleValue == 0){
			this.rotateAtPositiveAngle(
					throwThread, currentAngle, angleValue, axis, rotationsIsDone
			);
		}
	}

	/**
	 * Rotate the dice towards a negative angle
	 * until it reaches -360 then it the angle is setted
	 * to zero.
	 *
	 * @param throwThread the dice state and thread
	 * @param currentAngle of the dice
	 * @param angleValue the current angleValue
	 * @param axis which axis the dice is being rotated on.
	 * @param rotationsIsDone boolean which keep track on if the rotations is done.
	 * */
	public void rotateAtNegativeAngle(InGameFragment.ThrowThread throwThread,
									  int currentAngle[], int angleValue,
									  String axis, boolean rotationsIsDone){
		if (rotationsIsDone) {
			if(throwThread.getNewAngleIsSetted()) {
				if (angleValue == getCurrentAngle(axis, currentAngle)) {
					this.setRenderAngle(axis, angleValue);
					throwThread.endThis();
				} else {
					angleValue = angleValue - 10;
					this.setRenderAngle(axis, angleValue);
					this.setAngleValue(axis, angleValue, throwThread);
				}
			} else{
				this.setNewDiceAngleForXnY(throwThread);
			}
		}
	}

	/**
	 * Rotate the dice towards a positive angle
	 * until it reaches 360 then it the angle is setted
	 * to zero.
	 *
	 * @param throwThread the dice state and thread
	 * @param currentAngle of the dice
	 * @param angleValue the current angleValue
	 * @param axis which axis the dice is being rotated on.
	 * @param rotationsIsDone boolean which keep track on if the rotations is done.
	 * */
	public void rotateAtPositiveAngle(InGameFragment.ThrowThread throwThread,
									  int currentAngle[], int angleValue,
									  String axis, boolean rotationsIsDone){
		if(rotationsIsDone) {
			if(throwThread.getNewAngleIsSetted()) {
				if (angleValue == getCurrentAngle(axis, currentAngle)) {
					this.setRenderAngle(axis, angleValue);
					throwThread.endThis();
				} else {
					angleValue = angleValue + 10;
					this.setRenderAngle(axis, angleValue);
					this.setAngleValue(axis, angleValue, throwThread);
				}
			}else{
				this.setNewDiceAngleForXnY(throwThread);
			}
		}
	}
	/**
	 * Set a new dice angle depending on which random
	 * number generate between 0-5
	 *
	 * @param throwThread state handler class of the dice
	 * */
	public void setNewDiceAngleForXnY(InGameFragment.ThrowThread throwThread){
		Random rand = new Random();
		int diceNumber = rand.nextInt(5+1);
		throwThread.setCurrentAngle(this.getAngleOfDice(diceNumber));
		throwThread.setGetScore(diceNumber);
		throwThread.resetValues();
		throwThread.setNewAngleIsSetted(true);
	}
	/**
	 * Set if the rotations is done or not
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param value true of false if rotations is done
	 * @param throwThread state handler class of the dice
	 * */
	public void setIfRotationIsDone(String angleAxis, boolean value,
									InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setRotationXDone(value);
		}else if (angleAxis.equals("y")){
			throwThread.setRotationYDone(value);
		}
	}

	/**
	 * Set if the angles is reversed or not
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param value true of false if angles is reversed or not
	 * @param throwThread state handler class of the dice
	 * */
	public void setAngleReversed(String angleAxis, boolean value,
								 InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setReversedX(value);
		}else if (angleAxis.equals("y")){
			throwThread.setReversedY(value);
		}
	}

	/**
	 * Set a new angle value on right axis
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param currentAngle currentAngle of the dice
	 * */
	public int getCurrentAngle(String angleAxis, int[] currentAngle){
		if(angleAxis.equals("x")){
			return currentAngle[0];
		}else if (angleAxis.equals("y")){
			return currentAngle[1];
		}
		return -700;
	}

	/**
	 * Set if the angles is positive or not
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param value true of false if angles is positive or not
	 * @param throwThread state handler class of the dice
	 * */
	public void setAnglePositive(String angleAxis, boolean value,
								 InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setPositiveAngleX(value);
		}else if (angleAxis.equals("y")){
			throwThread.setPositiveAngleY(value);
		}
	}

	/**
	 * Set a new angle value for the x or y axis
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param value value of the angle
	 * @param throwThread state handler class of the dice
	 * */
	public void setAngleValue(String angleAxis, int value,
							  InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setXAngle(value);
		}else if (angleAxis.equals("y")){
			throwThread.setYAngle(value);
		}
	}

	/**
	 * Set a new angle value for cube
	 *
	 * @param angleAxis which axis the condition should apply on
	 * @param value value of the angle
	 * */
	public void setRenderAngle(String angleAxis, int value){
		if(angleAxis.equals("x")){
			renderer.xAngle = value;
		}else if (angleAxis.equals("y")){
			renderer.yAngle = value;
		}
	}

	/**
	 * Create a integer array which contains
	 * two value 0 = xAngle and yAngle = 1, which is
	 * the default angle for a specific diceNumber
	 *
	 * @param diceNumber which dice
	 *
	 * @return angelInteger which is right angle of the dice based on
	 * 			diceNumber
	 * */
	public int[] getAngleOfDice(int diceNumber){
		int angelInteger[] = new int[2];
		switch (diceNumber){
			case 1:
				angelInteger[0] = -180;
				angelInteger[1] = 0;
				return angelInteger;
			case 2:
				angelInteger[0] = 90;
				angelInteger[1] = 90;
				return angelInteger;
			case 3:
				angelInteger[0] = 0;
				angelInteger[1] = 0;
				return angelInteger;
			case 4:
				angelInteger[0] = -90;
				angelInteger[1] = 0;
				return angelInteger;
			case 5:
				angelInteger[0] = 0;
				angelInteger[1] = -270;
				return angelInteger;
			case 6:
				angelInteger[0] = 0;
				angelInteger[1] = -90;
				return angelInteger;
		}
		return null;
	}
	/**
	 * @param diceNumber sett dice depending on which diceNumber
	 * */
	public void changePositionOfDice(int diceNumber){
		switch (diceNumber){
			case 1:
				/*One*/
				renderer.xAngle = -180;
				renderer.yAngle = 0;
				break;
			case 2:
				/*two*/
				renderer.xAngle = 90;
				renderer.yAngle = 90;
				break;
			case 3:
				/*Three*/
				renderer.xAngle = 0;
				renderer.yAngle = 0;
				break;
			case 4:
				/*four*/
				renderer.xAngle = -90;
				renderer.yAngle = 0;
				break;
			case 5:
				/*Five*/
				renderer.xAngle = 0;
				renderer.yAngle = -270;
				break;
			case 6:
				/*Six*/
				renderer.xAngle = 0;
				renderer.yAngle = -90;
				break;
		}
	}

	/**
	 * @return if the surface is active or not
	 * */
	public boolean isSurfaceIsActive() {
		return surfaceIsActive;
	}

	/**
	 * Set a value of the surface
	 *
	 * @param surfaceIsActive boolean true or not, active or not
	 * */
	public void setSurfaceIsActive(boolean surfaceIsActive) {
		this.surfaceIsActive = surfaceIsActive;
	}

	/**
	 * Set the current diceNumber on the surface which dice
	 * the surface will controll.
	 *
	 * @param currentDiceNumber which dicesNumber
	 * */
	public void setCurrentDiceNumber(int currentDiceNumber) {
		this.currentDiceNumber = currentDiceNumber;
	}

	/**
	 * Set messure for the dicesurfaceview instead of
	 * using default values
	 *
	 * @param widthMeasureSpec the width of the surface
	 * @param heightMeasureSpec the height of the surface
	 * */
	@Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(size, size);
	}

}