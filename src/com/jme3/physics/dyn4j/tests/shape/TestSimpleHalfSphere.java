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
import org.dyn4j.geometry.HalfEllipse;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.scene.Spatial;

/**
 * 
 * @author H
 */
public class TestSimpleHalfSphere extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestSimpleHalfSphere().start();
    }

    @Override
    protected void simpleInit() {
        getCamera().setLocation(new Vector3f(0, 0, 15));

        // Create floor.
        createFloor(15, 1, 0, -4);

        // Create half ellipse.
        final Spatial halfEllipseGeom = this.geometryBuilder.createHalfEllipse(2, 2, -2, 4);
        this.rootNode.attachChild(halfEllipseGeom);

        // Create half ellipse physic object for the box.
        final HalfEllipse halfEllipseShape = new HalfEllipse(2.0, 2.0);
        final Body halfEllipsePhysic = new Body();
        halfEllipsePhysic.addFixture(halfEllipseShape);

        // Important!: Always call setMass in order to compute object's mass.
        halfEllipsePhysic.setMass();

        halfEllipsePhysic.translate(-2.0, 4.0);
        halfEllipsePhysic.rotateAboutCenter(FastMath.HALF_PI);

        // Setting velocity.
        halfEllipsePhysic.getLinearVelocity().set(5.0, 0.0);

        // Add control to halfEllipseGeom.
        halfEllipseGeom.addControl(new Dyn4jBodyControl(halfEllipsePhysic));

        this.dyn4jAppState.getPhysicsSpace().addBody(halfEllipsePhysic);
    }

}
