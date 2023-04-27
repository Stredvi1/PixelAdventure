package map;

import java.util.Random;


public class RandMapgenerator {


    public static final int VOID = 0;
    public static final int WATER = 7;
    public static final int SAND = 1;
    public static final int GRASS = 2;
    public static final int DIRT = 3;
    public static final int STONE = 4;
    public static final int ASPHALT = 5;
    public static final int TILE = 6;

    public static void main(String[] args) {
        // Vytvoření dvourozměrného pole o rozměrech 20 x 20

        int mapWidth = 18;
        int mapHeight = 9;
        int[][] mapDesign = new int[mapHeight][mapWidth];

        // Náhodné naplnění pole prvky z daného seznamu s určitou pravděpodobností
        Random rand = new Random();
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int randNum = rand.nextInt(100);
                if (randNum < 1) {
                    mapDesign[i][j] = WATER;
                } else if (randNum < 8) {
                    mapDesign[i][j] = SAND;
                } else if (randNum < 80) {
                    mapDesign[i][j] = GRASS;
                } else if (randNum < 82) {
                    mapDesign[i][j] = DIRT;
                } else {
                    mapDesign[i][j] = STONE;
                }
            }
        }

        // Výpis pole na konzoli pro kontrolu
        for (int i = 0; i < mapHeight; i++) {
            System.out.print("{");
            for (int j = 0; j < mapWidth; j++) {
                System.out.print(mapDesign[i][j] + ",");
            }
            System.out.print("},");
            System.out.println();
        }
    }
}


