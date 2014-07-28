package com.gmtuca.regexmaze.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by gmtuk on 06/06/2014.
 */
public class MazeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MazeStages mazeStages;

    public MazeSurfaceView(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();

        if(holder != null)
            holder.addCallback(this);
    }

    @Override
    public  void onDraw(Canvas c) {
        clearCanvas(c);
        mazeStages.drawCurrent(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {  }

    public static void clearCanvas(Canvas c){
        if(c == null)
            return;

        c.drawColor(Color.WHITE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        mazeStages = new MazeStages(getWidth(), getHeight());

        Canvas ca = null;

        if (surfaceHolder == null)
            return;

        try {
            ca = surfaceHolder.lockCanvas(null);

            if (ca != null) {
                this.onDraw(ca);
            }
        } finally {
            if (ca != null)
                surfaceHolder.unlockCanvasAndPost(ca);
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //System.out.println("Down");

                        Canvas ca = null;
                        SurfaceHolder holder = getHolder();
                        if (holder != null)
                            try {
                                ca = holder.lockCanvas(null);

                                if (ca != null) {
                                    mazeStages.pressCurrent(x, y);
                                    onDraw(ca); //redraw everything

                                }
                            } finally {
                                if (ca != null)
                                    holder.unlockCanvasAndPost(ca);
                            }
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        //System.out.println("Released");
                        break;
                    default:
                        //System.out.println("Something else");
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}
}




