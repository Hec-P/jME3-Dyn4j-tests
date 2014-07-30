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
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Polygon;

import com.jme3.math.FastMath;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

/**
 * 
 * @author H
 */
public class TestSimplePolygon extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestSimplePolygon().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor.
        createFloor(15, 1, 0, -4);

        // TODO Dyn4j: Create polygon.
        // final Spatial polygonGeom = this.geometryBuilder.createPolygon(.5f, .5f, .5f, -2, 4);
        // this.dynamicObjects.attachChild(polygonGeom);

        // Create polygon physic object.
        final Polygon polygonShape = Geometry.createPolygonalCircle(7, 1.5);
        final Body polygonPhysic = new Body();
        polygonPhysic.addFixture(polygonShape);

        // Important!: Always call setMass in order to compute object's mass.
        polygonPhysic.setMass();

        polygonPhysic.translate(0.0, -4.0);
        polygonPhysic.rotate(-FastMath.PI + .4f);

        // Setting velocity.
        polygonPhysic.getLinearVelocity().set(3.0, 0.0);

        // TODO Dyn4j: Add control to polygonGeom.
        // polygonGeom.addControl(new Dyn4jBodyControl(polygonPhysic));

        this.dyn4jAppState.getPhysicsSpace().addBody(polygonPhysic);
    }

}
