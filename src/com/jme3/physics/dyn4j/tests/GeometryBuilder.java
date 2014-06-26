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
package com.jme3.physics.dyn4j.tests;

import org.dyn4j.dynamics.Body;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.Dyn4jAppState;
import com.jme3.physics.dyn4j.control.Dyn4jBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 * 
 * @author H
 */
public class GeometryBuilder {

    private AssetManager assetManager = null;
    private PhysicObjectBuilder physicObjectBuilder = null;

    public GeometryBuilder(final AssetManager assetManager, final PhysicObjectBuilder physicObjectBuilder) {
        this.assetManager = assetManager;
        this.physicObjectBuilder = physicObjectBuilder;
    }

    public Spatial createFloor(final float width, final float height, final float posX, final float posY) {
        return createFloor(width, height, posX, posY, 0);
    }

    public Spatial createFloor(final float width, final float height, final float posX, final float posY,
            final float rotation) {
        final Box floor = new Box(width, height, 1f);

        final Texture floorTexture = this.assetManager.loadTexture("Textures/grass/grass-color-1_512x512.jpg");
        // TODO La textura se ve mal, tengo que arreglarla
        // floorTexture.setWrap(Texture.WrapMode.Repeat);
        // floor.scaleTextureCoordinates(new Vector2f(width, height));

        final Material floorMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setTexture("ColorMap", floorTexture);

        final Geometry floorGeom = new Geometry("Floor", floor);
        floorGeom.move(new Vector3f(posX, posY, 0));
        floorGeom.setMaterial(floorMat);

        if (rotation != 0) {
            floorGeom.rotate(0, 0, rotation);
        }

        return floorGeom;
    }

    public Spatial createBox(final float width, final float height, final float depth, final float posX,
            final float posY) {
        final Box box = new Box(width, height, depth);

        return createBox(box, posX, posY);
    }

    public Spatial createBox(final Box box, final float posX, final float posY) {
        final Material boxMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setTexture("ColorMap", this.assetManager.loadTexture("Textures/box/box-color-2_512x512.jpg"));

        final Geometry boxGeom = new Geometry("Box", box);
        boxGeom.setLocalTranslation(new Vector3f(posX, posY, 0));
        boxGeom.setMaterial(boxMat);

        return boxGeom;
    }

    public Body createBoxWithPhysic(final Node parentNode, final Dyn4jAppState dyn4jAppState, final float posX,
            final float posY) {
        // Create box.
        final Spatial boxGeom = createBox(.5f, .5f, .5f, posX, posY);
        parentNode.attachChild(boxGeom);

        // Create rectangle physic object for the box.
        final Body boxPhysic = this.physicObjectBuilder.createRectangle(1, 1, posX, posY);

        // Add control to boxGeom.
        boxGeom.addControl(new Dyn4jBodyControl(boxPhysic));

        dyn4jAppState.getPhysicsSpace().addBody(boxPhysic);

        return boxPhysic;
    }

    public Spatial createSquaredPyramid(final float baseWidth, final float height, final float posX, final float posY) {
        final Cylinder squaredPyramid = new Cylinder(4, 4, baseWidth, 0, height, true, false);

        return createSquaredPyramid(squaredPyramid, posX, posY);
    }

    public Spatial createSquaredPyramid(final Cylinder squaredPyramid, final float posX, final float posY) {
        final Material squaredPyramidMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        squaredPyramidMat.setTexture("ColorMap",
                this.assetManager.loadTexture("Textures/concrete/concrete-color-3_512x512.jpg"));

        final Geometry squaredPyramidGeom = new Geometry("squaredPyramid", squaredPyramid);
        squaredPyramidGeom.rotate(FastMath.HALF_PI, FastMath.QUARTER_PI, 0);
        squaredPyramidGeom.setMaterial(squaredPyramidMat);

        final Node squaredPyramidNode = new Node("squaredPyramidNode");
        squaredPyramidNode.attachChild(squaredPyramidGeom);
        squaredPyramidNode.move(new Vector3f(posX, posY, 0));

        return squaredPyramidNode;
    }

    public Spatial createSphere(final float radius, final float posX, final float posY) {
        final Sphere sphere = new Sphere(16, 16, radius);

        return createSphere(sphere, posX, posY);
    }

    public Spatial createSphere(final Sphere sphere, final float posX, final float posY) {
        final Material sphereMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sphereMat.setTexture("ColorMap", this.assetManager.loadTexture("Textures/rock/rock-color-4_512x512.jpg"));

        final Geometry sphereGeom = new Geometry("Box", sphere);
        sphereGeom.move(new Vector3f(posX, posY, 0));
        sphereGeom.setMaterial(sphereMat);

        return sphereGeom;
    }

    public Body createSphereWithPhysic(final Node parentNode, final Dyn4jAppState dyn4jAppState, final float radius,
            final float posX, final float posY) {
        // Create sphere
        final Spatial sphereGeom = createSphere(radius, posX, posY);
        parentNode.attachChild(sphereGeom);

        // Create physic object
        final Body sphereBody = this.physicObjectBuilder.createCircle(radius, posX, posY);

        // Add control to sphereGeom.
        sphereGeom.addControl(new Dyn4jBodyControl(sphereBody));

        dyn4jAppState.getPhysicsSpace().addBody(sphereBody);

        return sphereBody;
    }

    public Spatial createCapsule(final float width, final float height, final float posX, final float posY) {
        final Node capsule = new Node("Capsule");

        final Material capsuleMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        capsuleMat.setTexture("ColorMap", this.assetManager.loadTexture("Textures/rock/rock-color-3_512x512.jpg"));

        final Geometry body = new Geometry("Body", new Cylinder(3, 16, width, (height - width) * 2));
        body.rotate(FastMath.HALF_PI, 0, 0);
        capsule.attachChild(body);

        final Geometry rightHalfCircle = new Geometry("rightHalfCircle", new Dome(Vector3f.ZERO, 8, 16, width, false));
        rightHalfCircle.move(0, height - width, 0);
        capsule.attachChild(rightHalfCircle);

        final Geometry lefttHalfCircle = new Geometry("lefttHalfCircle", new Dome(Vector3f.ZERO, 8, 16, width, false));
        lefttHalfCircle.move(0, -height + width, 0);
        lefttHalfCircle.rotate(0, 0, FastMath.PI);
        capsule.attachChild(lefttHalfCircle);

        capsule.move(new Vector3f(posX, posY, 0));
        capsule.setMaterial(capsuleMat);
        return capsule;
    }

    public Spatial createHalfEllipse(final float width, final float height, final float posX, final float posY) {
        final Dome dome = new Dome(Vector3f.ZERO, 8, 16, width / 2, false);

        return createHalfEllipse(dome, height, posX, posY);
    }

    public Spatial createHalfEllipse(final Dome dome, final float height, final float posX, final float posY) {
        final Material halfEllipseMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        halfEllipseMat.setTexture("ColorMap", this.assetManager.loadTexture("Textures/rock/rock-color-2_512x512.jpg"));

        final Geometry halfEllipsis = new Geometry("rightHalfCircle", dome);
        halfEllipsis.scale(1, height, 1);
        halfEllipsis.setMaterial(halfEllipseMat);
        halfEllipsis.move(posX, posY, 0);

        return halfEllipsis;
    }

    public Spatial createQuad(final float width, final float height, final float posX, final float posY) {
        final Quad quad = new Quad(width, height);

        return createQuad(quad, posX, posY);
    }

    public Spatial createQuad(final Quad quad, final float posX, final float posY) {
        final Material quadMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        quadMat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        quadMat.setTexture("ColorMap", this.assetManager.loadTexture("Textures/box/box-color-5_512x512.jpg"));

        final Geometry quadGeom = new Geometry("quadGeom", quad);
        quadGeom.setMaterial(quadMat);
        quadGeom.rotate(0, FastMath.HALF_PI, 0);

        final Node node = new Node("quadNode");
        node.attachChild(quadGeom);
        node.move(posX, posY, .5f);

        return node;
    }

}
