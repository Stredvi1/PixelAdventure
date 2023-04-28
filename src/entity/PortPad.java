package entity;

import map.Position;

import static org.lwjgl.opengl.GL11.*;

public class PortPad extends Entity{

    public PortPad() {
        init("portPad.png");
    }

    public PortPad(Position pos) {
        this.pos = new Position(pos.getX(), pos.getY());
        init("portPad.png");
    }

    @Override
    public void render() {
        texture.bind();

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(pos.toMap()[0], pos.toMap()[1], 1);


        glBegin(GL_QUADS);

        glTexCoord2f(0, 0);
        glVertex2f(0, 0);

        glTexCoord2f(1, 0);
        glVertex2f(size, 0);

        glTexCoord2f(1, 1);
        glVertex2f(size, size);

        glTexCoord2f(0, 1);
        glVertex2f(0, size);

        glEnd();
        glPopMatrix();
    }

    @Override
    public void setPosition(Position pos) {
        this.pos = new Position(pos.getX(), pos.getY());
    }
}
