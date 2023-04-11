package map;

public class MapChecker {

    private float[] pos = new float[2];
    private Map map;


    public MapChecker(Map map) {
        this.map = map;
    }

    public boolean checkPos(int x, int y) {
        if (x > map.getHeight() || y > map.getRowLength(x)) {
            return false;
        }




        int parcel = map.getParcel(y, x);
        if(parcel != Map.WATER &&
        parcel != Map.VOID) {
            return true;
        }
        return false;
    }
}
