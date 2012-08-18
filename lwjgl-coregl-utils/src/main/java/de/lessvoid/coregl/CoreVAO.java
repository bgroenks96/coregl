package de.lessvoid.coregl;


import static org.lwjgl.opengl.ARBInstancedArrays.glVertexAttribDivisorARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * A Vertex Array Object (VAO).
 * @author void
 */
public class CoreVAO {
  private int vao;

  /**
   * Create a new VAO. This calls glGenVertexArrays.
   */
  public CoreVAO() {
    init();
  }

  /**
   * Bind this VAO to make it the current VAO.
   */
  public void bind() {
    glBindVertexArray(vao);
    CoreCheckGL.checkGLError("glBindVertexArray");
  }

  /**
   * Unbinds this VAO which makes the VAO with id 0 the active one.
   */
  public void unbind() {
    glBindVertexArray(0);
    CoreCheckGL.checkGLError("glBindVertexArray(0)");
  }

  /**
   * Delete all resources for this VAO.
   */
  public void delete() {
    glDeleteVertexArrays(vao);
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data will be
   * GL_FLOAT.
   *
   * @param index the index of the vertex attribute to modify
   * @param size the size of the data for this vertex attribute
   *        (the number of GL_FLOAT to use)
   * @param stride the stride between the data
   * @param offset the offset of the data
   */
  public void enableVertexAttributef(final int index, final int size, final int stride, final int offset) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glEnableVertexAttribArray(index);
    CoreCheckGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /**
   * Configure the vertex attribute with the given data. The type of the data will be
   * GL_FLOAT. This will additionally call glVertexAttribDivisorARB to change the
   * frequency this data will be sent.
   *
   * @param index the index of the vertex attribute to modify
   * @param size the size of the data for this vertex attribute
   *        (the number of GL_FLOAT to use)
   * @param stride the stride between the data
   * @param offset the offset of the data
   * @param divisor when the divisor is 1 this data is sent with every vertex. when it's 2
   *        the data is send for every other vertex and so on.
   */
  public void enableVertexAttributeDivisorf(final int index, final int size, final int stride, final int offset, final int divisor) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, stride * 4, offset * 4);
    glVertexAttribDivisorARB(index, divisor);
    glEnableVertexAttribArray(index);
    CoreCheckGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  private void init() {
    vao = glGenVertexArrays();
    CoreCheckGL.checkGLError("glGenVertexArrays");
  }
}
