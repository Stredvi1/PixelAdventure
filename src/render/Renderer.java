package render;

import entity.Bob;
import lwjglutils.GLCamera;
import map.Map;
import map.MapBuilder;
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
    private float trans = 0;
    private float[] pos = new float[2];

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
                switch (key) {
                    case GLFW_KEY_W:
                        //camera.forward(trans);
                        pos[0] -= trans;
                        bob.setPosition(pos);
                        break;
                    case GLFW_KEY_S:
                       // camera.backward(trans);
                        pos[0] += trans;
                        bob.setPosition(pos);

                        break;
                    case GLFW_KEY_A:
                       // camera.right(trans);
                        pos[1] -= trans;
                        bob.setPosition(pos);

                        break;
                    case GLFW_KEY_D:
                       // camera.left(trans);
                        pos[1] += trans;
                        bob.setPosition(pos);

                        break;

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
        trans = mapBuilder.getMapSize() / 2f;
        pos[0] = map.getWidth() / 2f;
        pos[1] = map.getHeight() / 2f;
        bob = new Bob(pos[0], pos[1]);

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
        glRotatef(-90, 0, 0, 1);
        gluPerspective(45, width / (float) height, 0.1f, 100.0f);


        gluLookAt(
                pos[0], pos[1] , 50,
                pos[0], pos[1] , 0,
                0, 1, 0
        );



        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();


        mapBuilder.renderMap();
        bob.render();


    }


    private void clearBuffers() {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

}
