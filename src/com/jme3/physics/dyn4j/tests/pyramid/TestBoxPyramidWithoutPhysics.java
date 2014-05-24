/*
 * Copyright (c) 2009-2014 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.physics.dyn4j.tests.pyramid;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.tests.GeometryBuilder;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * 
 * @author H
 */
public class TestBoxPyramidWithoutPhysics extends SimpleApplication {

    /** The height of the pyramid. To calculate # of Bodies use this formula: # of bodies = h(h+1)/2 */
    private static final int HEIGHT = 25;

    private GeometryBuilder geometryBuilder = null;

    public static void main(final String[] args) {
        new TestBoxPyramidWithoutPhysics().start();
    }

    @Override
    public void simpleInitApp() {
        this.geometryBuilder = new GeometryBuilder(this.assetManager);

        getCamera().setLocation(new Vector3f(0, 5, 20));

        // Create floor.
        this.rootNode.attachChild(this.geometryBuilder.createFloor(7.5f, .5f, 0, 0));

        final float width = .5f;
        final float height = .5f;

        final Box box = new Box(width / 2, height / 2, width / 2);

        // the current x position
        float posX = 0;
        // the current y position
        float posY = .26f;

        // the spacing between the boxes
        final float yspacing = .01f;
        final float xspacing = .01f;

        // loop to create the rows
        for (int i = 0; i < HEIGHT; i++) {
            // the number of boxes on this row
            final int num = HEIGHT - i;

            // increment y
            posY += height + yspacing;

            // set x
            posX = -(num * (width + xspacing)) / 2 + (width + xspacing) / 2;

            // loop to create the bodies in the rows
            for (int j = 0; j < num; j++) {
                // Create box.
                final Spatial boxGeom = this.geometryBuilder.createBox(box, posX, posY);

                boxGeom.setLocalTranslation(posX, posY, 0);

                this.rootNode.attachChild(boxGeom);

                // increment x
                posX += width + xspacing;
            }
        }
    }

}
