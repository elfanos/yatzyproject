package com.example.pandamove.yatzy;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.pandamove.yatzy.fragments.InGameFragment;
//import es2.learning.ViewPortRenderer;

public class DiceSurfaceView extends GLSurfaceView {

	float touchedX = 0;
	float touchedY = 0;
	float lastPositionX = 0;
	float lastPositionY = 0;
	private boolean surfaceIsActive;
	private int currentDiceNumber;
	Cuberenderer renderer;
	public DiceSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextClientVersion(2);
		this.setSurfaceIsActive(true);
		this.setCurrentDiceNumber(0);
		renderer = new Cuberenderer(this);
		setRenderer(renderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{

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


		} else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			renderer.xAngle += (touchedX - event.getX())/2f;
			renderer.yAngle += (touchedY - event.getY())/2f;
			/*System.out.println("touchedX: "+ touchedX);
			System.out.println("eventX: "+ event.getX());
			System.out.println("touchedY: "+ touchedY);
			System.out.println("event.getY(): "+ event.getY());*/
			/*System.out.println("le angle ratio x: " + (touchedX - event.getX())/2f);
			System.out.println("le angle ratio y: " + (touchedY - event.getY())/2f);*/
			touchedX = event.getX();
			touchedY = event.getY();
		}
		return true;
		
	}
	public void rotationNotReversed(InGameFragment.ThrowThread throwThread,
									int currentAngle[], int angleValue,
									boolean reversed, boolean positiveAngle,
									String axis, boolean rotationsIsDone){
		if(angleValue < 0 && !reversed){
			if(angleValue > -360) {
				System.out.println("le loop: " + angleValue);
				//angleValue--;
				angleValue = angleValue - 2;
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
				System.out.println("inla loop positive " + angleValue);
				//angleValue++;
				angleValue = angleValue + 2;
				this.setRenderAngle(axis, angleValue);
				this.setAngleValue(axis,angleValue,throwThread);
			}else{
				this.setAngleReversed(axis, true, throwThread);
				this.setAnglePositive(axis, true, throwThread);
				this.setIfRotationIsDone(axis, true, throwThread);
				this.setAngleValue(axis,0,throwThread);
			}
		}else if(angleValue == 0){
			System.out.println("smthing herE?");
			//angleValue--;
			angleValue = angleValue - 2;
			this.setAngleValue(axis,angleValue,throwThread);
			this.rotationNotReversed(
					throwThread, currentAngle, angleValue,
					reversed, positiveAngle, axis, rotationsIsDone
			);
		}

	}
	public void rotationOnDice(InGameFragment.ThrowThread throwThread,
							   int currentAngle[], int angleValue,
							   boolean reversed, boolean positiveAngle,
							   String axis, boolean rotationsIsDone){
		if(!reversed){
			this.rotationNotReversed(
					throwThread, currentAngle, angleValue,
					reversed, positiveAngle, axis, rotationsIsDone
			);
		}else if(!positiveAngle){
			if(angleValue == 0  || angleValue < 0) {
				if (rotationsIsDone) {
					System.out.println("inlast: " + angleValue);
					//System.out.println("inewx: " + currentAngle[0]);
					if (angleValue == getCurrentAngle(axis, currentAngle)) {
						//System.out.println("current anga??: " + xAngle);
						this.setRenderAngle(axis, angleValue);
						throwThread.endThis();
					} else {
						//angleValue--;
						angleValue = angleValue - 2;
						this.setRenderAngle(axis, angleValue);
						this.setAngleValue(axis, angleValue, throwThread);
					}
				} else {
					System.out.println("jaman should be her2222e");

				}
			}
		}
		else if(angleValue > 0 || angleValue == 0){
			if(rotationsIsDone) {
				System.out.println("inlast: " + angleValue);
				if (angleValue == getCurrentAngle(axis, currentAngle)) {
                    this.setRenderAngle(axis, angleValue);
					throwThread.endThis();
				}else{
                    //angleValue++;
                    angleValue = angleValue + 2;
                    this.setRenderAngle(axis, angleValue);
                    this.setAngleValue(axis, angleValue, throwThread);
                }
			}else{
				System.out.println("jaman should be he66666re");
				//throwThread.endThis();
			}
		}
	}
	public void setIfRotationIsDone(String angleAxis, boolean value,
									InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setRotationXDone(value);
		}else if (angleAxis.equals("y")){
			throwThread.setRotationYDone(value);
		}
	}
	public boolean getIfRotationIsDone(String angleAxis, InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			return throwThread.isRotationXDone();
		}else if (angleAxis.equals("y")){
			return throwThread.isRotationYDone();
		}
		return false;
	}
	public void setAngleReversed(String angleAxis, boolean value,
								 InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setReversedX(value);
		}else if (angleAxis.equals("y")){
			throwThread.setReversedY(value);
		}
	}
	public int getCurrentAngle(String angleAxis, int[] currentAngle){
		if(angleAxis.equals("x")){
			return currentAngle[0];
		}else if (angleAxis.equals("y")){
			return currentAngle[1];
		}
		return -700;
	}
	public void setAnglePositive(String angleAxis, boolean value,
								 InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setPositiveAngleX(value);
		}else if (angleAxis.equals("y")){
			throwThread.setPositiveAngleY(value);
		}
	}
	public void setAngleValue(String angleAxis, int value,
							  InGameFragment.ThrowThread throwThread){
		if(angleAxis.equals("x")){
			throwThread.setXAngle(value);
		}else if (angleAxis.equals("y")){
			throwThread.setYAngle(value);
		}
	}
	public void setRenderAngle(String angleAxis, int value){
		if(angleAxis.equals("x")){
			renderer.xAngle = value;
		}else if (angleAxis.equals("y")){
			renderer.yAngle = value;
		}
	}
	public void setRotationOnShake(float speed){
		renderer.xAngle += (lastPositionX - (speed / 2f)) / 4f;
		renderer.yAngle += (lastPositionY - (speed)) / 4f;

		lastPositionX = speed/2f;
		lastPositionY = speed;

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
			case 0:
				angelInteger[0] = -180;
				angelInteger[1] = 0;
				return angelInteger;
			case 1:
				angelInteger[0] = 90;
				angelInteger[1] = 90;
				return angelInteger;
			case 2:
				angelInteger[0] = 0;
				angelInteger[1] = 0;
				return angelInteger;
			case 3:
				angelInteger[0] = -90;
				angelInteger[1] = 0;
				return angelInteger;
			case 4:
				angelInteger[0] = 0;
				angelInteger[1] = -270;
				return angelInteger;
			case 5:
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
			case 0:
				/*One*/
				renderer.xAngle = -180;
				renderer.yAngle = 0;
				this.setCurrentDiceNumber(1);
				break;
			case 1:
				/*two*/
				renderer.xAngle = 90;
				renderer.yAngle = 90;
				this.setCurrentDiceNumber(2);

				break;
			case 2:
				/*Three*/
				renderer.xAngle = 0;
				renderer.yAngle = 0;
				this.setCurrentDiceNumber(3);
				break;
			case 3:
				/*four*/
				renderer.xAngle = -90;
				renderer.yAngle = 0;
				this.setCurrentDiceNumber(4);

				break;
			case 4:
				/*Five*/
				renderer.xAngle = 0;
				renderer.yAngle = -270;
				this.setCurrentDiceNumber(5);
				break;
			case 5:
				/*Six*/
				renderer.xAngle = 0;
				renderer.yAngle = -90;
				this.setCurrentDiceNumber(6);
				break;
		}
	}
	public boolean isSurfaceIsActive() {
		return surfaceIsActive;
	}

	public void setSurfaceIsActive(boolean surfaceIsActive) {
		this.surfaceIsActive = surfaceIsActive;
	}

	public int getCurrentDiceNumber() {
		return currentDiceNumber;
	}

	public void setCurrentDiceNumber(int currentDiceNumber) {
		this.currentDiceNumber = currentDiceNumber;
	}
	/*@Override
	public void queueEvent (Runnable r){

	}*/
	@Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(size, size);
	}

}