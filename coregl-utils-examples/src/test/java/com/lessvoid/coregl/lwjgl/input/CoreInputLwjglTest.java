package com.lessvoid.coregl.lwjgl.input;

import java.util.concurrent.atomic.AtomicBoolean;

import org.lwjgl.input.Keyboard;

import com.lessvoid.coregl.input.spi.CoreInput;
import com.lessvoid.coregl.input.spi.CoreKeyEvent;
import com.lessvoid.coregl.input.spi.CoreKeyListener;
import com.lessvoid.coregl.input.spi.CoreMouseEvent;
import com.lessvoid.coregl.input.spi.CoreMouseListener;
import com.lessvoid.coregl.lwjgl.CoreSetupLwjgl;
import com.lessvoid.coregl.lwjgl.LwjglCoreGL;
import com.lessvoid.coregl.spi.CoreGL;
import com.lessvoid.coregl.spi.CoreSetup;
import com.lessvoid.coregl.spi.CoreSetup.RenderLoopCallback;

public class CoreInputLwjglTest {

  public static void main(final String[] args) {
    final CoreGL gl = new LwjglCoreGL();
    final CoreSetup setup = new CoreSetupLwjgl(gl);
    try {
      setup.initialize("Test LWJGL Input", 1024, 768);
      setup.initializeLogging();
      Keyboard.enableRepeatEvents(true);
      final CoreInput input = setup.getInput();
      final CoreKeyListener keyInput = new CoreKeyListener() {
        @Override
        public void keyPressed(final CoreKeyEvent event) {
          System.out.println("pressed: " + event);
        }

        @Override
        public void keyReleased(final CoreKeyEvent event) {
          System.out.println("released: " + event);
        }
      };
      final CoreMouseListener mouseListener = new CoreMouseListener() {

        @Override
        public void mouseClicked(final CoreMouseEvent e) {
          System.out.println("clicked: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseDragged(final CoreMouseEvent e) {
          System.out.println("dragged: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseEntered(final CoreMouseEvent e) {
          System.out.println("entered: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseExited(final CoreMouseEvent e) {
          System.out.println("exited: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseMoved(final CoreMouseEvent e) {
          System.out.println("moved: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mousePressed(final CoreMouseEvent e) {
          System.out.println("pressed: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseReleased(final CoreMouseEvent e) {
          System.out.println("released: " + e.getX() + " " + e.getY());
        }

        @Override
        public void mouseWheelMoved(final CoreMouseEvent e) {
          System.out.println("mouseWheel: " + e.getX() + " " + e.getY());
        }
      };
      input.addListener(keyInput);
      input.addListener(mouseListener);
      setup.renderLoop(new NOPRenderLoop());
      setup.destroy();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  private static class NOPRenderLoop implements RenderLoopCallback {

    private final AtomicBoolean stop = new AtomicBoolean();

    @Override
    public void init(final CoreGL gl) {
    }

    @Override
    public boolean render(final CoreGL gl, final float deltaTime) {
      return true;
    }

    @Override
    public boolean endLoop() {
      return stop.get();
    }
  }
}
