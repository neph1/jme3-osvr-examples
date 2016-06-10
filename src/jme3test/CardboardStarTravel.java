/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test;

import com.jme3.app.SimpleApplication;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.vr.app.DualCamAppState;
import com.jme3.vr.osvr.app.state.OsvrAppState;
import java.util.List;

/**
 *
 * @author reden
 */
public class CardboardStarTravel extends SimpleApplication{

    private Material mat;
    private Node observer;
    
    private Node stars;
    private SpotLight light;
    
    private float maxDistance = 100f;
    
    public static void main(String[] args){
        CardboardStarTravel starTravel = new CardboardStarTravel();
        AppSettings settings = new AppSettings(false);
//        settings.setRenderer(AppSettings.LWJGL_OPENGL3);
        settings.setFrameRate(90);
//        starTravel.setShowSettings(false);
        starTravel.setSettings(settings);
        starTravel.start();
    }
    
    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        cam.setFrustumNear(0.1f);
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.White);
        geom.setMaterial(mat);
        
        rootNode.attachChild(geom);
        
        observer = new Node("");
        rootNode.attachChild(observer);
        getRenderManager().removePostView(guiViewPort);
        guiNode.detachAllChildren();
        
        stars = new Node();
        rootNode.attachChild(stars);
        initStars();
        
        light = new SpotLight();
        light.setSpotOuterAngle(FastMath.HALF_PI-0.01f);
        stars.addLight(light);
        
        DualCamAppState camAppState = new DualCamAppState(settings.getWidth() / 2, settings.getHeight(), rootNode);
        stateManager.attach(camAppState);
        
        OsvrAppState osvrAppState = new OsvrAppState(rootNode);
        stateManager.attach(osvrAppState);
    }
    
    

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        
        List<Spatial> starList = stars.getChildren();
        
        for(Spatial s: starList){
            s.move(0, 0, .25f);
            if(s.getWorldTranslation().z  > maxDistance){
                s.setLocalTranslation(FastMath.nextRandomFloat() * 100 - 50, FastMath.nextRandomFloat() * 100 - 50, -maxDistance);
            }
        }
        light.setDirection(cam.getDirection());
        
    }
    
    private void initStars(){
        Geometry star;
        for(int i = 0; i < 200; i++){
            star = new Geometry("Star"+i, new Box(1,1,1));
            star.setMaterial(mat);
            star.rotate(FastMath.nextRandomFloat() * FastMath.TWO_PI, FastMath.nextRandomFloat() * FastMath.TWO_PI, FastMath.nextRandomFloat() * FastMath.TWO_PI);
            star.setLocalTranslation(FastMath.nextRandomFloat() * 100 - 50, FastMath.nextRandomFloat() * 100 - 50, FastMath.nextRandomFloat() * maxDistance * 2 - maxDistance);
            stars.attachChild(star);
        }
    }
    
}
