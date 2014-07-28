package com.gmtuca.regexmaze.app;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by gmtuk on 20/07/2014.
 */
public class Maze {

    ArrayList<ArrayList<Square>> squares;
    private final int cols, rows;

    private final String characters;

    private MazePattern mazePattern;

    private ArrayList<Square> squareAnswers; //in order of press
    private ArrayList<Connection> connections;

    public static float boardWidth = 0, boardHeight = 0;

    public static void setBoardWidthHeight(float w, float h){
        boardWidth = w;
        boardHeight = h;
    }

    float size; // TODO CHANGE!!!!

    //TODO CHECK IF 4X4 IS ACTUALLY THE NUMBER OF CHARS! EXCEPTION OTHERWISE

    private static Paint pText;

    static{
        pText = new Paint(Paint.ANTI_ALIAS_FLAG);
        pText.setTextSize(50);
        pText.setTextAlign(Paint.Align.CENTER);
    }

    private float paddingSides, paddingTop;

    public Maze(String stringPattern, String characters, int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        this.characters = characters;

        float sizePercentage = 7/8f;

        this.paddingSides = ((1 - sizePercentage)/2) * boardWidth;
        this.paddingTop = 200;

        this.size = sizePercentage * boardWidth / rows; //TODO CHANGE THIS

        initializeSquares();

        mazePattern = new MazePattern(stringPattern);

        squareAnswers = new ArrayList<Square>();
        connections = new ArrayList<Connection>();

        addAnswer(get(0, 0));//start with first square
    }

    public void initializeSquares(){
        this.squares = new ArrayList<ArrayList<Square>>();
        for(int row = 0; row < rows; row++)
            squares.add(new ArrayList<Square>());

        int charI = 0;
        for(int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                squares.get(row).add(new Square(characters.charAt(charI++), paddingSides + size * col, paddingTop + size * row, size, col, row));
    }

    public Square lastAddedSquareAnswer(){
        if(squareAnswers.size() == 0)
            return null;

        return squareAnswers.get(squareAnswers.size()-1);
    }

    public void addAnswer(Square s){
        Square oldLastSquare = lastAddedSquareAnswer();

        squareAnswers.add(s);

        if(oldLastSquare != null)
            connections.add(new Connection(oldLastSquare, s));

        if(mazePattern.matches(squareAnswers)){
            for(Square sq : squareAnswers)
                sq.setCurrentAnswer(Square.Answer.RIGHT);

            if(s == finishLine())
                finished = true;
        }
        else
            s.setCurrentAnswer(Square.Answer.MAYBE);
    }

    private boolean finished = false;

    public boolean isFinished(){
        return finished;
    }

    public void press(float x, float y){

        if(!mazeIsBeingPressedOn(x,y))
            return;

        Square sq = squareBeingPressed(x,y);

        if(sq == lastAddedSquareAnswer()) {
            resetSquare(sq);
            return;
        }

        if(sq.isFree() && sq.isNeighbourOf(lastAddedSquareAnswer()))
            addAnswer(squareBeingPressed(x, y));

    }

    public Square finishLine(){
        return get(rows-1, cols-1);
    }

    public void resetSquare(Square s){
        if(s.isFirst())
            return;

        squareAnswers.remove(s);
        connections.remove(connections.size()-1);

        s.setCurrentAnswer(Square.Answer.NONE);

        //might need to do this somewhere else as well.... TODO
        int unmatched = mazePattern.lastMatchedSquareIndex(squareAnswers);

        for(int i = 0; i < squareAnswers.size(); i++)
            if(i < unmatched)
                squareAnswers.get(i).setCurrentAnswer(Square.Answer.RIGHT); // HOW ABOUT -1 TODO
            else
                squareAnswers.get(i).setCurrentAnswer(Square.Answer.MAYBE);
    }

    public Square squareBeingPressed(float x, float y){
        int sx = (int) ((x - paddingSides) / size);
        int sy = (int) ((y - paddingTop) / size);

        System.out.println("(" + sx + ", " + sy + ")");

        return get(sx,sy);
    }

    public boolean mazeIsBeingPressedOn(float x, float y){
        return x > paddingSides && x < boardWidth - paddingSides &&
                y > paddingTop && y < paddingTop + size * rows;
    }

    public Square get(int x, int y){
        return squares.get(y).get(x);
    }

    public void draw(Canvas ca){

        ca.drawText(mazePattern.toString(), boardWidth /2, 120, pText);

        for(int row = 0; row < rows; row++)
            for(int col = 0; col < cols; col++) {
                get(row, col).draw(ca);
            }

        for(Connection a : connections)
            a.draw(ca);

    }
}
