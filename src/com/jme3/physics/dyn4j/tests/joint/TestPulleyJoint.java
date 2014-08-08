package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.PulleyJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestPulleyJoint extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestPulleyJoint().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        final float posX = 2;
        final float posY = 3;
        final float h = 0.5f;
        final float l = 3;

        // create two boxes
        final Body box1 = this.geometryBuilder
                .createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, -posX, posY);
        box1.getFixture(0).setDensity(5.0);
        final Body box2 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, posX, posY);
        box2.getFixture(0).setDensity(5.0);

        // compute the joint points
        final Vector2 bodyAnchor1 = new Vector2(-posX, posY + h);
        final Vector2 bodyAnchor2 = new Vector2(posX, posY + h);
        final Vector2 pulleyAnchor1 = new Vector2(-posX, posY + h + l);
        final Vector2 pulleyAnchor2 = new Vector2(posX, posY + h + l);

        // create the joint
        final PulleyJoint pulleyJoint = new PulleyJoint(box1, box2, pulleyAnchor1, pulleyAnchor2, bodyAnchor1,
                bodyAnchor2);
        // emulate a block-and-tackle
        pulleyJoint.setRatio(2.0);
        // allow them to collide
        pulleyJoint.setCollisionAllowed(true);

        this.dyn4jAppState.getPhysicsSpace().addJoint(pulleyJoint);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
