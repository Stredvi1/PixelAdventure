package map;

import java.util.ArrayList;

public class Map {

    public static final int WATER = 0;
    public static final int GRASS = 1;
    public static final int DIRT = 2;
    public static final int STONE = 3;
    public static final int SAND = 4;


    private int[][] design = {
            {WATER, WATER, WATER, SAND, SAND, SAND, GRASS},
            {WATER, SAND, SAND, GRASS, GRASS, GRASS, DIRT},
            {SAND, GRASS, GRASS, DIRT, DIRT, DIRT, GRASS},
            {GRASS, GRASS, DIRT,  DIRT, STONE, STONE, STONE},
            {GRASS, GRASS, DIRT, STONE, GRASS, GRASS, STONE},
            {WATER, WATER, SAND, STONE, GRASS, GRASS, STONE},
            {WATER, WATER, SAND, STONE, GRASS, GRASS, STONE},
            {WATER, WATER, SAND, STONE, GRASS, GRASS, STONE},
            {WATER, WATER, SAND, STONE, GRASS, GRASS, STONE}
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

    public int getParcel(int i, int j) {
        return design[i][j];
    }

    public int getHeight() {
        return design.length;
    }

    public int getWidth() {
        return design[0].length;

    }
}
