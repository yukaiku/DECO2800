# Tundra Zone

# The `TundraWorld` class

`worlds/tundra/TundraWorld.java`

## public properties and methods

- `public static final String MAP_FILE`: the JSON file that stores the map. For now, this file only stores data of tiles.

### Constructors

Each constructor does the following things:

- Generate static entities for the world
- Create and set a player entity
- Provide available enemies to the EnemyManager

- `public TundraWorld()`: create a world with default width and height (`DEFAULT_WIDTH = 25`, `DEFAULT_HEIGHT = 25`)
- `public TundraWorld(int width, int height)`: create a world with given width and height

## private properties and methods

- `private void generateStaticEntities()`: generate static entities
- `private void addCampfire(float col, float row)`: add a `TundraCampfire` to the given position
- `private void addTreeLog(float col, float row)`: add a `TundraTreeLog` to the given position
- `private void addRock(float col, float row)`: add a `TundraRock` to the given position

# The Tundra Entity types

- The tundra entity types include:
  - `TundraCampfire`: in `entities/environment/tundra/TundraCampfire.java`
    - obstructed by default
  - `TundraTreeLog`: in `entities/environment/tundra/TundraTreeLog.java`
    - obstructed by default
  - `TundraRock`: in `entities/environment/tundra/TundraRock.java`
    - obstructed by default

# Changelog

*Last update: 4:00 Sep 6 2020*

*Documentation by @nathan-nguyen*