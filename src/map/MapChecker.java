package map;

import java.util.ArrayList;

public class MapChecker {

    private final Map map;
    private ArrayList<Position> teleport;


    public MapChecker(Map map) {
        this.map = map;
        teleport = new ArrayList<>();
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

    public int checkCurrentPos(Position pos) {
        for (Position special : teleport) {
            if (special.equals(pos)) {
                return special.getID();
            }
        }
        return 0;
    }

    public void addSpecialPos(Position pos, int sceneID) {
        teleport.add(new Position(pos, sceneID));
    }
}
