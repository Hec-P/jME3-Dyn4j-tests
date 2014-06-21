package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestDistanceJoint extends AbstractDyn4jTest {

    private Body floorBody;

    public static void main(final String[] args) {
        final TestDistanceJoint app = new TestDistanceJoint();
        app.setPhysicEnabled(false);
        app.start();
    }

    @Override
    protected void simpleInit() {
        getCamera().setLocation(new Vector3f(0, 5, 20));

        this.floorBody = createFloor(15, 1, 0, 5);

        createDistanceJointTest1();
        createDistanceJointTest2();
        createDistanceJointTest3();
        createDistanceJointTest4();
    }

    private void createDistanceJointTest1() {
        // Create box.
        final Body boxPhysic = this.geometryBuilder.createBoxWithPhysic(this.rootNode, this.dyn4jAppState, -10, 1.5f);

        final Vector2 anchor1 = this.floorBody.getWorldCenter().add(-7, 0);
        final Vector2 anchor2 = boxPhysic.getWorldCenter().add(-.5d, .5d);

        final DistanceJoint distanceJoint = new DistanceJoint(this.floorBody, boxPhysic, anchor1, anchor2);
        distanceJoint.setCollisionAllowed(true);

        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint);
    }

    private void createDistanceJointTest2() {
        // Create box.
        final Body boxPhysic = this.geometryBuilder.createBoxWithPhysic(this.rootNode, this.dyn4jAppState, 0, 2.5f);

        final Vector2 anchor1 = this.floorBody.getWorldCenter().add(-3, 0);
        final Vector2 anchor2 = boxPhysic.getWorldCenter();

        final DistanceJoint distanceJoint = new DistanceJoint(this.floorBody, boxPhysic, anchor1, anchor2);
        distanceJoint.setCollisionAllowed(true);

        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint);
    }

    private void createDistanceJointTest3() {
        // Create box.
        final Body boxPhysic = this.geometryBuilder.createBoxWithPhysic(this.rootNode, this.dyn4jAppState, 3, 2.5f);

        final Vector2 anchor1 = this.floorBody.getWorldCenter().add(3, 0);
        final Vector2 anchor2 = boxPhysic.getWorldCenter();

        final DistanceJoint distanceJoint = new DistanceJoint(this.floorBody, boxPhysic, anchor1, anchor2);
        distanceJoint.setCollisionAllowed(true);
        distanceJoint.setFrequency(.5f);
        distanceJoint.setDampingRatio(0.01);

        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint);
    }

    private void createDistanceJointTest4() {
        // Create box.
        final Body boxPhysic1 = this.geometryBuilder.createBoxWithPhysic(this.rootNode, this.dyn4jAppState, 3, 12);
        final Body boxPhysic2 = this.geometryBuilder.createBoxWithPhysic(this.rootNode, this.dyn4jAppState, 3, 10);

        final Vector2 anchor1 = boxPhysic1.getWorldCenter();
        final Vector2 anchor2 = boxPhysic2.getWorldCenter();

        final DistanceJoint distanceJoint = new DistanceJoint(boxPhysic1, boxPhysic2, anchor1, anchor2);

        // Change distance
        distanceJoint.setCollisionAllowed(true);
        distanceJoint.setFrequency(1.0);
        distanceJoint.setDampingRatio(0.1);

        this.dyn4jAppState.getPhysicsSpace().addJoint(distanceJoint);
    }

}
