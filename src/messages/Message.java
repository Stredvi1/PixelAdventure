package messages;

import map.Position;

public class Message {

    private final String text;
    private final Position posToShowMessage;
    private final boolean checkPos;

    public Message(String author, String text, Position posToShowMessage) {
        this.posToShowMessage = posToShowMessage;
        this.text = author + ":  " + text;
        this.checkPos = true;
    }

    public Message(String text, Position pos) {
        this.text = "VITO:  " + text;
        this.posToShowMessage = pos;
        this.checkPos = true;
    }

    public Message(String text) {
        this.text = "VITO:  " + text;
        this.posToShowMessage = null;
        this.checkPos = false;
    }

    @Override
    public String toString() {
        return text;
    }

    public Position getPos() {
        return posToShowMessage;
    }
     public boolean shouldCheckPos() {
        return checkPos;
     }
}
