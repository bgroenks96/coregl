package de.lessvoid.coregl.spi;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @author Aaron Mahan &lt;aaron@forerunnergames.com&gt;
 * @author Brian Groenke (groenke.5@osu.edu)
 */
public interface CoreGL {
  // OpenGL constants
  int GL_ALPHA();

  int GL_ALPHA_TEST();

  int GL_BLEND();

  int GL_BLEND_DST();

  int GL_BLEND_SRC();

  int GL_BYTE();

  int GL_COLOR_BUFFER_BIT();

  int GL_DEPTH_BUFFER_BIT();

  int GL_CULL_FACE();

  int GL_DEPTH_TEST();

  int GL_DST_COLOR();

  int GL_FALSE();

  int GL_FLOAT();

  int GL_INVALID_ENUM();

  int GL_INVALID_OPERATION();

  int GL_INVALID_VALUE();

  int GL_LINEAR();

  int GL_LINEAR_MIPMAP_LINEAR();

  int GL_LINEAR_MIPMAP_NEAREST();

  int GL_LUMINANCE();

  int GL_LUMINANCE_ALPHA();

  int GL_MAX_TEXTURE_SIZE();

  int GL_NEAREST();

  int GL_NEAREST_MIPMAP_LINEAR();

  int GL_NEAREST_MIPMAP_NEAREST();

  int GL_NO_ERROR();

  int GL_NOTEQUAL();

  int GL_OUT_OF_MEMORY();

  int GL_POINTS();

  int GL_RGB();

  int GL_RGBA();

  int GL_SHORT();

  int GL_STACK_OVERFLOW();

  int GL_STACK_UNDERFLOW();

  int GL_TEXTURE_2D();

  int GL_TEXTURE_BINDING_2D();

  int GL_TEXTURE_3D();

  int GL_TEXTURE_BINDING_3D();

  int GL_TEXTURE_MAG_FILTER();

  int GL_TEXTURE_MIN_FILTER();

  int GL_LINE_STRIP();

  int GL_LINE_STRIP_ADJACENCY();

  int GL_TRIANGLES();

  int GL_TRIANGLE_STRIP();

  int GL_TRIANGLE_FAN();

  int GL_TRUE();

  int GL_UNSIGNED_BYTE();

  int GL_UNSIGNED_SHORT();

  int GL_UNSIGNED_SHORT_4_4_4_4();

  int GL_UNSIGNED_SHORT_5_5_5_1();

  int GL_UNSIGNED_SHORT_5_6_5();

  int GL_VIEWPORT();

  int GL_ZERO();

  int GL_ONE();

  int GL_ONE_MINUS_DST_ALPHA();

  int GL_DST_ALPHA();

  int GL_SRC_ALPHA();

  int GL_ONE_MINUS_SRC_ALPHA();

  int GL_MAX();

  int GL_FUNC_ADD();

  int GL_ACTIVE_TEXTURE();

  int GL_ARRAY_BUFFER();

  int GL_BGR();

  int GL_BGRA();

  int GL_BLUE();

  int GL_COMPILE_STATUS();

  int GL_COMPRESSED_RGB();

  int GL_COMPRESSED_RGBA();

  int GL_CURRENT_PROGRAM();

  int GL_DYNAMIC_DRAW();

  int GL_ELEMENT_ARRAY_BUFFER();

  int GL_FRAGMENT_SHADER();

  int GL_GEOMETRY_SHADER();

  int GL_GREEN();

  int GL_INT();

  int GL_LINK_STATUS();

  int GL_PRIMITIVE_RESTART();

  int GL_PRIMITIVE_RESTART_INDEX();

  int GL_RED();

  int GL_STATIC_DRAW();

  int GL_STREAM_DRAW();

  int GL_TEXTURE0();

  int GL_TEXTURE_2D_ARRAY();

  int GL_TEXTURE_CUBE_MAP_NEGATIVE_X();

  int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y();

  int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z();

  int GL_TEXTURE_CUBE_MAP_POSITIVE_X();

  int GL_TEXTURE_CUBE_MAP_POSITIVE_Y();

  int GL_TEXTURE_CUBE_MAP_POSITIVE_Z();

  int GL_UNSIGNED_BYTE_2_3_3_REV();

  int GL_UNSIGNED_BYTE_3_3_2();

  int GL_UNSIGNED_INT();

  int GL_UNSIGNED_INT_10_10_10_2();

  int GL_UNSIGNED_INT_2_10_10_10_REV();

  int GL_UNSIGNED_INT_8_8_8_8();

  int GL_UNSIGNED_INT_8_8_8_8_REV();

  int GL_HALF_FLOAT();

  int GL_DOUBLE();

  int GL_FIXED();

  int GL_INT_2_10_10_10_REV();

  int GL_UNSIGNED_INT_10F_11F_11F_REV();

  int GL_UNSIGNED_SHORT_5_6_5_REV();

  int GL_UNSIGNED_SHORT_4_4_4_4_REV();

  int GL_UNSIGNED_SHORT_1_5_5_5_REV();

  int GL_VERTEX_SHADER();

  int GL_WRITE_ONLY();

  int GL_UNIFORM_OFFSET();

  int GL_UNIFORM_ARRAY_STRIDE();

  int GL_UNIFORM_MATRIX_STRIDE();

  int GL_PACK_ALIGNMENT();

  int GL_STENCIL_INDEX();

  int GL_TEXTURE_BUFFER();

  int GL_R32F();

  int GL_FRAMEBUFFER();

  int GL_FRAMEBUFFER_COMPLETE();

  int GL_FRAMEBUFFER_UNDEFINED();

  int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT();

  int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT();

  int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER();

  int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER();

  int GL_FRAMEBUFFER_UNSUPPORTED();

  int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE();

  int GL_COLOR_ATTACHMENT0();

  int GL_RENDERBUFFER();

  int GL_STENCIL_INDEX8();

  int GL_STENCIL_ATTACHMENT();

  int GL_VERSION();

  int GL_SHADING_LANGUAGE_VERSION();

  int GL_VENDOR();

  int GL_RENDERER();

  int GL_MAX_VERTEX_ATTRIBS();

  int GL_MAX_3D_TEXTURE_SIZE();

  int GL_UNIFORM_BUFFER();

  // Non-core enums currently in use by CoreTexture2D
  int GL_BITMAP();

  int GL_COLOR_INDEX();

  int GL_COMPRESSED_ALPHA();

  int GL_COMPRESSED_LUMINANCE();

  int GL_COMPRESSED_LUMINANCE_ALPHA();
  // --------------------------------------------- //

  // OpenGL methods
  void glBindTexture(int target, int texture);

  void glBlendFunc(int sfactor, int dfactor);

  void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha);

  void glClear(int mask);

  void glClearColor(float red, float green, float blue, float alpha);

  void glDeleteTextures(int n, IntBuffer textures);

  void glDisable(int cap);

  void glDrawArrays(int mode, int first, int count);

  void glDrawElements(int mode, int count, int type, int indices);

  void glEnable(int cap);

  void glGenTextures(int n, IntBuffer textures);

  void glPixelStorei(int param, int n);

  void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixelBuffer);

  void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixelBuffer);

  int glGetError();

  void glGetIntegerv(int pname, int[] params, int offset);

  void glGetIntegerv(int pname, IntBuffer params);

  int glGetInteger(int pname);

  String glGetString(int pname);

  boolean glIsEnabled(int cap);

  void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           ByteBuffer pixels);

  void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           DoubleBuffer pixels);

  void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           FloatBuffer pixels);

  void glTexImage2D(int arget,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           IntBuffer pixels);

  void glTexImage2D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int border,
                           int format,
                           int type,
                           ShortBuffer pixels);

  void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           ByteBuffer pixels);

  void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           DoubleBuffer pixels);

  void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           FloatBuffer pixels);

  void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           IntBuffer pixels);

  void glTexImage3D(int target,
                           int level,
                           int internalformat,
                           int width,
                           int height,
                           int depth,
                           int border,
                           int format,
                           int type,
                           ShortBuffer pixels);

  void glTexParameterf(int target, int pname, float param);

  void glTexParameteri(int target, int pname, int param);

  void glTexSubImage2D(int target,
                              int level,
                              int xoffset,
                              int yoffset,
                              int width,
                              int height,
                              int format,
                              int type,
                              ByteBuffer pixels);

  void glTexBuffer(int arg0, int arg1, int arg2);

  void glViewport(int x, int y, int width, int height);

  void glActiveTexture(int texture);

  void glAttachShader(int program, int shader);

  void glBindAttribLocation(int program, int index, String name);

  void glBindBuffer(int target, int buffer);

  void glBindVertexArray(int array);

  void glVertexAttribDivisor(int index, int divisor);

  void glBufferData(int target, IntBuffer data, int usage);

  void glBufferData(int target, FloatBuffer data, int usage);

  void glBufferData(int target, ShortBuffer data, int usage);

  void glCompileShader(int shader);

  int glCreateProgram();

  int glCreateShader(int type);

  void glDeleteBuffers(int n, IntBuffer buffers);

  void glDeleteVertexArrays(int n, IntBuffer arrays);

  void glDrawArraysInstanced(int mode, int first, int count, int primcount);

  void glEnableVertexAttribArray(int index);

  void glDisableVertexAttribArray(int index);

  void glGenBuffers(int n, IntBuffer buffers);

  void glGenerateMipmap(int target);

  void glGenFramebuffers(int n, IntBuffer frameBuffs);

  void glGenVertexArrays(int n, IntBuffer arrays);

  int glGetAttribLocation(int program, String name);

  void glGetProgramiv(int program, int pname, IntBuffer params);

  String glGetProgramInfoLog(int program);

  void glGetShaderiv(int shader, int pname, IntBuffer params);

  String glGetShaderInfoLog(int shader);

  int glGetUniformLocation(int program, String name);

  void glGetUniformIndices(int program, String[] uniformNames, IntBuffer indexBuffer);

  void glGetActiveUniforms(int program, int uniformCount, IntBuffer indices, int pname, IntBuffer params);

  int glGetUniformBlockIndex(int program, String name);

  void glLinkProgram(int program);

  ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer);

  void glPrimitiveRestartIndex(int index);

  void glShaderSource(int shader, String string);

  void glUniform1(int location, FloatBuffer values);

  void glUniform1f(int location, float v0);

  void glUniform2f(int location, float v0, float v1);

  void glUniform3f(int location, float v0, float v1, float v2);

  void glUniform4f(int location, float v0, float v1, float v2, float v3);

  void glUniform1fv(int location, FloatBuffer ubuff);

  void glUniform2fv(int location, FloatBuffer ubuff);

  void glUniform3fv(int location, FloatBuffer ubuff);

  void glUniform4fv(int location, FloatBuffer ubuff);

  void glUniform1i(int location, int v0);

  void glUniform2i(int location, int v0, int v1);

  void glUniform3i(int location, int v0, int v1, int v2);

  void glUniform4i(int location, int v0, int v1, int v2, int v3);

  void glUniform1iv(int location, IntBuffer ubuff);

  void glUniform2iv(int location, IntBuffer ubuff);

  void glUniform3iv(int location, IntBuffer ubuff);

  void glUniform4iv(int location, IntBuffer ubuff);

  void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix2x3(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix2x4(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix3x2(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix3x4(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix4x2(int location, boolean transpose, FloatBuffer matrices);

  void glUniformMatrix4x3(int location, boolean transpose, FloatBuffer matrices);

  void glUniformBlockBinding(int prog, int blockIndex, int blockBinding);

  boolean glUnmapBuffer(int target);

  void glUseProgram(int program);

  void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long offset);

  void glVertexAttribIPointer(int index, int size, int type, int stride, int buffer);

  int glCheckFramebufferStatus(int target);

  void glBindFramebuffer(int target, int fbo);

  void glDeleteFramebuffers(int fboCount, IntBuffer fboIds);

  void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

  void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer);

  void glDrawBuffer(int mode);

  void glGenRenderBuffers(int buffCount, IntBuffer buffer);

  void glBindRenderbuffer(int target, int renderBuffer);

  void glRenderbufferStorage(int target, int internalFormat, int width, int height);

  void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer);

  void glBindBufferBase(int target, int bindingPoint, int id);

  void glPointSize(int psize);

  void glBlendEquationSeparate(int e1, int e2);

  void checkGLError();

  void checkGLError(String msg);

  void checkGLError(String msg, Object...args);

  void checkGLError(boolean throwException, String msg, Object...args);

  void setErrorChecksEnabled(boolean enabled);

  CoreUtil getUtil();
}
