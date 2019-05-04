package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Grid {


    // Dimensions of grid
    private int noRows ;
    private  int noCols ;
    // Start state coordinates
    private int start_x;
    private  int start_y;
    // path of config file of grid
    private String configPath;
    // rewards for each color
    HashMap<String,Double> stateRewards;
    // A 2D array of state objects
    State[][] gridWorld;



   // Constructor
   public Grid(int noRows,int noCols,String configPath,HashMap<String,Double> stateRewards,int start_x,int start_y){
       this.noRows=noRows;
       this.noCols=noCols;
       this.configPath=configPath;
       this.stateRewards=stateRewards;
       this.gridWorld=new State[noRows][noCols];
       this.start_x=start_x;
       this.start_y=start_y;
       initialiseGridStates();
       initialisePossibleActions();
   }




    // Read text file and initialise grid
    public void initialiseGridStates(){
        try {
            int row_no=0;
            BufferedReader reader = new BufferedReader(new FileReader(this.configPath));
            String line=reader.readLine();
            while (line!=null){

                String[] parts=line.split(",");

                for(int col_no=0;col_no<this.getNoCols();col_no++){
                    //grid_states[row_no][col_no]=parts[col_no];
                    String state=parts[col_no];
                    switch (state) {
                        case "wl":
                            gridWorld[row_no][col_no] = new State(row_no, col_no, 0, 0, true);
                            break;
                        default:
                            gridWorld[row_no][col_no] = new State(row_no, col_no, stateRewards.get(state), 0, false);
                            break;

                    }

                }
                line=reader.readLine();
                ++row_no;
            }
        }
        catch(FileNotFoundException fnp){

        }
        catch (IOException ioe) {

        }



    }

    // initialise possible actions for each state depending on position in the grid
    // Possible actions are those if a movement can be made without colliding with maze boundary or wall and position can be changed
    private void initialisePossibleActions(){
        for(int i = 0; i< this.noRows; i++) {
            for (int j = 0; j < this.noCols; j++) {
                // no possible actions for wall states
                if (gridWorld[i][j].isWall()) continue;
                // If state is not in the first row and does not have wall immediately above
                if((i!=0) && !(gridWorld[i-1][j].isWall())) gridWorld[i][j].setMoveNorth(true);
                // If state is not in the last row and does not have wall immediately below
                if((i!= this.noRows -1)&&!(gridWorld[i+1][j].isWall())) gridWorld[i][j].setMoveSouth(true);
                // If state is not in the leftmost column and there is no wall immediately to the west
                if((j!=0) && !(gridWorld[i][j-1].isWall())) gridWorld[i][j].setMoveWest(true);
                // If state is not in the rightmost column and there is no wall to immediately to the east
                if((j!= this.noCols -1)&&!(gridWorld[i][j+1].isWall())) gridWorld[i][j].setMoveEast(true);


            }
        }

    }



    public int getNoRows() {
        return noRows;
    }

    public int getNoCols() {
        return noCols;
    }







}
