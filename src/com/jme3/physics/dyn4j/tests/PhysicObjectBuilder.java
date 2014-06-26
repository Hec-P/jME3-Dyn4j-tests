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
package com.jme3.physics.dyn4j.tests;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

/**
 * 
 * @author H
 */
public class PhysicObjectBuilder {

    public Body createFloor(final double width, final double height, final double posX, final double posY) {
        return createFloor(width, height, posX, posY, 0);
    }

    public Body createFloor(final double width, final double height, final double posX, final double posY,
            final double rotation) {
        final Rectangle floorShape = new Rectangle(width, height);

        final Body floorPhysic = new Body();
        floorPhysic.addFixture(new BodyFixture(floorShape));
        floorPhysic.setMass(Mass.Type.INFINITE);
        floorPhysic.translate(posX, posY);

        if (rotation != 0) {
            floorPhysic.rotate(rotation, floorPhysic.getWorldCenter());
        }

        return floorPhysic;
    }

    public Body createBody(final Convex shape, final double posX, final double posY) {
        final Body bodyPhysic = new Body();
        bodyPhysic.addFixture(shape);
        bodyPhysic.setMass();
        bodyPhysic.translate(posX, posY);

        return bodyPhysic;
    }

    public Body createRectangle(final double width, final double height, final double posX, final double posY) {
        final Rectangle shape = new Rectangle(width, height);
        final Body body = createBody(shape, posX, posY);
        return body;
    }

    public Body createCircle(final double radius, final double posX, final double posY) {
        final Circle shape = new Circle(radius);
        final Body body = createBody(shape, posX, posY);
        return body;
    }

}
