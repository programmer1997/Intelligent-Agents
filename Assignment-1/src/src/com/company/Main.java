package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        // Hashmap of rewards for different squares
        HashMap<String, Double> stateRewards = new HashMap();
        stateRewards.put("g", 1.0);
        stateRewards.put("b", -1.0);
        stateRewards.put("w", -0.04);

        int start_x = 3;
        int start_y = 2;


        //// execute value iteration

        Grid grid = new Grid(6, 6, "grid_config.txt", stateRewards, start_x, start_y);
        valueIteration(grid);
        printUtilities(grid);
        printPolicy(grid);
        System.out.println("POLICY ITERATION");
        Grid grid2 = new Grid(6, 6, "grid_config.txt", stateRewards, start_x, start_y);
        policyIteration(grid2);
        printUtilities(grid2);
        printPolicy(grid2);

    }

    public static void valueIteration(Grid grid) {
        // convergence value
        double EPSILON = 0.7;

        for (int iterations = 1; iterations <= 50; iterations++) {
            // array for storing all the delta values in an interation
            ArrayList<Double> delta = new ArrayList<>();
            for (int i = 0; i < grid.getNoRows(); i++) {
                for (int j = 0; j < grid.getNoCols(); j++) {
                    // computing utility using bellman equation
                    double new_utility = UtilityFunctions.computeOptimalUtilityAndAction(grid, i, j, "value");
                    // computing delta
                    delta.add(Math.abs(new_utility - grid.gridWorld[i][j].getUtility()));
                    // setting new utility
                    grid.gridWorld[i][j].setUtility(new_utility);
                    // writing utility values into the file for this iteration
                    try {

                        FileWriter fstream = new FileWriter("file"+Integer.toString(i)+Integer.toString(j)+".txt",true);
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write(Double.toString(grid.gridWorld[i][j].getUtility())+"\n");
                        out.close();

                    }
                    catch(IOException ioe){


                    }
                }
            }
            // Checking convergence condition
            if (Collections.max(delta) <= EPSILON) {
                System.out.println("Iterations==" + iterations);
                break;
            }

        }



    }

    public static void policyIteration(Grid grid) {
        // policy iterations
        for (int iterations = 1; iterations <= 50; iterations++) {
            // convergence criteria to check change in policy
            boolean convergence = true;
            // convergence value for policy evaluation
            double convergence_value = 0.7;
            // Policy Evaluation iterations
            for (int policyEvalIterations = 1; policyEvalIterations < 50; policyEvalIterations++) {
                ArrayList<Double> delta = new ArrayList<>();
                for (int i = 0; i < grid.getNoRows(); i++) {
                    for (int j = 0; j < grid.getNoCols(); j++) {
                        // Calculating utilities for the given action
                        double currentUtility = grid.gridWorld[i][j].getUtility();
                        double newUtility = UtilityFunctions.computeOptimalUtilityAndAction(grid, i, j, "policy");
                        grid.gridWorld[i][j].setUtility(newUtility);
                        delta.add(Math.abs(currentUtility - newUtility));
                    }
                }
                // checking for policy evalaution convergence
                if (Collections.max(delta) <= convergence_value) {
                    System.out.println(policyEvalIterations);
                    break;
                }
            }
            // writing utilities  to file after policy evaluation
            for (int i = 0; i < grid.getNoRows(); i++) {
                for (int j = 0; j < grid.getNoCols(); j++) {
                    try {
                        FileWriter fstream = new FileWriter("file"+Integer.toString(i)+Integer.toString(j)+".txt",true);
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write(Double.toString(grid.gridWorld[i][j].getUtility())+"\n");
                        out.close();
                    }
                    catch(IOException ioe){
                    }
                    String currentPolicy = grid.gridWorld[i][j].getAction();
                    grid.gridWorld[i][j].setUtility(UtilityFunctions.computeOptimalUtilityAndAction(grid,i,j,"value"));
                    String newPolicy=grid.gridWorld[i][j].getAction();
                    if (currentPolicy!=newPolicy) convergence=false;


                }
                }
            // checking for policy iteration convergence
            if (convergence == true) {
               System.out.println(" Policy Iterations==" + iterations);
               break;
           }
        }
    }

    public static  void printUtilities(Grid grid){
        System.out.println("Printing utilties");
        for (int i = 0; i < grid.getNoRows(); i++) {
            for (int j = 0; j < grid.getNoCols(); j++) {
                System.out.printf("%.2f",grid.gridWorld[i][j].getUtility());

                System.out.print("   ");
            }
            System.out.println();
        }


    }

    public static void printPolicy( Grid grid){
        System.out.println("Printing policy");
        for (int i = 0; i < grid.getNoRows(); i++) {
            for (int j = 0; j < grid.getNoCols(); j++) {
                String action=grid.gridWorld[i][j].getAction();
                if(action=="")  System.out.print("*");
                else  System.out.print(action);

                System.out.print("   ");
            }
            System.out.println();
        }

    }

}
