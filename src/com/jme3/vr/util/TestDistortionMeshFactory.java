/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.vr.util;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;

/**
 *
 * @author reden (neph1@github)
 */
public class TestDistortionMeshFactory extends SimpleApplication{

    public static void main(String... args){
        TestDistortionMeshFactory test = new TestDistortionMeshFactory();
        test.start();
    }
    
    
    
    @Override
    public void simpleInitApp() {
        float frustumSize = 0.6f;
        cam.setFrustumNear(0.5f);
        cam.setLocation(Vector3f.ZERO);
        cam.setParallelProjection(true);
        stateManager.getState(FlyCamAppState.class).setEnabled(false);
        float aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum(-15, 15, -aspect * frustumSize, aspect * frustumSize, frustumSize, -frustumSize);
        DistortionMeshFactory factory = new DistortionMeshFactory(DistortionMeshFactory.DistortionType.OSVR, -0.3f);
        
        Geometry leftMesh = factory.makeOsvrDistortionMesh(40, 45, new Vector2f(0.47099999999999997f, 0.5f));
        
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        m.getAdditionalRenderState().setWireframe(true);
//        m.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        Texture2D tex = (Texture2D) assetManager.loadTexture("Textures/gridtest.png");
        tex.setWrap(Texture.WrapMode.EdgeClamp);
        m.setTexture("ColorMap", tex);
        leftMesh.move(-0.54f, 0f, 0);
        leftMesh.setMaterial(m);
        rootNode.attachChild(leftMesh);
        
        Geometry rightMesh = factory.makeOsvrDistortionMesh(40, 45, new Vector2f(0.52900000000000003f, 0.5f));
        rightMesh.move(0.54f, 0f, 0);
        rightMesh.setMaterial(m.clone());
        rootNode.attachChild(rightMesh);
    }
    
}
