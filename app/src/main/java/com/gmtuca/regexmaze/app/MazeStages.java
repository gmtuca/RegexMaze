package com.gmtuca.regexmaze.app;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by gmtuk on 25/07/2014.
 */
public class MazeStages {

    private ArrayList<Maze> mazes;
    private int currentMazeIndex;

    public MazeStages(float width, float height){
        mazes = new ArrayList<Maze>();

        Maze.setBoardWidthHeight(width,height);

        mazes.add(new Maze("\\d+", "19CD" +
                                   "E37H" +
                                   "IJ3L" +
                                   "MN68", 4, 4));

        mazes.add(new Maze("([A-Z]\\d)+[a-z]*", "K59C" +
                                                "4D3P" +
                                                "3gH8" +
                                                "xbks", 4, 4));

        mazes.add(new Maze("S2+[^dog](or)*cat?", "S22Do" +
                                                 "2dCar" +
                                                 "^r?ro" +
                                                 "doroa" +
                                                 "ogcat", 5, 5));

        mazes.add(new Maze("\\D{3}[^\\.\\?aAbB]+[13579]?[xyz]*z[XYZ\\W]+", "wDd!59" +
                                                                           "y7?1yx" +
                                                                           ".@ZYxZ" +
                                                                           "Ay-Z?W" +
                                                                           "zy=yZ+" +
                                                                           "Z.X!PX", 6, 6));

        this.currentMazeIndex = 0;
    }

    public Maze currentMaze(){
        return mazes.get(currentMazeIndex);
    }

    public void drawCurrent(Canvas ca){
       currentMaze().draw(ca);
    }

    public void pressCurrent(float x, float y){
        Maze current = currentMaze();

        current.press(x, y);

        if(current.isFinished()) {
            currentMazeIndex++;
        }
    }


}
