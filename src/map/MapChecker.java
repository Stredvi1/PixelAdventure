package map;

import render.Renderer;

import java.util.ArrayList;

public class MapChecker {

    private final Map map;
    private ArrayList<Position> teleportPad;
    private ArrayList<Position> teleportTo;



    public MapChecker(Map map) {
        this.map = map;
        teleportPad = new ArrayList<>();
        teleportTo = new ArrayList<>();
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
        for (Position special : teleportPad) {
            if (special.equals(pos)) {
                return special.getID();
            }
        }
        return 0;
    }

    public void addTeleportPad(Position pos, int sceneID) {
        teleportPad.add(new Position(pos, sceneID));
        teleportTo.add(null);
    }

    public void addTeleportPad(Position pos, int sceneID, Position teleportTo) {
        teleportPad.add(new Position(pos, sceneID));
        this.teleportTo.add(new Position(teleportTo, sceneID));
    }

    public Position getTeleportTo(Position playerPos) {
        for(int i = 0; i < teleportPad.size(); i++) {
            if(teleportPad.get(i).equals(playerPos)) {
                return teleportTo.get(i);
            }
        }
        return null;
    }
}
