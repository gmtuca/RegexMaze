package com.gmtuca.regexmaze.app;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gmtuk on 25/07/2014.
 */
public class MazePattern {
    private String stringPattern;
    private Pattern pattern;

    public MazePattern(String stringPattern) {
        this.stringPattern = stringPattern;

        pattern = Pattern.compile(stringPattern);
    }

    public boolean matches(ArrayList<Square> squares){
        String inputString = "";

        for(Square s : squares)
            inputString += s.getChar();

        Matcher m = pattern.matcher(inputString);

        return m.matches();
    }

    public int lastMatchedSquareIndex(ArrayList<Square> squares){

        for(int i = squares.size(); i > 0; i--)
            if(matches(squares, i))
                return i;

        return -1; //no unmatched squares
    }

    //match up to index i
    public boolean matches(ArrayList<Square> squares, int index){
        ArrayList<Square> lessSquares = new ArrayList<Square>(squares);

        for(int i = index; i < squares.size(); i++)
            lessSquares.remove(squares.get(i));

        return matches(lessSquares);
    }

    @Override

    public String toString(){
        return stringPattern;
    }

}