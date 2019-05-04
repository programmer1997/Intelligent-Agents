package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class State {
    // Directions in which the robot can travel
    public static  ArrayList<String> directions=new ArrayList<>();
    public static final  String North="N";
    public static  final String South="S";
    public static final  String West="W";
    public static  final String East="E";
    public int getX_position() {
        return x_position;
    }

    public int getY_position() {
        return y_position;
    }
    // position of the state in the grid
    private int x_position;
    private int y_position;
    private Random randomizer=new Random();


    // reward and utility of the state in the grid
    private double reward;
    private double utility;


    // whether state is a wall in the grid
    private boolean isWall;
    // policy of the state
    private String action;
    // the directions which the robot can move from this state
    private boolean isMoveNorth=false;
    private boolean isMoveSouth=false;
    private boolean isMoveEast=false;
    private boolean isMoveWest=false;


    // Constructor
    public State(int x_position,int y_position,double reward,double utility,boolean isWall){
        this.x_position=x_position;
        this.y_position=y_position;
        this.reward=reward;
        this.utility=utility;
        this.isWall=isWall;
        directions.add(State.North);
        directions.add(State.South);
        directions.add(State.East);
        directions.add(State.West);
        if( this.isWall==true) this.action="";
        else this.action=State.West; //this.action=directions.get(randomizer.nextInt(4));
    }


    public double getUtility() {
        return utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isMoveNorth() {
        return isMoveNorth;
    }

    public void setMoveNorth(boolean moveNorth) {
        isMoveNorth = moveNorth;
    }

    public boolean isMoveSouth() {
        return isMoveSouth;
    }

    public void setMoveSouth(boolean moveSouth) {
        isMoveSouth = moveSouth;
    }

    public boolean isMoveEast() {
        return isMoveEast;
    }

    public void setMoveEast(boolean moveEast) {
        isMoveEast = moveEast;
    }

    public boolean isMoveWest() {
        return isMoveWest;
    }

    public void setMoveWest(boolean moveWest) {
        isMoveWest = moveWest;
    }

    public boolean isWall() {
        return isWall;
    }
    public double getReward() {
        return reward;
    }





}
