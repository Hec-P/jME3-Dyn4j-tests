/*
 * Copyright (c) 2009-2012 jMonkeyEngine
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

package com.jme3.physics.dyn4j.tests.miscellaneous;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * Sample 8 - how to let the user pick (select) objects in the scene using the mouse or key presses. Can be used for
 * shooting, opening doors, etc.
 */
public class TestDragAndDrop extends SimpleApplication {

    public static void main(final String[] args) {
        final TestDragAndDrop app = new TestDragAndDrop();
        app.start();
    }

    private Node shootables = null;
    private Geometry selectedGeom = null;
    private final Vector3f delta = new Vector3f();

    @Override
    public void simpleInitApp() {
        this.flyCam.setEnabled(false);
        this.inputManager.setCursorVisible(true);

        initKeys();

        this.shootables = new Node("Shootables");
        this.rootNode.attachChild(this.shootables);
        this.shootables.attachChild(makeCube("cube", 1, 1, 1));
        this.rootNode.attachChild(makeFloor());
    }

    @Override
    public void simpleUpdate(final float tpf) {
        if (this.selectedGeom != null) {
            final Vector3f pos = this.selectedGeom.getWorldTranslation().clone();
            final Vector3f localPos = pos.subtract(TestDragAndDrop.this.cam.getLocation());
            final float dist = this.cam.getDirection().dot(localPos);

            final Vector2f click2d = this.inputManager.getCursorPosition();
            final Vector3f click3d = this.cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y),
                    this.cam.getViewToProjectionZ(dist)).clone();

            // this.delta = this.cam.getWorldCoordinates(new Vector2f(this.delta.x, this.delta.y),
            // this.cam.getViewToProjectionZ(dist)).clone();
            //
            // click3d.x += this.delta.x;
            // click3d.y += this.delta.y;
            // TestDragAndDrop.this.selectedGeom.setLocalTranslation(click3d);
            TestDragAndDrop.this.selectedGeom.setLocalTranslation(click3d.subtract(this.delta));
        }
    }

    private void initKeys() {
        this.inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        this.inputManager.addListener(this.actionListener, "Shoot");
    }

    private final ActionListener actionListener = new ActionListener() {

        @Override
        public void onAction(final String name, final boolean keyPressed, final float tpf) {
            if (name.equals("Shoot")) {

                if (keyPressed) {
                    // System.out.println("Dragging - pressed");

                    final Vector2f click2d = TestDragAndDrop.this.inputManager.getCursorPosition();
                    final Vector3f click3d = TestDragAndDrop.this.cam.getWorldCoordinates(
                            new Vector2f(click2d.x, click2d.y), 0f).clone();
                    final Vector3f dir = TestDragAndDrop.this.cam
                            .getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d)
                            .normalizeLocal();

                    // 1. Reset results list.
                    final CollisionResults results = new CollisionResults();
                    // 2. Aim the ray from cam loc to cam direction.
                    final Ray ray = new Ray(click3d, dir);

                    // 3. Collect intersections between Ray and Shootables in results list.
                    TestDragAndDrop.this.shootables.collideWith(ray, results);

                    // 5. Use the results (we mark the hit object)
                    if (results.size() > 0) {
                        // System.out.println("Dragging - object");
                        // The closest collision point is what was truly hit:
                        final CollisionResult closest = results.getClosestCollision();

                        // Let's interact - we mark the hit with a red dot.
                        TestDragAndDrop.this.selectedGeom = closest.getGeometry();
                        final Vector3f geomCenterPosition = TestDragAndDrop.this.selectedGeom.getWorldTranslation();
                        final Vector3f contactPoint = closest.getContactPoint();
                        System.out.println("Contact Point :" + contactPoint);
                        System.out.println("Geom Center :" + geomCenterPosition);
                        TestDragAndDrop.this.delta.x = contactPoint.x - geomCenterPosition.x;
                        TestDragAndDrop.this.delta.y = contactPoint.y - geomCenterPosition.y;
                        TestDragAndDrop.this.delta.z = 0;
                    } else {
                        // System.out.println("Dragging - no object");
                        // // No hits? Then remove the red mark.
                        TestDragAndDrop.this.selectedGeom = null;
                    }
                } else {
                    // System.out.println("Dragging - released");

                    TestDragAndDrop.this.selectedGeom = null;
                }
            }
        }
    };

    protected Geometry makeCube(final String name, final float x, final float y, final float z) {
        final Box box = new Box(1, 1, 1);
        final Geometry cube = new Geometry(name, box);
        cube.setLocalTranslation(x, y, z);
        final Material mat1 = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.randomColor());
        cube.setMaterial(mat1);
        return cube;
    }

    protected Geometry makeFloor() {
        final Box box = new Box(15, .2f, 15);
        final Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -4, -5);
        final Material mat1 = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat1);
        return floor;
    }

}
