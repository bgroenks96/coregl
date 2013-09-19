package de.lessvoid.coregl;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Helper class that represents a shader (actually the combination of a vertex
 * and a fragment shader - what GL actually calls a program).
 * @author void
 */
public class CoreShader {
  private static final Logger log = Logger.getLogger(CoreShader.class.getName());
  private int program;
  private Hashtable<String, Integer> parameter = new Hashtable<String, Integer>();
  private FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
  private final String[] attributes;

  /**
   * Create a new Shader.
   * @return the new CoreShader instance
   */
  public static CoreShader newShader() {
    return new CoreShader();
  }

  /**
   * Create a new Shader with the given vertex attributes automatically bind to the generic attribute indices in
   * ascending order beginning with 0. This method can be used when you want to control the vertex attribute binding
   * on your own.
   *
   * @param vertexAttributes the name of the vertex attribute. The first String gets generic attribute index 0. the
   *        second String gets generic attribute index 1 and so on.
   * @return the CoreShader instance
   */
  public static CoreShader newShaderWithVertexAttributes(final String ... vertexAttributes) {
    return new CoreShader(vertexAttributes);
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public int vertexShader(final String filename) {
    return vertexShader(getStream(filename));
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public int fragmentShader(final String filename) {
    return fragmentShader(getStream(filename));
  }

  public int fragmentShader(final String file, final InputStream ... inputStreams) throws FileNotFoundException {
    InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return fragmentShader(sources);
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public int geometryShader(final String filename) {
    return geometryShader(getStream(filename));
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int vertexShader(final File file) throws FileNotFoundException {
    return vertexShader(getStream(file));
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int fragmentShader(final File file) throws FileNotFoundException {
    return fragmentShader(getStream(file));
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int geometryShader(final File file) throws FileNotFoundException {
    return geometryShader(getStream(file));
  }

  public int geometryShader(final File file, final InputStream ... inputStreams) throws FileNotFoundException {
    InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return geometryShader(sources);
  }

  public int geometryShader(final String file, final InputStream ... inputStreams) throws FileNotFoundException {
    InputStream[] sources = new InputStream[inputStreams.length + 1];
    System.arraycopy(inputStreams, 0, sources, 0, inputStreams.length);
    sources[sources.length - 1] = getStream(file);
    return geometryShader(sources);
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int vertexShader(final InputStream source) {
    int shaderId = glCreateShader(GL_VERTEX_SHADER);
    checkGLError("glCreateShader(GL_VERTEX_SHADER)");
    prepareShader(shaderId, source);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int fragmentShader(final InputStream ... sources) {
    int shaderId = glCreateShader(GL_FRAGMENT_SHADER);
    checkGLError("glCreateShader(GL_FRAGMENT_SHADER)");
    prepareShader(shaderId, sources);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public int geometryShader(final InputStream ... sources) {
    int shaderId = glCreateShader(GL_GEOMETRY_SHADER);
    checkGLError("glCreateShader(GL_GEOMETRY_SHADER)");
    prepareShader(shaderId, sources);
    glAttachShader(program, shaderId);
    checkGLError("glAttachShader");
    return shaderId;
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public void vertexShader(final int shaderId, final String filename) {
    vertexShader(shaderId, getStream(filename));
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public void fragmentShader(final int shaderId, final String filename) {
    fragmentShader(shaderId, getStream(filename));
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the filename of the shader
   */
  public void geometryShader(final int shaderId, final String filename) {
    geometryShader(shaderId, getStream(filename));
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void vertexShader(final int shaderId, final File file) throws FileNotFoundException {
    vertexShader(shaderId, getStream(file));
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void fragmentShader(final int shaderId, final File file) throws FileNotFoundException {
    fragmentShader(shaderId, getStream(file));
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void geometryShader(final int shaderId, final File file) throws FileNotFoundException {
    geometryShader(shaderId, getStream(file));
  }

  /**
   * Attach the given vertex shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void vertexShader(final int shaderId, final InputStream source) {
    prepareShader(shaderId, source);
  }

  /**
   * Attach the given fragment shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void fragmentShader(final int shaderId, final InputStream source) {
    prepareShader(shaderId, source);
  }

  /**
   * Attach the given geometry shader file to this CoreShader. This will call glCreateShader(), loads and compiles
   * the shader source and finally attaches the shader.
   * @param filename the file of the shader
   */
  public void geometryShader(final int shaderId, InputStream source) {
    prepareShader(shaderId, source);
  }

  /**
   * Link the Shader.
   */
  public void link() {
    for (int i=0; i<attributes.length; i++) {
      glBindAttribLocation(program, i, attributes[i]);
      checkGLError("glBindAttribLocation (" + attributes[i] + ")");
    }

    glLinkProgram(program);
    checkGLError("glLinkProgram");

    if (glGetProgram(program, GL_LINK_STATUS) != GL_TRUE) {
      log.warning("link error: " + glGetProgramInfoLog(program, 1024));
      checkGLError("glGetProgramInfoLog");
    }
    checkGLError("glGetProgram");
  }

  /**
   * Set the uniform with the given name to the given float value.
   * @param name name of the uniform to set
   * @param value the new float value
   */
  public void setUniformf(final String name, final float value) {
    glUniform1f(getLocation(name), value);
    checkGLError("glUniform1f");
  }

  /**
   * Set the uniform with the given name to the given value (two floats = vec2).
   * @param name name of the uniform to set
   * @param v1 first component of the vec2
   * @param v2 second component of the vec2
   */
  public void setUniformf(final String name, final float v1, final float v2) {
    glUniform2f(getLocation(name), v1, v2);
    checkGLError("glUniform2f");
  }

  /**
   * Set the uniform with the given name to the given value (three floats = vec3).
   * @param name name of the uniform to set
   * @param v1 first component of the vec3
   * @param v2 second component of the vec3
   * @param v3 third component of the vec3
   */
  public void setUniformf(final String name, final float v1, final float v2, final float v3) {
    glUniform3f(getLocation(name), v1, v2, v3);
    checkGLError("glUniform3f");
  }

  /**
   * Set the uniform with the given name to the given value (four floats = vec4).
   * @param name name of the uniform to set
   * @param v1 first component of the vec4
   * @param v2 second component of the vec4
   * @param v3 third component of the vec4
   * @param v4 fourth component of the vec4
   */
  public void setUniformf(final String name, final float x, final float y, final float z, final float w) {
    glUniform4f(getLocation(name), x, y, z, w);
    checkGLError("glUniform4f");
  }

  /**
   * Set the uniform with the given name to the given int value.
   * @param name name of the uniform to set
   * @param value the new int value
   */
  public void setUniformi(final String name, final int v1) {
    glUniform1i(getLocation(name), v1);
    checkGLError("glUniform1i");
  }

  /**
   * Set the uniform with the given name to the given int values (two values = ivec2).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec2
   * @param v2 the second int value of the ivec2
   */
  public void setUniformi(final String name, final int v1, final int v2) {
    glUniform2i(getLocation(name), v1, v2);
    checkGLError("glUniform2i");
  }

  /**
   * Set the uniform with the given name to the given int values (three values = ivec3).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec3
   * @param v2 the second int value of the ivec3
   * @param v3 the third int value of the ivec3
   */
  public void setUniformi(final String name, final int v1, final int v2, final int v3) {
    glUniform3i(getLocation(name), v1, v2, v3);
    checkGLError("glUniform3i");
  }

  /**
   * Set the uniform with the given name to the given int values (four values = ivec4).
   * @param name name of the uniform to set
   * @param v1 the first int value of the ivec4
   * @param v2 the second int value of the ivec4
   * @param v3 the third int value of the ivec4
   * @param v3 the fourth int value of the ivec4
   */
  public void setUniformi(final String name, final int v1, final int v2, final int v3, final int v4) {
    glUniform4i(getLocation(name), v1, v2, v3, v4);
    checkGLError("glUniform4i");
  }

  /**
   * Set the uniform mat4 with the given name to the given matrix (Matrix4f).
   * @param name the name of the uniform
   * @param matrix the Matrix4f to set
   */
  public void setUniformMatrix4f(final String name, final Matrix4f matrix) {
    matBuffer.clear();
    matrix.store(matBuffer);
    matBuffer.rewind();
    glUniformMatrix4(getLocation(name), false, matBuffer);
    checkGLError("glUniformMatrix4");
  }

  /**
   * Set the uniform mat4 with the given name to the given matrix (Matrix4f).
   * @param name the name of the uniform
   * @param matrix the Matrix4f to set
   */
  public void setUniformMatrix3f(final String name, final Matrix3f matrix) {
    matBuffer.clear();
    matrix.store(matBuffer);
    matBuffer.rewind();
    glUniformMatrix3(getLocation(name), false, matBuffer);
    checkGLError("glUniformMatrix3");
  }

  /**
   * Set the uniform float array with the given name to a new value.
   * @param name the name of the uniform
   * @param values the new float array to set
   */
  public void setUniformfArray(final String name, final float[] values) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
    buffer.put(values);
    buffer.rewind();
    glUniform1(getLocation(name), buffer);
    checkGLError("glUniform1");
  }

  /**
   * Get the vertex attribute location of the vertex attribute with the given name.
   * @param name the name of the vertex attribute
   * @return the generic vertex attribute index value
   */
  public int getAttribLocation(final String name) {
    int result = glGetAttribLocation(program, name);
    checkGLError("glGetAttribLocation");
    return result;
  }

  /**
   * You can manually bind the vertex attribute with the given name to the given specific index value. You'll need to
   * call this method before calling the link() method!
   * 
   * @param name the name of the vertex attribute
   * @param index the new index you want to give that vertex attribute
   */
  public void bindAttribLocation(final String name, final int index) {
    glBindAttribLocation(program, index, name);
    checkGLError("glBindAttribLocation");
  }

  /**
   * Activate this program.
   */
  public void activate() {
    glUseProgram(program);
    checkGLError("glUseProgram");
  }

  private CoreShader(final String ... vertexAttributes) {
    this.attributes = vertexAttributes;
    this.program = glCreateProgram();
    checkGLError("glCreateProgram");
  }

  private int registerParameter(final String name) {
    int location = getUniform(name);
    parameter.put(name, location);
    return location;
  }

  private int getLocation(final String name) {
    Integer value = parameter.get(name);
    if (value == null) {
      return registerParameter(name);
    }
    return value;
  }

  private int getUniform(final String uniformName) {
    try {
      byte[] bytes = uniformName.getBytes("ISO-8859-1");
      ByteBuffer name = BufferUtils.createByteBuffer(bytes.length + 1);
      name.put(bytes);
      name.put((byte)0x00);
      name.rewind();
      int result = glGetUniformLocation(program, name);
      checkGLError("glGetUniformLocation for [" + uniformName + "] failed");
      log.fine(getLoggingPrefix() + "glUniformLocation for [" + uniformName + "] = [" + result + "]");
      return result;
    } catch (UnsupportedEncodingException e) {
      log.log(Level.WARNING, getLoggingPrefix() + e.getMessage(), e);
      return -1;
    }
  }

  private void prepareShader(final int shaderId, final InputStream ... sources) {
    try {
      glShaderSource(shaderId, loadShader(sources));
      checkGLError("glShaderSource");
    } catch (IOException e) {
      throw new CoreGLException(e);
    }

    glCompileShader(shaderId);
    checkGLError("glCompileShader");

    if (glGetShader(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
      log.warning("compile error: " + glGetShaderInfoLog(shaderId, 1024));
    }

    printLogInfo(shaderId);
    checkGLError(String.valueOf(shaderId));
  }

  private ByteBuffer loadShader(final InputStream ... sources) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    for (InputStream source : sources) {
      out.write(read(source));
    }

    byte[] data = out.toByteArray();
    ByteBuffer result = BufferUtils.createByteBuffer(data.length);
    result.put(data);
    result.flip();
    return result;
  }

  private byte[] read(final InputStream dataStream) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    byte[] readBuffer = new byte[1024];
    int bytesRead = -1;
    try {
      while ((bytesRead = dataStream.read(readBuffer)) > 0) {
        out.write(readBuffer, 0, bytesRead);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        dataStream.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return out.toByteArray();
  }

  private void printLogInfo(final int obj) {
    ByteBuffer infoLog = BufferUtils.createByteBuffer(2048);
    IntBuffer lengthBuffer = BufferUtils.createIntBuffer(1);
    glGetShaderInfoLog(obj, lengthBuffer, infoLog);
    checkGLError("glGetShaderInfoLog");

    byte[] infoBytes = new byte[lengthBuffer.get()];
    infoLog.get(infoBytes);
    if (infoBytes.length == 0) {
      return;
    }
    try {
      log.info(getLoggingPrefix() + "Info log:\n" + new String(infoBytes, "ISO-8859-1"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    checkGLError("printLogInfo");
  }

  private void checkGLError(final String message) {
    CoreCheckGL.checkGLError(getLoggingPrefix() + message);
  }

  private String getLoggingPrefix() {
    return "[" + program + "] ";
  }

  private InputStream getStream(final File file) throws FileNotFoundException {
    log.fine("loading shader file [" + file + "]");
    return new ByteArrayInputStream(read(new FileInputStream(file)));
  }

  private InputStream getStream(final String filename) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
  }
}
