package com.example.pandamove.yatzy;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.jar.Attributes;
//import es2.learning.ViewPortRenderer;

public class Cubesurfaceview extends GLSurfaceView {

	float touchedX = 0;
	float touchedY = 0;
	Cuberenderer renderer;
	public Cubesurfaceview(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextClientVersion(2);
        setRenderer(renderer = new Cuberenderer(this));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			touchedX = event.getX();
			touchedY = event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			renderer.xAngle += (touchedX - event.getX())/2f;
			renderer.yAngle += (touchedY - event.getY())/2f;
			
			touchedX = event.getX();
			touchedY = event.getY();
		}
		return true;
		
	}
}