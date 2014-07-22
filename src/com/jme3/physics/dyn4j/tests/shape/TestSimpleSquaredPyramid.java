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
package com.jme3.physics.dyn4j.tests.shape;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.scene.Spatial;

/**
 * 
 * @author H
 */
public class TestSimpleSquaredPyramid extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestSimpleSquaredPyramid().start();
    }

    @Override
    protected void simpleInit() {
        getCamera().setLocation(new Vector3f(0, 0, 15));

        // Create floor.
        createFloor(15, 1, 0, -4);

        // Create squared pyramid.
        final Spatial squaredPyramidGeom = this.geometryBuilder.createSquaredPyramid(.7f, 1, -2, 4);
        this.dynamicObjects.attachChild(squaredPyramidGeom);

        // Create a triangle physic object for squared pyramid
        final Triangle triangleShape = new Triangle(new Vector2(0.0, 0.5), new Vector2(-0.5, -0.5), new Vector2(0.5,
                -0.5));
        final Body trianglePhysic = new Body();
        trianglePhysic.addFixture(triangleShape);
        trianglePhysic.setMass();

        trianglePhysic.translate(-1.0, 2.0);
        trianglePhysic.rotate(FastMath.QUARTER_PI);

        // Setting velocity.
        trianglePhysic.getLinearVelocity().set(5.0, 0.0);

        // Add control to squaredPyramidGeom.
        squaredPyramidGeom.addControl(new Dyn4jBodyControl(trianglePhysic));

        this.dyn4jAppState.getPhysicsSpace().addBody(trianglePhysic);
    }

}
