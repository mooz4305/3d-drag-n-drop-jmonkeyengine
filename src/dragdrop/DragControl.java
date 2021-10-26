/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragdrop;

import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.List;

/** A class encapsulating drag behavior for a spatial,
 *  and is created by DragDropControl automatically
 * 
 *  @author JMT
 */
public class DragControl {

    DragControlManager dragControlManager;
    Spatial spatial;

    private boolean enabled = false;
    private boolean draggable = true;   // whether spatial can be dragged by cursor
    
    public DragControl(DragControlManager dc){
        dragControlManager = dc;
    }
   
    public DragControl clone() {                
        DragControl dc = new DragControl(dragControlManager);        
        return dc;
    }
    
    // the update loop - when enabled, update the position of spatial to track mouse cursor
    public void update(float tpf) {
        if (enabled) {
            InputManager inputManager = dragControlManager.getInputManager();
            Camera cam = dragControlManager.getCamera();

            Vector3f item_location = spatial.getLocalTranslation().subtract(cam.getLocation());
            Vector3f projection = item_location.project(cam.getDirection());
            float z_view = cam.getViewToProjectionZ(projection.length());

            Vector2f click2d = inputManager.getCursorPosition().clone();  
            Vector3f click3d = cam.getWorldCoordinates(click2d, z_view);

            spatial.setLocalTranslation(click3d.x, click3d.y, spatial.getLocalTranslation().z);
        }
    }
    
    // the command to make the spatial track the mouse cursor
    public void snapToCursor() {
        // make spatial pop out
        Vector3f location = spatial.getLocalTranslation();        
        
        //spatial.setLocalTranslation(location.getX(), location.getY(), 2); 
        
        // enable update loop
        setEnabled(true);
    }
    
    // the command to make the spatial stop following the cursor
    public void unsnapFromCursor() {
        setEnabled(false);
    }
    
    
    // ------------- SETTERS / GETTERS -------------- //
    
    private void setEnabled(boolean bool) {
        enabled = bool;
    }
    
    // turn on (or off) draggability of the spatial
    public void setDraggable(boolean b) {
        draggable = b;
    }
    
    public boolean isDraggable() {
        return draggable;
    }
    
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
}














































