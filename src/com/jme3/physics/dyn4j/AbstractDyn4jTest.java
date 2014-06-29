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

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.Body;

import com.jme3.app.SimpleApplication;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.physics.dyn4j.tests.GeometryBuilder;
import com.jme3.physics.dyn4j.tests.PhysicObjectBuilder;

/**
 * 
 * @author H
 */
public abstract class AbstractDyn4jTest extends SimpleApplication {

    private static final String ACTION_ENABLE_DEBUG = "enableDebug";
    private static final String ACTION_ENABLE_PHYSIC = "enablePhysic";

    protected Dyn4jAppState dyn4jAppState = null;
    private boolean debugEnabled = true;
    private boolean physicEnabled = false;

    protected GeometryBuilder geometryBuilder = null;
    protected PhysicObjectBuilder physicObjectBuilder = null;

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
        }
    };

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

        initKeys();

        simpleInit();
    }

    protected abstract void simpleInit();

    public void setDebugEnabled(final boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public void setPhysicEnabled(final boolean physicEnabled) {
        this.physicEnabled = physicEnabled;
    }

    private void initKeys() {
        this.inputManager.addMapping(ACTION_ENABLE_DEBUG, new KeyTrigger(KeyInput.KEY_1));
        this.inputManager.addMapping(ACTION_ENABLE_PHYSIC, new KeyTrigger(KeyInput.KEY_2));

        this.inputManager.addListener(this.actionListener, ACTION_ENABLE_DEBUG, ACTION_ENABLE_PHYSIC);
    }

    protected Body createFloor(final float width, final float height, final float posX, final float posY) {
        return createFloor(width, height, posX, posY, 0);
    }

    protected Body createFloor(final float width, final float height, final float posX, final float posY,
            final float rotation) {
        this.rootNode.attachChild(this.geometryBuilder.createFloor(width / 2, height / 2, posX, posY, rotation));
        final Body floorBody = this.physicObjectBuilder.createFloor(width, height, posX, posY, rotation);
        this.dyn4jAppState.getPhysicsSpace().addBody(floorBody);
        return floorBody;
    }

}
