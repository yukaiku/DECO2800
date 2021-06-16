# Introduction 

The Swamp Dungeon is accessible via a Portal placed in the Swamp Zone. Upon entering the Dungeon, the player will be greeted by six NPCs, all of which claim that they are not liars. The mission of the player is to talk with each of these NPCs and eventually deduce which of them is actually speaking the truth so that the player can make the correct choice of stepping on one of the three available golden platforms. If the player steps on the correct platform, they immediately gain a new Healing skill and a Portal to exit the dungeon will appear. Otherwise, if the player makes the wrong choice, they will have to pay for the consequences.

# Implementation

## Inside SwampWorld.java

- A new function is created to add the Dungeon Portal to the Zone.

  ```java
  public void createDungeonPortal(float col, float row){
      Tile portalTile = getTile(col, row);
      entities.add(new Portal(portalTile, false, "swamp_portal", "SwampDungeonPortal"));
  }
  ```

## The new SwampDungeon.java

- The class SwampDungeon extends the AbstractWorld class. It was basically implemented in the same way as the four main Zones.

- Same as the main Zone classes, SwampDungeon also contains its own EnemyManager, NonPlayablePeonManager and DialogManager

- When the player steps onto either of the wrong platforms, the platform texture switchs back to light black and enemies will spawn surrounding the player.

  ```java
  private void spawnEnemies() {
      EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
      enemyManager.spawnSpecialEnemy("swampGoblin", 1, 2);
      enemyManager.spawnSpecialEnemy("swampGoblin", -1, 1);
      enemyManager.spawnSpecialEnemy("swampGoblin", -2, -1);
      enemyManager.spawnSpecialEnemy("swampGoblin", -1, -3);
      enemyManager.spawnSpecialEnemy("swampGoblin", 1, -5);
      enemyManager.spawnSpecialEnemy("swampGoblin", 8, 2);
      enemyManager.spawnSpecialEnemy("swampGoblin", 10, 1);
      enemyManager.spawnSpecialEnemy("swampGoblin", 11, -1);
      enemyManager.spawnSpecialEnemy("swampGoblin", 10, -3);
      enemyManager.spawnSpecialEnemy("swampGoblin", 8, -5);

      for (int i = -6; i < 4; i++) {
          enemyManager.spawnSpecialEnemy("swampOrc", -3, i);
      }
  }

  @Override
  public void activateTrapTile(Tile tile) {
     tile.setTexture("dungeon-light-black");
     spawnEnemies();
  }
  ```

- When the player steps onto the correct platform, they are rewarded the Healing skill and a Portal opens up for them to exit the Dungeon, going back to the main Swamp Zone.

  ```java
  private void spawnExitPortal() {
      Tile portalTile = getTile(-9, -1);
      entities.add(new Portal(portalTile, false, "swamp_portal", "ExitPortal"));
  }

  @Override
  public void activateRewardTile(Tile tile) {
      tile.setTexture("dungeon-light-black");
      GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.HEAL);
      spawnExitPortal();
  }
  ```

# Documentation prepared by @hayden-huynh