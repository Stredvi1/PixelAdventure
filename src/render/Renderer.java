package render;

import entity.Bob;
import entity.Item;
import lwjglutils.GLCamera;
import map.Map;
import map.MapBuilder;
import map.MapChecker;
import map.Position;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static lwjglutils.GluUtils.gluLookAt;
import static lwjglutils.GluUtils.gluPerspective;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Shows using 3D transformation and interaction in a scene
 *
 * @author PGRF FIM UHK
 * @version 3.1
 * @since 2020-01-20
 */
public class Renderer extends AbstractRenderer {

    private float[] modelMatrix = new float[16];

    private boolean mouseButton1 = false;

    private Map map = new Map();
    private MapBuilder mapBuilder;
    private Bob bob;

    private GLCamera camera;
    private int trans = 1;
    private float[] pos = new float[2];
    private Position position;
    private MapChecker checker;


    private Item baget;

    public Renderer() {
        super();


        glfwKeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    // We will detect this in our rendering loop
                    glfwSetWindowShouldClose(window, true);
                if (action == GLFW_RELEASE) {
                    //do nothing
                }
                if (action == GLFW_PRESS) {

                    int pos0, pos1;

                    switch (key) {
                        case GLFW_KEY_W:

                            pos1 = position.toParcel()[1] - trans;


                            if(checker.checkPos(position.toParcel()[0], pos1)) {
                               position.y(pos1);
                               baget.pickUp(position);
                               bob.setPosition(position);
                            }

                            break;
                        case GLFW_KEY_S:

                            pos1 = position.toParcel()[1] + trans;


                            if(checker.checkPos(position.toParcel()[0], pos1)) {
                                position.y(pos1);
                                bob.setPosition(position);
                            }
                            break;
                        case GLFW_KEY_A:

                            pos0 = position.toParcel()[0] - trans;

                            if(checker.checkPos(pos0, position.toParcel()[1])) {
                                position.x(pos0);
                                bob.setPosition(position);
                            }
                            break;
                        case GLFW_KEY_D:
                            pos0 = position.toParcel()[0] + trans;


                            if(checker.checkPos(pos0, position.toParcel()[1])) {
                                position.x(pos0);
                                bob.setPosition(position);
                                break;
                            }
                    }
                }
            }

        };
    }

    @Override
    public void init() {
        super.init();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glPolygonMode(GL_FRONT, GL_FILL);
        glPolygonMode(GL_BACK, GL_LINE);
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHTING);
        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();
        glGetFloatv(GL_MODELVIEW_MATRIX, modelMatrix);


        camera = new GLCamera();


        mapBuilder = new MapBuilder(map);
        checker = new MapChecker(map);
//        pos[0] = map.getWidth() / 2f;
//        pos[1] = map.getHeight() / 2f;

        position = mapBuilder.getCenter();

        bob = new Bob(position, mapBuilder.getMapSize());
        baget = new Item("textures/bb.png", 3);

        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);

        clearBuffers();


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(45, width / (float) height, 0.1f, 100.0f);


        gluLookAt(
                position.toMap()[0], position.toMap()[1], 50,
                position.toMap()[0], position.toMap()[1], 0,
                0, 1, 0
        );


        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();


        mapBuilder.renderMap();
        baget.render();
        bob.render();

    }


    private void clearBuffers() {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

}
