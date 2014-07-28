package com.gmtuca.regexmaze.app;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by gmtuk on 20/07/2014.
 */
public class Square {

    private final char c;
    private final float x, y;     //pixel position within the screen
    private final int posX, posY; //position within Maze
    private final float size;
    private static final Paint pRed, pGreen, pYellow, pWhite, pFrame, pText;
    private Answer currentAnswer = Answer.NONE;

    public static enum Answer { NONE, RIGHT, WRONG, MAYBE };

    static{
        // GREEN - RIGHT
        pGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        pGreen.setStyle(Paint.Style.FILL);
        pGreen.setARGB(255, 0, 255, 0); //green

        // YELLOW - MAYBE
        pYellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        pYellow.setStyle(Paint.Style.FILL);
        pYellow.setARGB(255, 255, 255, 0); //yellow

        //RED - WRONG
        pRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        pRed.setStyle(Paint.Style.FILL);
        pRed.setARGB(255, 255, 0, 0); //red

        //WHITE - NONE
        pWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        pWhite.setStyle(Paint.Style.FILL);
        pWhite.setARGB(255, 255, 255, 255); //red

        //TEXT
        pText = new Paint(Paint.ANTI_ALIAS_FLAG);
        pText.setTextSize(35);
        pText.setTextAlign(Paint.Align.CENTER);

        //FRAME
        pFrame =  new Paint(Paint.ANTI_ALIAS_FLAG);
        pFrame.setARGB(255, 0, 0, 0); //black
        pFrame.setStyle(Paint.Style.STROKE);
    }

    public Square(char c, float x, float y, float size, int posX, int posY) {
        this.c = c;

        this.x = x;
        this.y = y;

        this.size = size;

        this.posX = posX;
        this.posY = posY;
    }

    public boolean isFirst(){
        return posX == 0 && posY ==0;
    }

    public char getChar(){
        return c;
    }
    public float centerX(){
        return x + 1/2f * size;
    }
    public float centerY(){
        return y + 1/2f * size;
    }

    public boolean isFree(){
        return currentAnswer == Answer.NONE;
    }

    public boolean isNeighbourOf(Square sq){
           return (posX == sq.posX && Math.abs(posY - sq.posY) == 1) ||
                  (posY == sq.posY && Math.abs(posX - sq.posX) == 1);
    }

    public void setCurrentAnswer(Answer currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public void draw(Canvas ca){
        //INSIDE
        Paint p;
        switch(currentAnswer){
            case RIGHT:  p = pGreen;  break; //green
            case WRONG:  p = pRed;    break; //red
            case MAYBE:  p = pYellow; break; //yellow
            default:     p = pWhite;  break; //white
        }
        ca.drawRect(x, y, x+ size, y+ size, p);

        //FRAME
        ca.drawRect(x, y, x+ size, y+ size, pFrame);

        //TEXT
        ca.drawText("" + c, centerX(), centerY(), pText);
    }

    @Override
    public String toString(){
        return "[" + c + "] - (" + posX + ", " + posY + ")";
    }

}
