package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import dragdrop.DragControlManager;
import dragdrop.DragDropControl;
import dragdrop.DropContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // -------- DROP CONTAINERS SETUP --------
        
        Material containerMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        containerMaterial.setBoolean("UseMaterialColors",true);
        containerMaterial.setColor("Diffuse", ColorRGBA.Gray);
        containerMaterial.setColor("Specular",ColorRGBA.Gray);
        
        ArrayList<DropContainer> dropContainers = new ArrayList<>();
        List<Vector3f> dropOffsets 
            = Arrays.asList(new Vector3f( 2, 2, 0.5f), 
                            new Vector3f(-2, 2, 0.5f), 
                            new Vector3f( 2,-2, 0.5f), 
                            new Vector3f(-2,-2, 0.5f));
        
        for (Vector3f offset : dropOffsets) {
            // setup of geometry to detect collision with dropped object
            Geometry collisionGeometry = new Geometry("Box", new Box(1,1,1));
            collisionGeometry.setMaterial(containerMaterial);
            collisionGeometry.setCullHint(CullHint.Always);
            collisionGeometry.move(offset);

            // visual aid for drop locations
            Geometry containerGeometry = new Geometry("Box", new Box(1,1,0.10f));
            containerGeometry.setMaterial(containerMaterial);
            containerGeometry.move(offset);
            containerGeometry.move(new Vector3f(0,0,-1.10f));
            rootNode.attachChild(containerGeometry);
                    
            // IMPORTANT: make a drop container using the collision geometry
            DropContainer container = new DropContainer(collisionGeometry);
            rootNode.attachChild(container);
            dropContainers.add(container);
        }
        
        // ------ DRAGGABLE ITEM SETUP ------
        
        Material draggableMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        draggableMaterial.setBoolean("UseMaterialColors",true);
        draggableMaterial.setColor("Diffuse", ColorRGBA.LightGray);
        draggableMaterial.setColor("Specular",ColorRGBA.LightGray);
        
         Material draggableMaterialTwo = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        draggableMaterialTwo.setBoolean("UseMaterialColors",true);
        draggableMaterialTwo.setColor("Diffuse", ColorRGBA.DarkGray);
        draggableMaterialTwo.setColor("Specular",ColorRGBA.DarkGray);
        
        Geometry draggableItem = new Geometry("Box", new Box(1, 1, 1));
        Geometry draggableItemTwo = new Geometry("Box", new Box(1, 1, 1));
            
        draggableItem.move(0,0,0.5f);
        draggableItemTwo.move(-4,4,0.5f);
       
        draggableItem.setMaterial(draggableMaterial);
        draggableItemTwo.setMaterial(draggableMaterialTwo);

        rootNode.attachChild(draggableItem);
        rootNode.attachChild(draggableItemTwo);
        
        
        // ------ IMPORTANT: DRAG DROP BEHAVIOR SETUP --------
        
        // instantiate manager 
        DragControlManager dcm = new DragControlManager(this.inputManager, this.cam, this.rootNode);

        // create drag drop control with reference to manager
        DragDropControl dc = new DragDropControl(dcm);
        
        // give control references to drop containers to snap to
        dc.addDropContainers(dropContainers);
        
        // attach control to items for drag and drop behavior
        draggableItem.addControl(dc);
        draggableItemTwo.addControl(dc.clone());

        
        // ------ SCENE ENVIRONMENT SETUP --------
      
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        cam.setLocation(new Vector3f(-5,-5,10));
        cam.lookAt(new Vector3f(-0.5f,-0.5f,0), Vector3f.UNIT_Z);
        
        flyCam.setEnabled(false);
        viewPort.setBackgroundColor(ColorRGBA.White);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
