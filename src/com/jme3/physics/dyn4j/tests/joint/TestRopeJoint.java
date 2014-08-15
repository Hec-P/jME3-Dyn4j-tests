package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.RopeJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestRopeJoint extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestRopeJoint().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        // create two boxes
        final Body box1 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 7.6f);
        final Body box2 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 6.4f);

        // compute the joint points
        final Vector2 p1 = box1.getWorldCenter().copy().add(0, -0.4f);
        final Vector2 p2 = box2.getWorldCenter().copy().add(0, 0.4);

        final RopeJoint ropeJoint = new RopeJoint(box1, box2, p1, p2);
        // VERY IMPORTANT FOR THIS JOINT
        // set and enable the limits
        ropeJoint.setLimitsEnabled(0.5, 1.5);
        ropeJoint.setCollisionAllowed(true);
        this.dyn4jAppState.getPhysicsSpace().addJoint(ropeJoint);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
