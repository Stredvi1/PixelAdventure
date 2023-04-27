package map;

import java.util.ArrayList;

public class Map {

    public static final int VOID = 0;
    public static final int WATER = 7;
    public static final int SAND = 1;
    public static final int GRASS = 2;
    public static final int DIRT = 3;
    public static final int STONE = 4;
    public static final int ASPHALT = 5;
    public static final int TILE = 6;
    public static final int WOOD = 8;






    private int[][] design = {
            {0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3},
            {1, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2},
            {2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {2, 2, 3, 4, 2, 2, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 0, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 0, 0, 4, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 0, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 0, 0, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 0, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 0, 0, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 0, 0, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {0, 0, 1, 4, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},

    };

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
        if (row < this.getHeight() && index < this.getRowLength(row)) {
            return design[row][index];
        }
        return VOID;
    }

    public int getHeight() {
        return design.length;
    }


    public int getRowLength(int row) {
        return design[row].length;
    }

    public int getHighestWidth() {
        int width = 0;
        for (int i = 0; i < getHeight(); i++) {
            if(getRowLength(i) > width) {
                width = getRowLength(i);
            }
        }
        return width;
    }

}
