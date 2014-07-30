package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestDistanceJointCollision extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestDistanceJointCollision().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        // create two boxes
        final Body box1 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 2, 7.6f);
        final Body box2 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 2, 6.4f);

        // Create DistanceJoint with collisionAllowed = true
        final Vector2 p1 = box1.getWorldCenter().copy().add(0, -0.4f);
        final Vector2 p2 = box2.getWorldCenter().copy().add(0, 0.4);
        final DistanceJoint distanceJoint1 = new DistanceJoint(box1, box2, p1, p2);
        distanceJoint1.setCollisionAllowed(true);
        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint1);

        // create two boxes
        final Body box3 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, -2, 7.6f);
        final Body box4 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, -2, 6.4f);

        // Create DistanceJoint with collisionAllowed = false (collisionAllowed = false by default)
        final Vector2 p3 = box3.getWorldCenter().copy().add(0, -0.4f);
        final Vector2 p4 = box4.getWorldCenter().copy().add(0, 0.4);
        final DistanceJoint distanceJoint2 = new DistanceJoint(box3, box4, p3, p4);
        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint2);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
