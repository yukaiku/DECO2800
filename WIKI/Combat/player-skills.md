# ***Player Skills***
The player skills system provides an method for initialising the player with certain skills based on how they set up their polyhedron. This page describes:
1) What skills are implemented and their effects,
2) Using the player skill system for granting skills to the player,
3) Adding new skills to the system, and
4) The code implementation.

| **Index** |
|-----------|
| [Skill List](#skill-list) |
| [Gaining Skills](#gaining-skills) |
| [Adding New Skills](#adding-new-skills) |
| [Implementation](#implementation) |

## Skill List
| **Skill Name** | **Icon** | **Knight/Character** | **Description** |
|----------------|----------|----------------------|-----------------|
| Fireball | ![firewizard_skill_icon](uploads/74480d05f1a5e45af4a17255034033b1/firewizard_skill_icon.png) | Fire Wizard | Projectile that applies damage when it hits a target. Short cooldown. |
| Iceball | ![tundra_skill_icon](uploads/e2a7ddffc909661c38492844af43e62e/tundra_skill_icon.png) | Water Wizard | Projectile that applies small amount of damage, and applies a slow effect. Medium cooldown. |
| Sting | ![swamp_skill_icon](uploads/d7e64b566e194977be1ab917f1c63f9e/swamp_skill_icon.png) | _earned_ | Projectile that applies a damage over time affect to a target. Medium cooldown. |
| Sand Tornado | ![desert_skill_icon](uploads/d1085fe1c320bb3e8b665c8c8b45bb2a/desert_skill_icon.png) | _earned_ | Large area of attack ranged projectile. |
| Ice Breath | tba | _earned_ | tbc. |
| Heal | ![health_skill_icon](uploads/8555003db84fff897efeebea0feae616/health_skill_icon.png) | _earned_ (Swamp Dungeon) | Heals the player. |
| Firebomb | ![volcano_skill_icon](uploads/32583d644d851aabf401e5ec39dbc470/volcano_skill_icon.png) | Fire Knight | Spawns explosions around the player causing massive damage. Long cooldown. |
| Watershield | ![watershield_icon](uploads/c4a50e70b820b486b2e28a4499491d58/watershield_icon.png) | Water Knight | Creates a protective bubble around the player temporarily preventing incoming damage. |

## Gaining Skills
### Initial Skills
When the player picks their polyhedron, they are granted 2 skills. 1 Wizard Skill, and 1 Knight Skill (aka the Ultimate). If you pick the fire polyhedron, you are granted the Fireball skill and the Firebomb skill. If you pick the water polyhedron, you are granted the Iceball skill and the Watershield skill.

Use the _setWizard_ and _setKnight_ methods in PlayerManager to do this. Also note, you should ***always*** reset the player before doing this, as the PlayerManager is persistent until the game is closed.

    // Example of setting the Fire Polyhedron
    PlayerManager pm = GameManager.getManagerByInstance(PlayerManager.class);
    pm.resetPlayer();
    pm.setWizard(Wizard.FIRE);
    pm.setKnight(Knight.FIRE);

### Earned Skills
Through the game, skills are earned in 2 ways. First, upon killing a boss you will gain a skill related to that boss. For example, killing the Tundra boss grants the Icebreath skill. Secondly, completing puzzles in dungeons can grant a skill.

Use the _grantWizardSkill_ method in PlayerManager to do this. In this case, it is important not to reset the player, or their other skill acquisition progress will be lost.

    // Example of granting the Sting skill
    GameManager.getManagerByInstance(PlayerManager.class).grantWizardSkill(WizardSkill.STING);

## Adding New Skills
1) Create your new skill (including the tasks, and skill class).
2) Add your skill name to either the KnightSkills or WizardSkills enum (whichever is appropriate).
3) In PlayerSkills, find the relevant function getNewKnightSkill or getNewWizardSkill.
4) Add a new case to the switch statement for your skill.

## Implementation
This system relies on two major classes. The PlayerManager is a persistent manager class that the PlayerPeon refers to during its constructor to set base values, such as currently acquired skills. The PlayerSkills class is a utility class for getting a list of skills associated with a given Polyhedron, or for creating new instances of AbstractSkills from a skill name.

### UML Diagram for PlayerManager
![image](uploads/2e9f290e3d8491ac729b8831d1cf6e43/image.png)

### UML Diagram for PlayerSkills
![image](uploads/d124a27405941b65e6b3974dcd49e77a/image.png)