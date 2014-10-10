/**
 * Copyright (c) 2013, Jens Hohmuth 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met: 
 * 
 *  * Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl.examples;

import org.junit.Test;

import de.lessvoid.coregl.*;
import de.lessvoid.coregl.CoreVAO.FloatType;
import de.lessvoid.coregl.CoreVBO.DataType;
import de.lessvoid.coregl.CoreVBO.UsageType;
import de.lessvoid.coregl.examples.spi.CoreExample;
import de.lessvoid.coregl.jogl.*;
import de.lessvoid.coregl.lwjgl.*;
import de.lessvoid.coregl.spi.*;

public class BackgroundMain implements CoreExample {
	
  private CoreShader shader;
  private long startTime = System.currentTimeMillis();
  private CoreRender coreRender;
  
	@Override
	public void init(final CoreGL gl) {
		coreRender = CoreRender.createCoreRender(gl);
    shader = CoreShader.createShaderWithVertexAttributes(gl, "vVertex");
    shader.vertexShader("background/background.vs");
    shader.fragmentShader("background/background.fs");
    shader.link();

    CoreVAO vao = CoreVAO.createCoreVAO(gl);
    vao.bind();

    CoreVBO.createCoreVBO(gl, DataType.FLOAT, UsageType.STATIC_DRAW, new Float[] {
        -1.0f, -1.0f,
        -1.0f,  1.0f,
         1.0f, -1.0f,
         1.0f,  1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttribute(0);
    vao.vertexAttribPointer(0, 2, FloatType.FLOAT, 2, 0);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();
	}
  
	@Override
	public boolean render(final CoreGL gl, final float deltaTime) {
    gl.glClearColor(.1f, .1f, .3f, 0.f);
    gl.glClear(gl.GL_COLOR_BUFFER_BIT());

    float time = (System.currentTimeMillis() - startTime) / 1000.f;
    shader.setUniformf("time", time);
    shader.setUniformf("resolution", 1024.f, 768.f);

    // render all the data in the currently active vao using triangle strips
    coreRender.renderTriangleStrip(4);
    return false;
	}
	
	@Override
	@Test
	public void runJogl() {
		CoreGL gl = new JoglCoreGL();
		CoreSetup setup = new CoreSetupJogl(gl);
		setup.initializeLogging(); // optional to get jdk14 to better format the log
		try {
			setup.initialize("Hello JOGL Core GL", 1024, 768);
			
			// init PrintFrames
			PrintFrames printFrames = new PrintFrames(setup);
			Thread t = new Thread(printFrames);
			t.setDaemon(true);
			t.start();
			// -----
			
			setup.renderLoop(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Test
	public void runLwjgl() {
		CoreGL gl = new LwjglCoreGL();
		CoreSetup setup = new CoreSetupLwjgl(gl);
		setup.initializeLogging(); // optional to get jdk14 to better format the log
		try {
			setup.initialize("Hello LWJGL Core GL", 1024, 768);
			
			// init PrintFrames
			PrintFrames printFrames = new PrintFrames(setup);
			Thread t = new Thread(printFrames);
			t.setDaemon(true);
			t.start();
			// -----
			
			setup.renderLoop(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public static void main(final String[] args) throws Exception {
    CoreExample backgroundExample = new BackgroundMain();
    ExampleMain.runExample(backgroundExample, args);
  }
  
  private class PrintFrames implements Runnable {
  	
  	final CoreSetup setup;
  	
  	volatile boolean stop = false;
  	
  	PrintFrames(CoreSetup setup) {
  		this.setup = setup;
  	}

		@Override
		public void run() {
			long now, last = 0;
			while (!stop) {
				now = System.currentTimeMillis();
				if (now - last > 1000) {
					System.out.println(setup.getFPS());
					last = now;
				}
			}
		}
  }
}