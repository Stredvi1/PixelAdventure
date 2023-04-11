package map;

import java.util.Arrays;

public class Position {

    private int[] parcelPos = new int[2];

    private int mapSize;

    public Position(int mapSize) {
        this.mapSize = mapSize;
    }

    public Position(int[] parcelPos, int mapSize) {
        this.mapSize = mapSize;
        this.parcelPos = parcelPos;

    }

    public Position(int parcelX, int parcelY, int mapSize) {

        this.mapSize = mapSize;
        this.parcelPos[0] = parcelX;
        this.parcelPos[1] = parcelY;
    }

    public Position(float[] mapPos, int mapSize) {
        this.mapSize = mapSize;
    }

    public float[] toMap() {
        float[] mapPos = new float[2];

        mapPos[0] = parcelPos[0] * mapSize;
        mapPos[1] = parcelPos[1] * mapSize * -1;
        return mapPos;
    }

    public int[] toParcel() {
        return parcelPos;
    }

    public void x(int x) {
        parcelPos[0] = x;
    }

    public void y(int y) {
        parcelPos[1] = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "parcelPos=" + Arrays.toString(parcelPos) +
                '}';
    }
}
