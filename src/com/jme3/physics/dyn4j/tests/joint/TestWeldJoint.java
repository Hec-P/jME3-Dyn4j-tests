package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestWeldJoint extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestWeldJoint().start();
    }

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        // create two boxes
        final Body box1 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 2.5f);
        box1.getLinearVelocity().set(2, 0);
        final Body box2 = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0, 1.5f);

        final WeldJoint weldJoint = new WeldJoint(box1, box2, new Vector2(0.0, 2.0));
        this.dyn4jAppState.getPhysicsSpace().addJoint(weldJoint);
    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
