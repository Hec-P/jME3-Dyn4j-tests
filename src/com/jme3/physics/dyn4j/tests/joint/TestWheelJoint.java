package com.jme3.physics.dyn4j.tests.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.dynamics.joint.WheelJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.AbstractDyn4jTest;
import com.jme3.physics.dyn4j.Converter;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class TestWheelJoint extends AbstractDyn4jTest {

	public static void main(final String[] args) {
		new TestWheelJoint().start();
	}

	@Override
	protected void simpleInit() {
		// Create floor
		createFloor(15, 1, 0, 0);

		// Create rectangle physic object.
		final Rectangle boxShape = Geometry.createRectangle(3.0, 0.5);
		final Body boxPhysic = this.physicObjectBuilder.createBody(boxShape,
				-3, 4.75);

		// Create box.
		final Spatial boxGeom = this.geometryBuilder.createBox(3f/2f, 0.5f/2f, 1, -3,
				4.75f);
		this.dynamicObjects.attachChild(boxGeom);

		// Add control to boxGeom.
		boxGeom.addControl(new Dyn4jBodyControl(boxPhysic));

		this.dyn4jAppState.getPhysicsSpace().addBody(boxPhysic);

		Body wheelR = geometryBuilder.createSphereWithPhysic(dynamicObjects, dyn4jAppState, 0.25f, -4, 3.6f);
		Body wheelL = geometryBuilder.createSphereWithPhysic(dynamicObjects, dyn4jAppState, 0.25f, -2, 3.6f);
		
		final Vector2 axis = Converter.toVector2(Vector3f.UNIT_X);
		
		WheelJoint wheelJoint1 = new WheelJoint(boxPhysic, wheelR, wheelR.getWorldCenter(), axis);
		wheelJoint1.setCollisionAllowed(true);
		// setup a spring
		wheelJoint1.setFrequency(8.0);
		wheelJoint1.setDampingRatio(0.4);
		// give the car rear-wheel-drive
		wheelJoint1.setMotorEnabled(true);
		// set the speed to -180 degrees per second
		wheelJoint1.setMotorSpeed(Math.PI);
		// don't forget to set the maximum torque
		wheelJoint1.setMaximumMotorTorque(1000);
		this.dyn4jAppState.getPhysicsSpace().addJoint(wheelJoint1);
		
		WheelJoint wheelJoint2 = new WheelJoint(boxPhysic, wheelL, wheelL.getWorldCenter(), axis);
		wheelJoint2.setCollisionAllowed(true);
		// setup a spring
		wheelJoint2.setFrequency(8.0);
		wheelJoint2.setDampingRatio(0.4);
		this.dyn4jAppState.getPhysicsSpace().addJoint(wheelJoint2);
	}

	@Override
	protected Vector3f getCamInitialLocation() {
		return new Vector3f(0, 5, 20);
	}

}
