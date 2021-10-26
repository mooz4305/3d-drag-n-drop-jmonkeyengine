/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragdrop;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

/** A drop container for spatials with DragDropControl. 
 *  Spatials can drop into a drop container if that container was added to the control.
 *  Usage:
 *      - Create new DropContainer using an associated collision spatial.
 *      - The collision spatial will only be visible if added to the scene graph.
 *      - Add the DropContainer to any spatial with DragDropControl behavior.
 * @author JMT
 */
public class DropContainer extends Node {
    private Spatial item;             // item currently in the container
    private Spatial collisionSpatial; // detect drops using a collision with this spatial
    private Boolean vacant;           // is container vacant
    
    public DropContainer(Spatial s) {
        super();
        
        item = null;
        collisionSpatial = s;
        vacant = true;
        
        this.attachChild(s);
    }
    
    public void addItem(Spatial item) {
        this.item = item;
        vacant = false;
        
        System.out.println("Notification: item has been added!");
    }
    
    public void removeItem() {
        this.item = null;
        vacant = true;
        
        System.out.println("Notification: item has been removed!");
    }
    
    public Spatial getItem() {
        return item;
    }
    
    public Spatial getCollisionSpatial() {
        return collisionSpatial;
    }    
    
    public Vector3f getPosition() {
        return collisionSpatial.getLocalTranslation();
    }
    
    public boolean isVacant() {
        return vacant;
    }
}
