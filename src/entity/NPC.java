package entity;

import map.Position;
import java.util.ArrayList;

public class NPC extends Entity{

    private ArrayList<Message> messages;

    public NPC(Position pos, String textureName) {
        this.pos = pos;

        init(textureName);
    }

    protected void init(String textureName) {
        super.init(textureName);
        messages = new ArrayList<>();

    }

    public void showMessage() {
        for(Message m : messages) {
            m.show();
        }
    }

    public void addMessage(String text) {
        messages.add(new Message(text));
    }
}
