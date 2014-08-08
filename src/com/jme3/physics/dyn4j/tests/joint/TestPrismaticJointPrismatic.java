package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.PrismaticJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;
import com.jme3.physics.dyn4j.Converter;

public class TestPrismaticJointPrismatic extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestPrismaticJointPrismatic().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        // create two boxes
        final Body box1 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 5);
        box1.getLinearVelocity().set(2, 0);
        final Body box2 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 4);

        final Vector2 anchor = new Vector2(0, 4.5);

        // Set Y as allowed translation axis
        final Vector2 axis = Converter.toVector2(Vector3f.UNIT_Y);
        // final Vector2 axis = new Vector2(0, -1);

        final PrismaticJoint prismaticJoint = new PrismaticJoint(box1, box2, anchor, axis);
        prismaticJoint.setLimitsEnabled(0, 2.5);
        // prismaticJoint.setCollisionAllowed(true);

        this.dyn4jAppState.getPhysicsSpace().addJoint(prismaticJoint);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
