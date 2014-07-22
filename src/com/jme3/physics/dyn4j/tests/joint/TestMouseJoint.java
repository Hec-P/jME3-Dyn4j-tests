package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.MouseJoint;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;

public class TestMouseJoint extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestMouseJoint().start();
    }

    @Override
    protected void simpleInit() {
        getCamera().setLocation(new Vector3f(0, 5, 20));

        createFloor(15, 1, 0, 0);

        // Create box.
        final Body boxPhysic = this.geometryBuilder.createBoxWithPhysic(this.dynamicObjects, this.dyn4jAppState, 0,
                1.5f);

        final Vector2 anchor = boxPhysic.getWorldCenter().copy().add(0, .3);
        final MouseJoint mouseJoint = new MouseJoint(boxPhysic, anchor, 5, 0.3, 100);
        // pin it to a random point
        mouseJoint.setTarget(new Vector2(0, 8));

        this.dyn4jAppState.getPhysicsSpace().addJoint(mouseJoint);
    }

}
