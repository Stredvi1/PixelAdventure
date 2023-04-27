package entity;

import map.MapBuilder;
import map.Position;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class NPC extends Entity{

    private String name;

    public NPC(Position pos, String name, String textureName) {
        this.pos = pos;
        this.name = name;
        init(textureName);
        size = MapBuilder.MAP_SIZE * 1.4f;
    }

    protected void init(String textureName) {
        super.init(textureName);

    }

    public String getName() {
        return name;
    }

    public void setTexture(String textureName) {
        init(textureName);
    }

    public void render() {

        texture.bind();

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glTranslatef(pos.toMap()[0] - 1.3f, pos.toMap()[1] + 0.8f, 1);


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

}
