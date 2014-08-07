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
package com.jme3.physics.dyn4j.tests.miscellaneous;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.physics.dyn4j.debug.shape.CapsuleDebug;
import com.jme3.physics.dyn4j.debug.shape.CircleDebug;
import com.jme3.physics.dyn4j.debug.shape.CrossDebug;
import com.jme3.physics.dyn4j.debug.shape.HalfEllipseDebug;
import com.jme3.physics.dyn4j.debug.shape.SliceDebug;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.BufferUtils;

/**
 * 
 * @author H
 */
public class TestDrawPhysicsShapes extends SimpleApplication {

    private Material mat;

    public static void main(final String[] args) {
        new TestDrawPhysicsShapes().start();
    }

    @Override
    public void simpleInitApp() {
        getCamera().setLocation(new Vector3f(9, 0, 25));
        this.flyCam.setMoveSpeed(10);

        this.mat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.mat.getAdditionalRenderState().setWireframe(true);
        this.mat.setColor("Color", ColorRGBA.Blue);
        this.mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);

        // ########## Line 0
        // Circle
        this.rootNode.attachChild(buildCircle(12, 1, -3));

        // Box
        final Box b = new Box(1, 1, 1);
        final Geometry geom = new Geometry("Box", b);
        geom.setMaterial(this.mat);
        this.rootNode.attachChild(geom);

        // Cylinder
        // final Cylinder cylinderShape = new Cylinder(2, 20, 2, 1);
        final Cylinder cylinderShape = new Cylinder(4, 4, 1, 0, 4, true, false);

        final Geometry cylinderGeom = new Geometry("cylinder", cylinderShape);
        cylinderGeom.setLocalTranslation(4, 0, 0);
        cylinderGeom.setMaterial(this.mat);
        this.rootNode.attachChild(cylinderGeom);

        // Polygon (mesh mode = lineLoop)
        this.rootNode.attachChild(buildPolygonMesh(Mode.LineLoop, 7));

        // Polygon (mesh mode = lines)
        this.rootNode.attachChild(buildPolygonMesh(Mode.Lines, 9));

        // Polygon (mesh mode = Points)
        this.rootNode.attachChild(buildPolygonMesh(Mode.Points, 11));

        // Polygon (mesh mode = Points)
        this.rootNode.attachChild(buildPolygonMesh(Mode.LineStrip, 13));

        // Polygon (mesh mode = Points)
        this.rootNode.attachChild(buildPolygonMesh(Mode.Triangles, 15));

        // Polygon (mesh mode = Points)
        this.rootNode.attachChild(buildPolygonMesh(Mode.TriangleFan, 17));

        // Polygon (mesh mode = Points)
        this.rootNode.attachChild(buildPolygonMesh(Mode.TriangleStrip, 19));

        // ########## Line -1
        // Capsules
        this.rootNode.attachChild(buildCapsule(1, 3, 3, 360 / 15));
        this.rootNode.attachChild(buildCapsule(.5f, 2, -2, 5));

        // Triangle (mesh mode = lineLoop)
        this.rootNode.attachChild(buildTriangleMesh(Mode.LineLoop, 7));

        // Triangle (mesh mode = lines)
        this.rootNode.attachChild(buildTriangleMesh(Mode.Lines, 9));

        // Triangle (mesh mode = Points)
        this.rootNode.attachChild(buildTriangleMesh(Mode.Points, 11));

        // Triangle (mesh mode = Points)
        this.rootNode.attachChild(buildTriangleMesh(Mode.LineStrip, 13));

        // Triangle (mesh mode = Points)
        this.rootNode.attachChild(buildTriangleMesh(Mode.Triangles, 15));

        // Triangle (mesh mode = Points)
        this.rootNode.attachChild(buildTriangleMesh(Mode.TriangleFan, 17));

        // Triangle (mesh mode = Points)
        this.rootNode.attachChild(buildTriangleMesh(Mode.TriangleStrip, 19));

        // ########## Line 1
        // Slice
        this.rootNode.attachChild(buildSlice(12, 1, FastMath.QUARTER_PI, -3));

        this.rootNode.attachChild(buildSliceMesh(12, 1, FastMath.QUARTER_PI, -1));

        this.rootNode.attachChild(buildSliceMesh(12, 1, FastMath.PI, 1));

        // Circle
        this.rootNode.attachChild(buildCircleMesh(12, 1, 4));

        // Circle 2 (mesh mode = LineLoop)
        this.rootNode.attachChild(buildCircleTriangularMesh(12, 1, 7, Mode.LineLoop));

        // Circle 2 (mesh mode = TriangleFan)
        this.rootNode.attachChild(buildCircleTriangularMesh(12, 1, 10, Mode.TriangleFan));

        // Ellipse
        this.rootNode.attachChild(buildEllipseMesh(2, 1, 14, Mode.LineLoop));

        // Half ellipse
        this.rootNode.attachChild(buildHalfEllipseMesh(2, 1, 17, Mode.LineLoop));

        // ########## Line 2
        // Half ellipse
        this.rootNode.attachChild(buildHalfEllipse(2, 1, 17, 180 / 15));

        // Cross
        this.rootNode.attachChild(buildCross(0.5f, 12));

        // Cross geom
        this.rootNode.attachChild(buildGeom(0.5f, 10));

    }

    private Geometry buildGeom(final float segmentSize, final float pos_x) {
        final CrossDebug cross = new CrossDebug(0.5f);
        cross.setLineWidth(2);

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", cross);

        geom.setLocalTranslation(pos_x, 7, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildCross(final float segmentSize, final float pos_x) {
        final Mode meshMode = Mode.Lines;

        // Vertex positions in space
        final Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(segmentSize, segmentSize, 0);
        vertices[1] = new Vector3f(-segmentSize, segmentSize, 0);
        vertices[2] = new Vector3f(-segmentSize, -segmentSize, 0);
        vertices[3] = new Vector3f(segmentSize, -segmentSize, 0);

        // Texture coordinates
        // final Vector2f[] texCoord = new Vector2f[7];
        // texCoord[0] = new Vector2f(0.5f, 0);
        // texCoord[1] = new Vector2f(1, 0.3f);
        // texCoord[2] = new Vector2f(1, 0.6f);
        // texCoord[3] = new Vector2f(0.5f, 1);
        // texCoord[4] = new Vector2f(0, 0.6f);
        // texCoord[5] = new Vector2f(0, 0.3f);
        // texCoord[6] = new Vector2f(0.5f, 0.5f);

        // Indexes. We define the order in which mesh should be constructed
        final Mesh m = new Mesh();
        m.setMode(meshMode);

        short[] indexes = null;
        switch (meshMode) {
        case LineLoop:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            // LineLoop usa el primer vertice del arreglo para cerrar el poligono
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(3);
            break;

        case LineStrip:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            // LineStrip no usa el primer vertice para cerrar el poligono, lo deja abierto
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(2);
            break;

        case Lines:
            indexes = new short[] { 0, 2, 1, 3 };
            // Igual a usar LineLoop con indexes = new short[] { 0, 1, 2, 3, 4, 5 };
            // Igual a usar LineStrip con indexes = new short[] { 0, 1, 2, 3, 4, 5, 0 };
            m.setLineWidth(2);
            break;

        case Triangles:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            m.setLineWidth(4);
            break;

        case TriangleFan:
            indexes = new short[] { 6, 0, 1, 2, 3, 4, 5, 0 };
            m.setLineWidth(3);
            break;

        case TriangleStrip:
            // Parece no ser tan bueno para dibujar como TriangleFan, porque necesitas mas vertices para figuras
            // cerradas
            indexes = new short[] { 0, 1, 6, 2, 3, 6, 4, 5, 6, 0 };
            m.setLineWidth(2);
            break;

        default:
            m.setMode(Mode.Points);
            indexes = new short[] { 0, 1, 2, 3 };
            m.setPointSize(5);
            break;
        }

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);

        geom.setLocalTranslation(pos_x, 7, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Spatial buildHalfEllipseMesh(final float xRadius, final float yRadius, final float pos_x,
            final Mode lineloop) {
        final int segmentNumber = 180 / 15;

        final Vector3f[] vertices = new Vector3f[segmentNumber + 1];
        final short[] indexes = new short[segmentNumber + 1];

        for (int i = 0; i <= segmentNumber; i++) {

            final float rad = i * FastMath.DEG_TO_RAD * 15;
            final float x = xRadius * FastMath.sin(rad);
            final float y = yRadius * FastMath.cos(rad);

            vertices[i] = new Vector3f(x, y, 0);

            indexes[i] = (short) i;
        }

        final Mesh m = new Mesh();
        m.setMode(Mode.LineLoop);

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Spatial buildHalfEllipse(final float xRadius, final float yRadius, final float pos_x,
            final int segmentNumber) {
        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", new HalfEllipseDebug(xRadius, yRadius, segmentNumber));
        geom.setLocalTranslation(pos_x, 7, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Spatial buildCapsule(final float xRadius, final float yRadius, final float pos_x, final int segmentNumber) {
        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", new CapsuleDebug(xRadius, yRadius, segmentNumber));
        geom.setLocalTranslation(pos_x, -2.5f, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Spatial buildEllipseMesh(final float xRadius, final float yRadius, final float pos_x, final Mode lineloop) {
        final int segmentNumber = 360;

        final Vector3f[] vertices = new Vector3f[segmentNumber];
        final short[] indexes = new short[segmentNumber];

        int idx = 0;
        for (int i = 0; i < segmentNumber; i = i + 15) {

            final float rad = i * FastMath.DEG_TO_RAD;
            final float x = xRadius * FastMath.sin(rad);
            final float y = yRadius * FastMath.cos(rad);

            vertices[idx] = new Vector3f(x, y, 0);

            indexes[idx] = (short) idx;
            idx++;
        }

        final Mesh m = new Mesh();
        m.setMode(Mode.LineLoop);

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildCircleMesh(final int segmentNumber, final int radius, final float pos_x) {
        final float invSegmentNum = 1 / (float) segmentNumber;

        final Vector3f[] vertices = new Vector3f[segmentNumber];
        final short[] indexes = new short[segmentNumber];

        for (int i = 0; i < segmentNumber; i++) {
            // get the current angle
            final float theta = FastMath.TWO_PI * i * invSegmentNum;

            final float x = radius * FastMath.cos(theta);
            final float y = radius * FastMath.sin(theta);

            vertices[i] = new Vector3f(x, y, 0);

            indexes[i] = (short) i;
        }

        final Mesh m = new Mesh();
        m.setMode(Mode.LineLoop);

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildCircle(final int segmentNumber, final int radius, final float pos_x) {
        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", new CircleDebug(radius, segmentNumber));
        geom.setLocalTranslation(pos_x, 0, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildSliceMesh(final int segmentNumber, final float radius, final float angle, final float pos_x) {

        final float angleStep = angle / segmentNumber;

        final Vector3f[] vertices = new Vector3f[segmentNumber + 2];
        final short[] indexes = new short[segmentNumber + 2];
        final float offset = angle / 2;

        for (int i = 0; i <= segmentNumber; i++) {
            // get the current angle
            final float theta = i * angleStep - offset;

            final float x = radius * FastMath.cos(theta);
            final float y = radius * FastMath.sin(theta);

            vertices[i] = new Vector3f(x, y, 0);

            indexes[i] = (short) i;
        }

        vertices[segmentNumber + 1] = new Vector3f(0, 0, 0);

        indexes[segmentNumber + 1] = (short) (segmentNumber + 1);

        final Mesh m = new Mesh();
        m.setMode(Mode.LineLoop);

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildSlice(final int segmentNumber, final float radius, final float angle, final float pos_x) {
        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", new SliceDebug(radius, angle, segmentNumber));
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildCircleTriangularMesh(final int segmentNumber, final int radius, final float pos_x,
            final Mode meshMode) {
        final float invSegmentNum = 1 / (float) segmentNumber;

        final Vector3f[] vertices = new Vector3f[segmentNumber + 1];
        vertices[segmentNumber] = new Vector3f(0, 0, 0);

        final short[] indexes = new short[segmentNumber + 2];
        indexes[0] = (short) segmentNumber;
        indexes[segmentNumber] = 0;

        for (int i = 0; i < segmentNumber; i++) {
            // get the current angle
            final float theta = FastMath.TWO_PI * i * invSegmentNum;

            final float x = radius * FastMath.cos(theta);
            final float y = radius * FastMath.sin(theta);

            vertices[i] = new Vector3f(x, y, 0);

            indexes[i + 1] = (short) i;
        }

        final Mesh m = new Mesh();
        m.setMode(meshMode);

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);
        geom.setLocalTranslation(pos_x, 4, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildPolygonMesh(final Mode meshMode, final float pos_x) {
        // Vertex positions in space
        final Vector3f[] vertices = new Vector3f[7];
        vertices[0] = new Vector3f(0, -1, 0);
        vertices[1] = new Vector3f(0.5f, -0.5f, 0);
        vertices[2] = new Vector3f(0.5f, 0.5f, 0);
        vertices[3] = new Vector3f(0, 1, 0);
        vertices[4] = new Vector3f(-0.5f, 0.5f, 0);
        vertices[5] = new Vector3f(-0.5f, -0.5f, 0);
        vertices[6] = new Vector3f(0, 0, 0);

        // Texture coordinates
        // final Vector2f[] texCoord = new Vector2f[7];
        // texCoord[0] = new Vector2f(0.5f, 0);
        // texCoord[1] = new Vector2f(1, 0.3f);
        // texCoord[2] = new Vector2f(1, 0.6f);
        // texCoord[3] = new Vector2f(0.5f, 1);
        // texCoord[4] = new Vector2f(0, 0.6f);
        // texCoord[5] = new Vector2f(0, 0.3f);
        // texCoord[6] = new Vector2f(0.5f, 0.5f);

        // Indexes. We define the order in which mesh should be constructed
        final Mesh m = new Mesh();
        m.setMode(meshMode);

        short[] indexes = null;
        switch (meshMode) {
        case LineLoop:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            // LineLoop usa el primer vertice del arreglo para cerrar el poligono
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(3);
            break;

        case LineStrip:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            // LineStrip no usa el primer vertice para cerrar el poligono, lo deja abierto
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(2);
            break;

        case Lines:
            indexes = new short[] { 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 0 };
            // Igual a usar LineLoop con indexes = new short[] { 0, 1, 2, 3, 4, 5 };
            // Igual a usar LineStrip con indexes = new short[] { 0, 1, 2, 3, 4, 5, 0 };
            m.setLineWidth(2);
            break;

        case Triangles:
            indexes = new short[] { 6, 0, 1, 6, 1, 2, 6, 2, 3, 6, 3, 4, 6, 4, 5, 6, 5, 0 };
            m.setLineWidth(4);
            break;

        case TriangleFan:
            indexes = new short[] { 6, 0, 1, 2, 3, 4, 5, 0 };
            m.setLineWidth(3);
            break;

        case TriangleStrip:
            // Parece no ser tan bueno para dibujar como TriangleFan, porque necesitas mas vertices para figuras
            // cerradas
            indexes = new short[] { 0, 1, 6, 2, 3, 6, 4, 5, 6, 0 };
            m.setLineWidth(2);
            break;

        default:
            m.setMode(Mode.Points);
            indexes = new short[] { 0, 1, 2, 3, 4, 5, 6 };
            m.setPointSize(5);
            break;
        }

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);

        geom.setLocalTranslation(pos_x, 0, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

    private Geometry buildTriangleMesh(final Mode meshMode, final float pos_x) {
        // Vertex positions in space
        final Vector3f[] vertices = new Vector3f[3];
        vertices[0] = new Vector3f(0, -1, 0);
        vertices[1] = new Vector3f(0.5f, -0.5f, 0);
        vertices[2] = new Vector3f(0.5f, 0.5f, 0);

        // Texture coordinates
        // final Vector2f[] texCoord = new Vector2f[3];
        // texCoord[0] = new Vector2f(0.5f, 0);
        // texCoord[1] = new Vector2f(1, 0.3f);
        // texCoord[2] = new Vector2f(1, 0.6f);

        // Indexes. We define the order in which mesh should be constructed
        final Mesh m = new Mesh();
        m.setMode(meshMode);

        short[] indexes = null;
        switch (meshMode) {
        case LineLoop:
            indexes = new short[] { 2, 0, 1 };
            // LineLoop usa el primer vertice del arreglo para cerrar el poligono
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(3);
            break;

        case LineStrip:
            indexes = new short[] { 2, 0, 1, 2 };
            // LineStrip no usa el primer vertice para cerrar el poligono, lo deja abierto
            // Ver ejemplo:
            // indexes = new short[] { 6, 0, 1, 2 };
            m.setLineWidth(2);
            break;

        case Lines:
            indexes = new short[] { 0, 1, 1, 2, 2, 0 };
            // Igual a usar LineLoop con indexes = new short[] { 0, 1, 2, 3, 4, 5 };
            // Igual a usar LineStrip con indexes = new short[] { 0, 1, 2, 3, 4, 5, 0 };
            m.setLineWidth(2);
            break;

        case Triangles:
            indexes = new short[] { 2, 0, 1 };
            m.setLineWidth(4);
            break;

        case TriangleFan:
            indexes = new short[] { 2, 0, 1 };
            m.setLineWidth(3);
            break;

        case TriangleStrip:
            // Parece no ser tan bueno para dibujar como TriangleFan, porque necesitas mas vertices para figuras
            // cerradas
            indexes = new short[] { 0, 1, 2 };
            m.setLineWidth(2);
            break;

        default:
            m.setMode(Mode.Points);
            indexes = new short[] { 0, 1, 2 };
            m.setPointSize(5);
            break;
        }

        // Setting buffers
        m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        // m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // Creating a geometry, and apply a single color material to it
        final Geometry geom = new Geometry("OurMesh", m);

        geom.setLocalTranslation(pos_x, -2.5f, 0);
        geom.setMaterial(this.mat);

        return geom;
    }

}
