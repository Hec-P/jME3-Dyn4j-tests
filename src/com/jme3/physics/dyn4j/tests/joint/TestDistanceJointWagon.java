package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestDistanceJointWagon extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestDistanceJointWagon().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(20, 1, 0, 0);

        // Create some slopes
        createFloor(9, .5f, -3, 7, (float) Math.toRadians(-20));
        createFloor(7, .5f, 3, 4, (float) Math.toRadians(20));
        createFloor(3, .2f, 5, .8f, (float) Math.toRadians(30));
        createFloor(3, .2f, -5, .8f, (float) Math.toRadians(-30));

        // create two circles (the wheels)
        final Body wheel1 = this.geometryBuilder.createSphereWithPhysic(this.dynamicObjects, this.dyn4jAppState, .5f,
                -1.5f, 7.5f);
        final Body wheel2 = this.geometryBuilder.createSphereWithPhysic(this.dynamicObjects, this.dyn4jAppState, .5f,
                -2.9f, 8);

        // Create DistanceJoint
        final Vector2 p1 = wheel1.getWorldCenter().copy();
        final Vector2 p2 = wheel2.getWorldCenter().copy();
        final DistanceJoint distanceJoint = new DistanceJoint(wheel1, wheel2, p1, p2);
        distanceJoint.setCollisionAllowed(true);
        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
