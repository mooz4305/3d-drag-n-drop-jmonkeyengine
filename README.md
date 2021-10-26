### About

The project contains a package `dragdrop` for jMonkeyEngine (3.2.4+) that provides in-game 3D drag and drop functionality. There are three components of the package that are of most interest:
- a `DragDropControl` control for Geometry spatials, that allows them to be dragged/dropped 
  in game with the mouse cursor,
- a `DropContainer` class, so one can create containers that dropped items will snap to,
- a `DragControlManager` class that tracks draggable items and orchestrates user input.

### Usage
There is a demo `mygame` that illustrates package usage. Here are a few principles:
- there must be a drag control manager,
- to setup draggable Geometry objects :
  - each control must have a reference to a drag control manager, so manager can register the draggable Geometry,
  - drop containers can be added to a control (to isolate containers for different types of draggable objects),
  - a control can be cloned and added to Geometry objects as desired, 
- drop containers are initialized with a collision geometry for detecting a dropped Geometry object.
### Functionality

Geometries with a drag and drop control can be dragged through 3D space from 
any camera orientation.
Objects are set to move with their z-position locked, 
as the original use case of the package was for dragging pieces/cards in board/card games; 
however, the package can be used and expanded for other applications.
For example, the `update` method in `DragControl` can be altered to define how objects will be dragged 
through 3D space, e.g. rotation about camera/player or xy-translation along a ground topography.

Drop containers keep track of the object they contain, preventing objects from snapping to 
a container if the container is already occupied. Objects currently snap to the drop container's collision geometry, 
but the `getPosition()` method in the `DropContainer` class can be changed to return a 
custom location for items to snap to.

### Future Directions

The package works well for immobile drop containers, but does not work for mobile drop containers.
It would be very useful to expand functionality in this direction, as there are many additional use cases: 
items may need to be dropped and snapped onto moving vehicles, other players, etc.

It would be useful to add custom presets for the snapping locations of drop containers 
(possible based on draggable item geometry), as well as how items are dragged through 3D space.