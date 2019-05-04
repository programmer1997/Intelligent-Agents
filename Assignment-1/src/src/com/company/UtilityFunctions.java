package com.company;

public class UtilityFunctions {
    private static double DISCOUNT_FACTOR=0.99;
    // probability that the agent will go in that direction
    private static double INTENDED_PROBABILITY=0.8;
    // probability that the agent will go to either side of intended direction
    private static double OTHER_PROBABILITY=0.1;

    public static double computeOptimalUtilityAndAction(Grid grid,int i, int j,String algorithm){
        State state=grid.gridWorld[i][j];
        if (state.isWall()) return 0 ;
        double utility=state.getReward();
        double max=0;
        String action="";
        double sumN,sumS,sumW,sumE;
        double north_utility,south_utility,west_utility,east_utility;
        // If it is possible for the agent to move in that direction,get the utilities of neighbouring states
        if (state.isMoveNorth()){
            north_utility=grid.gridWorld[i-1][j].getUtility();
        }
        else north_utility=grid.gridWorld[i][j].getUtility();
        if (state.isMoveSouth()){
            south_utility=grid.gridWorld[i+1][j].getUtility();
        }
        else south_utility=grid.gridWorld[i][j].getUtility();
        if (state.isMoveWest()){
            west_utility=grid.gridWorld[i][j-1].getUtility();
        }
        else west_utility=grid.gridWorld[i][j].getUtility();
        if (state.isMoveEast()){
            east_utility=grid.gridWorld[i][j+1].getUtility();
        }
        else east_utility=grid.gridWorld[i][j].getUtility();
        // calculate the sum in intended directions
        sumN=INTENDED_PROBABILITY*north_utility+OTHER_PROBABILITY*(west_utility+east_utility);
        sumS=INTENDED_PROBABILITY*south_utility+OTHER_PROBABILITY*(west_utility+east_utility);
        sumW=INTENDED_PROBABILITY*west_utility+OTHER_PROBABILITY*(north_utility+south_utility);
        sumE=INTENDED_PROBABILITY*east_utility+OTHER_PROBABILITY*(north_utility+south_utility);
        // For Value Iterations and policy Evaluation
        if(algorithm=="value"){
            // computing the max value
            max=Math.max(Math.max(sumN,sumS),Math.max(sumE,sumW));
            // Assigning action to the state
            if(max==sumN) action="N";
            else if(max==sumS) action="S";
            else if(max==sumW) action="W";
            else action="E";
            // computing utility
            utility+=DISCOUNT_FACTOR*max;

            // Setting action
            state.setAction(action);

        }
        // For policy Iteration just calculating in the given direction
        else if(algorithm=="policy"){
            switch (state.getAction()){
                case (State.North):
                    utility+=DISCOUNT_FACTOR*sumN;
                    break;
                case (State.South):
                    utility+=DISCOUNT_FACTOR*sumS;
                    break;
                case (State.West):
                    utility+=DISCOUNT_FACTOR*sumW;
                    break;
                case (State.East):
                    utility+=DISCOUNT_FACTOR*sumE;
                    break;

            }

        }



        // return utility
        return utility;

    }




}
