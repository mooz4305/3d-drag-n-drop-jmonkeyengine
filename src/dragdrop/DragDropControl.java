/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragdrop;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.List;

/** A custom control that can be used for drag and drop.
 *  Usage:
 *      - construct new control with reference to a DragControlManager
 *      - add control to any Spatial, and call the following methods;
 *          (1) setDraggable(boolean) to set draggability for the spatial
 *          (2) addDropContainer(DropContainer) to adda drop container 
 *          (3) addDropContaniers(List<DropContainer>) to add multiple drop containers
 * 
 * @author JMT
 */
public class DragDropControl extends AbstractControl {
    protected DragControlManager dragControlManager;
    protected DragControl dragControl;
    protected DropControl dropControl;
    
    Spatial spatial;
    
    public DragDropControl(DragControlManager dc) {
        dragControlManager = dc;
        dragControl = new DragControl(dc);
        dropControl = new DropControl(dc);
    }
    
    public DragDropControl clone() {      
        
        DragDropControl dc = new DragDropControl(dragControlManager);
        dc.dragControl = dragControl.clone();
        dc.dropControl = dropControl.clone();
        
        return dc;
    }
    
    /* ------------- METHODS FOR OUR PROJECT --------------- */
    
    // turn on (or off) draggability
    public void setDraggable(boolean bool) {
        dragControl.setDraggable(bool);
    }
 
    // add drop container for Spatial to snap to
    public void addDropContainer(DropContainer container) {
        dropControl.addDropContainer(container);
    }
    
    // add multiple drop containers
    public void addDropContainers(List<DropContainer> containers) {
        dropControl.addDropContainers(containers);
    }
    
    // remove all drop containers
    public void removeDropContainers() {
        dropControl.removeDropContainers();
    }
    
    // swap out drag control
    public void setDragControl(DragControl dc) {
        dragControl.setSpatial(null);
        dragControl = dc;
        dragControl.setSpatial(spatial);
    }
    
    // swap out drop control
    public void setDropControl(DropControl dc) {
        dropControl.setSpatial(null);
        dropControl = dc;
        dropControl.setSpatial(spatial);
    }
    
    // ------ METHODS FROM ABSTRACT CONTROL --------
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        if (spatial != null) { 
            dragControlManager.register(this);
        } else {
            dragControlManager.remove(this);
        }
        
        dragControl.setSpatial(spatial);
        dropControl.setSpatial(spatial);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        dragControl.update(tpf);
        dropControl.update(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
 
    
    // ------------ METHODS FOR DRAG CONTROL MANAGER ------------------
    
    public boolean isDraggable() {
        return dragControl.isDraggable() && dropControl.isDropped();
    }

    public void snapToCursor() {
        dropControl.snapToCursor();
        dragControl.snapToCursor();
    }
    
    public void unsnapFromCursor() {
        dropControl.unsnapFromCursor();
        dragControl.unsnapFromCursor();
    }
    
}
