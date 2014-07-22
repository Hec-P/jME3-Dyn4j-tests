/*
 * Copyright (c) 2009-2014 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.physics.dyn4j;

import org.dyn4j.Epsilon;
import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.MouseJoint;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.physics.dyn4j.tests.GeometryBuilder;
import com.jme3.physics.dyn4j.tests.PhysicObjectBuilder;
import com.jme3.scene.Node;

/**
 * 
 * @author H
 */
public abstract class AbstractDyn4jTest extends SimpleApplication {

    private static final String ACTION_ENABLE_DEBUG = "enableDebug";
    private static final String ACTION_ENABLE_PHYSIC = "enablePhysic";

    private static final String ACTION_SELECT_GEOM = "selectGeometry";

    protected Dyn4jAppState dyn4jAppState = null;
    private boolean debugEnabled = true;
    private boolean physicEnabled = false;

    protected GeometryBuilder geometryBuilder = null;
    protected PhysicObjectBuilder physicObjectBuilder = null;

    protected Node staticObjects = new Node("Static Objects");
    protected Node dynamicObjects = new Node("Dynamic Objects");

    protected Vector3f contactPoint = null;
    private MouseJoint mouseJoint = null;

    @Override
    public void simpleInitApp() {
        // Initialize Ddn4jAppState.
        // Important! You can define the world's bounds, so objects that go out of world's bound are deactivated in
        // order to improve calculations performance.
        this.dyn4jAppState = new Dyn4jAppState(new AxisAlignedBounds(30, 30));
        this.dyn4jAppState.setDebugEnabled(this.debugEnabled);
        this.dyn4jAppState.setEnabled(this.physicEnabled);
        this.stateManager.attach(this.dyn4jAppState);

        setDisplayStatView(false);

        // Initialize builder classes.
        this.physicObjectBuilder = new PhysicObjectBuilder();
        this.geometryBuilder = new GeometryBuilder(this.assetManager, this.physicObjectBuilder);

        this.flyCam.setEnabled(false);
        // this.inputManager.setCursorVisible(false);
        this.inputManager.setMouseCursor((JmeCursor) this.assetManager.loadAsset("Textures/Cursors/Green-cursor.cur"));
        // Change flyCamera move speed.
        // getFlyByCamera().setMoveSpeed(10);
        // getCamera().setLocation(new Vector3f(0, 10, 40));

        this.rootNode.attachChild(this.staticObjects);
        this.rootNode.attachChild(this.dynamicObjects);

        initKeys();

        simpleInit();
    }

    @Override
    public void simpleUpdate(final float tpf) {
        if (this.mouseJoint != null) {
            final Vector3f pos = this.contactPoint.clone();
            final Vector3f localPos = pos.subtract(this.cam.getLocation());
            final float dist = this.cam.getDirection().dot(localPos);

            final Vector2f cursorPos2f = this.inputManager.getCursorPosition();
            final Vector3f cursorPos3f = this.cam.getWorldCoordinates(cursorPos2f, this.cam.getViewToProjectionZ(dist))
                    .clone();

            this.mouseJoint.setTarget(Converter.toVector2(cursorPos3f));

        }
    }

    protected abstract void simpleInit();

    protected void setDebugEnabled(final boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    protected void setPhysicEnabled(final boolean physicEnabled) {
        this.physicEnabled = physicEnabled;
    }

    protected Body createFloor(final float width, final float height, final float posX, final float posY) {
        return createFloor(width, height, posX, posY, 0);
    }

    protected Body createFloor(final float width, final float height, final float posX, final float posY,
            final float rotation) {
        this.staticObjects.attachChild(this.geometryBuilder.createFloor(width / 2, height / 2, posX, posY, rotation));
        final Body floorBody = this.physicObjectBuilder.createFloor(width, height, posX, posY, rotation);
        this.dyn4jAppState.getPhysicsSpace().addBody(floorBody);
        return floorBody;
    }

    private void initKeys() {
        this.inputManager.addMapping(ACTION_ENABLE_DEBUG, new KeyTrigger(KeyInput.KEY_1));
        this.inputManager.addMapping(ACTION_ENABLE_PHYSIC, new KeyTrigger(KeyInput.KEY_2));

        this.inputManager.addMapping(ACTION_SELECT_GEOM, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        this.inputManager.addListener(this.actionListener, ACTION_ENABLE_DEBUG, ACTION_ENABLE_PHYSIC,
                ACTION_SELECT_GEOM);
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(final String name, final boolean keyPressed, final float tpf) {
            if (name.equals(ACTION_ENABLE_DEBUG) && !keyPressed) {
                AbstractDyn4jTest.this.debugEnabled = !AbstractDyn4jTest.this.debugEnabled;
                AbstractDyn4jTest.this.dyn4jAppState.setDebugEnabled(AbstractDyn4jTest.this.debugEnabled);
            }
            if (name.equals(ACTION_ENABLE_PHYSIC) && !keyPressed) {
                AbstractDyn4jTest.this.physicEnabled = !AbstractDyn4jTest.this.physicEnabled;
                AbstractDyn4jTest.this.dyn4jAppState.setEnabled(AbstractDyn4jTest.this.physicEnabled);
            }
            if (name.equals(ACTION_SELECT_GEOM)) {

                if (keyPressed) {
                    final Vector2f cursorPos2f = AbstractDyn4jTest.this.inputManager.getCursorPosition();
                    final Vector3f cursorPos3f = AbstractDyn4jTest.this.cam.getWorldCoordinates(cursorPos2f, 0f);
                    final Vector3f dir = AbstractDyn4jTest.this.cam.getWorldCoordinates(cursorPos2f, 1f)
                            .subtractLocal(cursorPos3f).normalizeLocal();

                    final CollisionResults results = new CollisionResults();
                    // Aim the ray from cursor loc to cam direction.
                    final Ray ray = new Ray(cursorPos3f, dir);

                    AbstractDyn4jTest.this.dynamicObjects.collideWith(ray, results);

                    // Check for collision
                    if (results.size() > 0) {
                        // Collision detected. Get Contact Point from closest object
                        final CollisionResult closest = results.getClosestCollision();

                        AbstractDyn4jTest.this.contactPoint = closest.getContactPoint();

                        // Create MouseJoint
                        final Dyn4jBodyControl control = closest.getGeometry().getControl(Dyn4jBodyControl.class);
                        if (control != null) {
                            final Body body = control.getBody();
                            double mass = body.getMass().getMass();
                            if (mass <= Epsilon.E) {
                                // if the mass is zero, attempt to use the inertia
                                mass = body.getMass().getInertia();
                            }
                            AbstractDyn4jTest.this.mouseJoint = new MouseJoint(body,
                                    Converter.toVector2(AbstractDyn4jTest.this.contactPoint), 4, 0.7, 10000 * mass);
                            AbstractDyn4jTest.this.dyn4jAppState.getPhysicsSpace().addJoint(
                                    AbstractDyn4jTest.this.mouseJoint);

                        } else {
                            // No dynamic body select: Throw a warning
                            System.out.println("No dynamic object detected on dynamicObjects Node: Object name = "
                                    + closest.getGeometry().getName() + " . Object Position = "
                                    + closest.getGeometry().getWorldTranslation());
                        }
                    }

                } else {
                    if (AbstractDyn4jTest.this.contactPoint != null) {
                        AbstractDyn4jTest.this.contactPoint = null;
                        AbstractDyn4jTest.this.dyn4jAppState.getPhysicsSpace().removeJoint(
                                AbstractDyn4jTest.this.mouseJoint);
                        AbstractDyn4jTest.this.mouseJoint = null;
                    }
                }

            }
        }
    };

}