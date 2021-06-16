# Introduction

Saving and loading is an important feature of the game. It does not only improve the experience of the player, but also helps game developers create some other useful features for the game, including:
- Designing tiled map and loading them into the game
- Placing entities at fixed position inside the worlds without hard-coding
- Minimap

The initial project decided on saving and loading worlds with JSON files using the [Gson library](https://github.com/google/gson).

To save and load worlds, these static methods of the `DatabaseManager` class can be used:

- `public static void saveWorldToJsonFile(AbstractWorld world, String filepath)`
- `public static void loadWorldFromJsonFile(AbstractWorld world, String filepath)`

Before using these methods, please go through this doc to make sure that you know how to use them correctly.

# Why rewriting `saveWorld` and `loadWorld`

The initial project already came with the `saveWorld` and `loadWorld` methods. They are capable of storing and loading basic entity and tile types. However, there is no good way to modify them to store more complex types.

# Structure of JSON files

A JSON file that is created by the `saveWorldToJsonFile` method (and thus compatible with the `loadWorldFromJsonFile` method) has the following structure:

```json
{
    "entities": [
        {
            "entityClass": "...", // Entity class (type)
            "entityObject": {
                // Entity object (data)
            }
        },
        {
            // ...
        }
    ],
    "tiles": [
        {
            "tileClass": "...", // Tile class (type)
            "tileObject": {
                // Tile object (data)
            }
        }
    ]
}
```

There are two arrays in the object that the file stores:
- The first array is the `entities` array.
- The second array is the `tiles` array.
- Each element in each array stores two pieces of information:
  - The class of the element. This will help `loadWorldFromJsonFile` figure out the correct class for the entity or tile.
  - The attributes of the entity or tile. An attribute of a entity class or tile class can only be saved or loaded if it is declared with the `@Expose` annotation.


# Overview of the methods

## `saveWorldToJsonFile`

The `saveWorldToJsonFile` method takes the `entities` and `tiles` list of a world and write them to a JSON file specified by the `filepath`.

The `saveWorldToJsonFile` does not save all attributes of an entity or tile object. Rather, it only saves attributes that have the `@Expose` annotation attached to it.

```java
public class EntityX {
    @Expose
    String name;

    int entityID;
    
    @Expose
    SquareVector coords;
}
```

For the `EntityX` class in the above example, `saveWorldToJsonFile` will only save the `name` and `coords` attributes of `EntityX` objects to the JSON file.

## `loadWorldFromJsonFile`

The `loadWorldFromJsonFile` overrides the `entities` and `tiles` list with the entities and tiles from the JSON file.

**Important note**: Each entity or tile object is first created with the **no-argument constructor** of the entity/tile class. After that, attributes of the object are assigned. Keep this important note in mind if you want to make sure that your entity/tile class is compatible with the `loadWorldFromJsonFile` method.

# Compatible Entity and Tile classes

**For your Entity type or Tile type to be saved and loaded successfully** using `saveWorldToJsonFile` and `loadWorldFromJsonFile`, **you need to do the following things**:

- Decide on which attributes of an object of the class to be saved or loaded. Attach them with the `@Expose` annotation.
- Add the class to the `TypeManager` class.
- When creating an entity/tile object, the `loadWorldFromJsonFile` invoke the no-argument constructor of the entity/tile class. Only after that are the attributes got assigned (see the previous section). **Please re-check the logic of your no-argument constructor (if there is one).**

## List of Compatible Entity and Tile classes

*Please include the names of your Entity/Tile classes in the list below if you are certain that they are compatible with the `saveWorldToJsonFile` and `loadWorldToJsonFile` methods.*

- **Entity classes**:
  - `TundraCampfire`
  - `TundraTreeLog`
  - `TundraRock`
  - ...
- **Tile classes**:
  - `Tile`
  - ...

# Change log

*Last update: 20:20 Sep 16 2020*

*Documentation by @nathan-nguyen*