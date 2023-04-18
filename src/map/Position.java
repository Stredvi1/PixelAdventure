package map;

import java.util.Arrays;

public class Position {

    private int[] parcelPos = new int[2];


    public Position(int[] parcelPos) {
        this.parcelPos = parcelPos;

    }

    public Position(int parcelX, int parcelY) {

        this.parcelPos[0] = parcelX;
        this.parcelPos[1] = parcelY;
    }

    public float[] toMap() {
        float[] mapPos = new float[2];

        mapPos[0] = parcelPos[0] * MapBuilder.MAP_SIZE;
        mapPos[1] = parcelPos[1] * MapBuilder.MAP_SIZE * -1;
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

    public boolean equals(Position pos) {
        if (parcelPos[0] == pos.toParcel()[0] &&
            parcelPos[1] == pos.toParcel()[1]) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position{" +
                "parcelPos=" + Arrays.toString(parcelPos) +
                '}';
    }
}
