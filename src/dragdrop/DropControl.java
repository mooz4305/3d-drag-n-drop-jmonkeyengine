/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragdrop;

import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** A class encapsulating drop behavior for a spatial,
 *  and is created by DragDropControl automatically.
 *
 * @author JMT
 */
public class DropControl {

    protected DragControlManager dragControlManager;
    private Spatial spatial;
    
    protected List<DropContainer> drop_containers;
    private DropContainer current_drop_container;
    
    private Vector3f start_pos;
    private Vector3f final_pos;
    
    private boolean enabled = false;
    private boolean dropped = true;
    private boolean animated = true;
    private boolean snapback = true;    


    DropControl(DragControlManager dc) {
        dragControlManager = dc;
        drop_containers = new ArrayList();
    }
    
    public DropControl clone() {                
        DropControl dc = new DropControl(dragControlManager);
        dc.drop_containers = new ArrayList(drop_containers);
        
        return dc;
    }
    
    /* -------------- METHODS FOR CONTROL ---------------- */
    
    public void addDropContainer(DropContainer container) {
        drop_containers.add(container);
    }
    
    public void addDropContainers(List<DropContainer> containers) {
        for (DropContainer container : containers) {
            drop_containers.add(container);
        }
    }
    
    public void removeDropContainers() {
        drop_containers.clear();
    }

    // the update loop - when enabled, move the spatial to the final position
    public void update(float tpf) {
        if (enabled) {
            Vector3f current_pos = spatial.getLocalTranslation().clone();  
            Vector3f dir = final_pos.subtract(current_pos);

            if (dir.length() < 0.05f || !animated) {
                spatial.setLocalTranslation(final_pos);  
                dropped = true;
                setEnabled(false);
            } else {
                spatial.setLocalTranslation(current_pos.add(dir.mult(10*tpf)));
            }
        }
    }
    
    public void snapToCursor() {
        start_pos = spatial.getLocalTranslation().clone();
    }
    
    public void unsnapFromCursor() {
        Vector3f location = spatial.getLocalTranslation();
        //spatial.setLocalTranslation(location.getX(), location.getY(), 0);
        
        Map<CollisionResult, DropContainer> resultMap = new HashMap();
        CollisionResults results = new CollisionResults();
        
        for (DropContainer container : drop_containers) { 
            Spatial collision_spatial = container.getCollisionSpatial();
            CollisionResults local_results = new CollisionResults();
            
            collision_spatial.collideWith((BoundingBox) spatial.getWorldBound(), local_results);
            if (local_results.size() > 0) {
                CollisionResult result = local_results.getClosestCollision();
                results.addCollision(result);
                resultMap.put(result, container);
            }
        } 
        
        if (results.size() > 0) {
            CollisionResult final_result = results.getClosestCollision();
            DropContainer container = resultMap.get(final_result);
            
            if (container.isVacant()) {
                final_pos = container.getPosition();
                container.addItem(spatial);
                
                if (current_drop_container != null) {
                    current_drop_container.removeItem();
                }
                
                current_drop_container = container;
            } else if (snapback) {
                final_pos = start_pos;
            } else {
                final_pos = spatial.getLocalTranslation();
            }
        } else if (snapback) {
            final_pos = start_pos;
        } else {
            final_pos = spatial.getLocalTranslation();
        }
        
        dropped = false;
        setEnabled(true);
    }
    
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
        
        if (spatial != null) { 
            start_pos = spatial.getLocalTranslation().clone();
        }
    }
    
    private void setEnabled(boolean bool) {
        enabled = bool;
    }
    
    public boolean isDropped() {
        return dropped;
    }
}
