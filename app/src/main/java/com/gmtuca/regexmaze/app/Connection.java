package com.gmtuca.regexmaze.app;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gmtuk on 26/07/2014.
 */
public class Connection {
    private final Square from, to;
    private static final int radius = 4;
    private static Paint paint; // or could I have a paint for all arrows???

    static{
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setARGB(255, 0, 0, 0);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
    }

    public Connection(Square from, Square to) {
        this.from = from;
        this.to = to;

//        if(from.equals(to))
//            throw new Exception("Connecting square and itself");
    }

    public void draw(Canvas ca){
        ca.drawCircle((from.centerX()+to.centerX())/2, (from.centerY()+to.centerY())/2, radius, paint);

    }

}
