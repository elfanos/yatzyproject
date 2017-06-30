package com.example.pandamove.yatzy;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
	public void setRotationOnShake(float speed){
		renderer.xAngle += (lastPositionX - (speed / 2f)) / 4f;
		renderer.yAngle += (lastPositionY - (speed)) / 4f;

		lastPositionX = speed/2f;
		lastPositionY = speed;

	}
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
	@Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(size, size);
	}
}