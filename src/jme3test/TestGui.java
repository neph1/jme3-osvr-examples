/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.vr.app.DualCamAppState;
import com.jme3.vr.osvr.app.state.OsvrAppState;

/**
 *
 * @author reden (neph1@github)
 */
public class TestGui extends SimpleApplication{

    private BitmapText txt;
    private String txtB =
    "ABCDEFGHIKLMNOPQRSTUVWXYZ1234567\n890`~!@#$%^&*()-=_+[]\\;',./{}|:<>?";
    
    public static void main(String[] args){
        TestGui app = new TestGui();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        stateManager.getState(FlyCamAppState.class).setEnabled(false);
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, settings.getWidth()/2, settings.getHeight()));
        txt.setSize(fnt.getPreferredSize() * 2f);
        txt.setText(txtB);
        txt.setLocalTranslation(160, txt.getHeight()-500, 60);
        guiNode.attachChild(txt);
        
        DualCamAppState camAppState = new DualCamAppState(settings.getWidth() / 2, settings.getHeight(), rootNode, guiNode, true);
        stateManager.attach(camAppState);

        OsvrAppState osvrAppState = new OsvrAppState(rootNode);
        stateManager.attach(osvrAppState);
    }
    
}
