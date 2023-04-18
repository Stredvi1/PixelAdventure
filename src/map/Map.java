package map;

import java.util.ArrayList;

public class Map {

    public static final int VOID = -1;
    public static final int WATER = 0;
    public static final int SAND = 1;
    public static final int GRASS = 2;
    public static final int DIRT = 3;
    public static final int STONE = 4;
    public static final int ASPHALT = 5;
    public static final int TILE = 6;





    private int[][] design = {
            {0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3},
            {1, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2},
            {2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {2, 2, 3, 4, 2, 2, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 0, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 0, 0, 4, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, -1, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 0, 0, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 0, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},

    };


//    private int[][] design = {
//            {3, 3, 0, 0, 0, 0},
//            {3, 1, 1, 1, 1, 0},
//            {0, 2, 1, 1, 1},
//            {0, 1, 1, 1, 1},
//            {0, 0, 0, 0,}
//
//    };


    public Map(int[][] design) {
        this.design = design;
    }

    public Map() {
    }

    public void setDesign(int[][] design) {
        this.design = design;
    }

    public void setParcel(int i, int j, int type) {
        this.design[i][j] = 0;
    }

    public int[][] getDesign() {
        return this.design;
    }

    public int getParcel(int row, int index) {
        //TODO PODMÍNKA
        if (row < this.getHeight() && index < this.getWidth()) {
            return design[row][index];
        }
        return -1;
    }

    public int getHeight() {
        return design.length;
    }

    public int getWidth() {
        return design[0].length;

    }

    public int getRowLength(int row) {
        return design[row].length;
    }
}
