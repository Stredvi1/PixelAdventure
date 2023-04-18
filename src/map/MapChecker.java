package map;

public class MapChecker {

    private Map map;

    public MapChecker(Map map) {
        this.map = map;
    }

    public boolean checkMove(int x, int y) {
        if (y < 0 || y >= map.getHeight() ||
            x >= map.getRowLength(y) || x < 0)
         {
            return false;
        }


        //TODO WHY U STOOPID BASTARD? WHY!
        int parcel = map.getParcel(y, x);

        if(parcel != Map.WATER &&
        parcel != Map.VOID) {
            return true;
        }
        return false;
    }
}
