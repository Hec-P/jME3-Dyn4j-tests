package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestDistanceJointSpringDamper extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestDistanceJointSpringDamper().start();
    }

    @Override
    protected void simpleInit() {
        createFloor(15, 1, 0, -4);

        final Rectangle r = new Rectangle(3.0, 0.5);
        final BodyFixture fr = new BodyFixture(r);
        fr.setDensity(0.2);
        // create a reusable circle
        final Circle c = new Circle(0.25);
        final BodyFixture fc = new BodyFixture(c);
        fc.setDensity(0.5);
        fc.setFriction(0.5);

        final Body body = new Body();
        body.addFixture(fr);
        body.setMass();
        body.translate(0, 4.25);

        final Body wheel1 = new Body();
        wheel1.addFixture(fc);
        wheel1.setMass();
        wheel1.translate(-1.0, 3.6);

        final Body wheel2 = new Body();
        wheel2.addFixture(fc);
        wheel2.setMass();
        wheel2.translate(1.0, 3.6);

        this.dyn4jAppState.getPhysicsSpace().addBody(body);
        this.dyn4jAppState.getPhysicsSpace().addBody(wheel1);
        this.dyn4jAppState.getPhysicsSpace().addBody(wheel2);

        // create a fixed distance joint between the wheels
        final Vector2 p1 = wheel1.getWorldCenter().copy();
        final Vector2 p2 = wheel2.getWorldCenter().copy();
        final Vector2 p3 = body.getWorldCenter().copy();

        // join them
        final DistanceJoint j1 = new DistanceJoint(body, wheel1, p3, p1);
        j1.setCollisionAllowed(true);
        this.dyn4jAppState.getPhysicsSpace().addJoint(j1);
        final DistanceJoint j2 = new DistanceJoint(body, wheel2, p3, p2);
        j2.setCollisionAllowed(true);
        this.dyn4jAppState.getPhysicsSpace().addJoint(j2);

        // create a spring joint for the rear wheel
        final DistanceJoint j3 = new DistanceJoint(body, wheel1, new Vector2(-1.0, 4.0), p1);
        j3.setCollisionAllowed(true);
        j3.setFrequency(8.0);
        j3.setDampingRatio(0.4);
        this.dyn4jAppState.getPhysicsSpace().addJoint(j3);

        // create a spring joint for the front wheel
        final DistanceJoint j4 = new DistanceJoint(body, wheel2, new Vector2(1.0, 4.0), p2);
        j4.setCollisionAllowed(true);
        j4.setFrequency(8.0);
        j4.setDampingRatio(0.4);
        this.dyn4jAppState.getPhysicsSpace().addJoint(j4);

    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 0, 20);
    }

}
