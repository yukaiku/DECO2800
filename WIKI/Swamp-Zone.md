## worlds/SwampWorld.java
- `SAVE_LOCATION_AND_FILE_NAME`: A String attribute to store the path to the JSON file for loading the game map
- Constructors:
  - `SwampWorld(int width, int height)`: `width` and `height` are the halves of the dimensions of the game map (25 and 25 for a 50x50 map). This constructor calls the `loadWorld` method through the `DatabaseManager` object to load the JSON file as the game map. 
  - `SwampWorld()`: Default constructor that makes a call to the `SwampWorld(int width, int height)` constructor.
- `generateTiles()`: An abstract method declared in `AbstractWorld` class that is defined in `SwampWorld`. This method is no longer in use as we have shifted to the approach explained in ***Environment/Tile Map Setup***. This method is still kept because it is used only in the `TestWorld` class.
- `generateStaticEntities()`: A method to instantiate the classes of static entities mentioned below and add them to the `entities` list.

## worlds/SwampTile.java
- `SwampTile` is a subclass of `Tile`. Up until this point, `SwampTile` is basically the same as Tile.
- Later on, tile effects that are unique to Swamp Zone only can be implemented in this `SwampTile` class.

## entities/SwampDeadTree.java, SwampFallenTree.java, SwampPond.java, SwampTreeLog.java, SwampTreeStub.java, SwampVineTree.java
- All of these classes extend the `StaticEntity` class and represent the entities that are placed in the Swamp Zone.\
- Instances of theses classes are created and added to the Swamp Zone through the `generateStaticEntities()` method in the `SwampWorld` class.

# Documentation by @hayden-huynh