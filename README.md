### About

The project contains a package `dragdrop` that provides in-game 3D drag and drop functionality,
for jMonkeyEngine (3.2+). There are three components of the package that are of most interest:
- a `DragDropControl` control for Geometry spatials, that allows them to be dragged/dropped 
  in game with the mouse cursor,
- a `DropContainer` class, so one can create containers that dropped items will snap to,
- a `DragControlManager` class that tracks draggable items and orchestrates user input.

### Usage
There is a demo `mygame` that illustrates package usage, but here are a few principles:
- to setup draggable Geometry spatials :
  - each control must have a reference to a drag control manager, so manager can register the draggable Geometry,
  - drop containers can be added to a control (to isolate containers for different types of draggable objects),
  - a control can cloned and added to Geometry objects as desired, 
- drop containers are initialized with  collision geometry to detect a draggable Geometry object when dropped
### Functionality

Geometries with a drag and drop control be dragged through 3D space 
from any camera orientation, but objects are set to move with z-position locked. 
The original use case of the package was dragging pieces/cards in board/card games, 
but the package can be easily expanded to other applications.

For example, changing the `update` method in `DragControl` to define
how object will be dragged through 3D space, e.g. rotation about camera/player,
or translation along a ground topography.

Drop containers keep track of the object it contains, preventing objects from snapping to 
a container if the container is already occupied. Object snap to the drop container's geometry, but the method `getPosition()` in the `DropContainer` class can be altered to return a 
custom location for items to snap to.

### Future Directions

The package works well for immobile drop containers, but does not work for mobile drop containers.
It would be very useful to expand functionality in this direction, as there are many use cases: 
items may need to be dropped and snapped onto moving vehicles, other players, etc.

It would be useful to add custom presets for snapping locations of drop containers 
(possible pased on draggable item geometry) as well as how items are dragged through 3D space.