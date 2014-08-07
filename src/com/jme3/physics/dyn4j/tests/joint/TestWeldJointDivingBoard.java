package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class TestWeldJointDivingBoard extends AbstractDyn4jTest {

    public static void main(final String[] args) {
        new TestWeldJointDivingBoard().start();
    }

    /** The length of the chain in number of bodies */
    private static final int LENGTH = 2;

    @Override
    protected void simpleInit() {
        // Create floor
        createFloor(15, 1, 0, 0);

        // create a reusable rectangle
        final float h = 0.125f;
        final float w = 1;
        final Rectangle r = Geometry.createRectangle(w, h);
        final BodyFixture f = new BodyFixture(r);
        f.setDensity(20.0);

        float posX = -LENGTH * w * 0.5f;
        final float posY = 3.5f;

        final Box box = new Box(w / 2, h / 2, .5f);

        Body boxPhysic = createFloor(w, h, posX, posY);

        Body previous = boxPhysic;

        for (int i = 0; i < LENGTH; i++) {
            posX += w;
            // create a chain link
            boxPhysic = this.physicObjectBuilder.createBody(f, posX, posY);
            this.dyn4jAppState.getPhysicsSpace().addBody(boxPhysic);

            // Create box.
            final Spatial boxGeom = this.geometryBuilder.createBox(box, posX, posY);
            this.dynamicObjects.attachChild(boxGeom);

            // Add control to boxGeom.
            boxGeom.addControl(new Dyn4jBodyControl(boxPhysic));

            // define the anchor point
            final Vector2 anchor = new Vector2(posX - w * 0.5, posY);

            // create a joint from the previous body to this body
            final WeldJoint joint = new WeldJoint(previous, boxPhysic, anchor);
            joint.setFrequency(5.0);
            joint.setDampingRatio(0.7);
            this.dyn4jAppState.getPhysicsSpace().addJoint(joint);

            previous = boxPhysic;
        }

    }

    @Override
    protected Vector3f getCamInitialLocation() {
        return new Vector3f(0, 5, 20);
    }

}
