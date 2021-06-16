# *Combat Skills*
## High Level Process Description
A combat skill is responsible for encapsulating:

* Instantiation of Combat Tasks
* Tracking the cooldown of skills
* Providing a means to draw a hotbar with Skill icons + cooldowns displayed
* Providing an extendable system for later features of the game.

This is provided through use of a Skill interface, which each type of Skill implements.

## Important Notes!
* Not sure if there are any

## How to use:
### Creating a new skill
1) Implement the Skill interface
2) Define the skill cooldown, and implement a cooldown tracker in the onTick method
3) When implementing the getNewSkillTask, return the relevant CombatAttackTask with appropriately set parameters.

**See the FireballSkill as an example.**

### Using a skill
1) In the constructor of the relevant Peon, create the new Skill.
2) When attempting to use the skill, first check the cooldown is <= 0, or else an exception will be thrown.
3) Set the Peon's combat task to skill.getNewSkillTask()

_e.g., Attacking with a fireball:_

    // Define a private field for the relevant Peon class
    private Skill fireball;
    
    // Initialise in the Peon's constructor
    fireball = new FireballSkill(this);


    // To use the attack, for example in the NotifyTouchDown method
    try {
        if (fireball.getCooldown() <= 0) {
            this.setCombatTask(fireball.getNewSkillTask(targetX, targetY));
        }
    } catch (SkillOnCooldownException e) {
        e.printStackTrace();
    }

## Implemented Class Structure for Combat Skills
### Current Implementation
_Skills Implementing Skill Interface:_

![image](uploads/24d4e1396575498b45c0617ea3f2a508/image.png)

## General remarks and comments
This system seems to adds another layer of abstraction to combat, however it is useful for keeping the code extendable. By using the Skill interface, many variations of attacks with different base damages, different cooldowns etc, can reuse existing Combat Tasks and Entities, but hiding the code behind a simple SkillName from the designers perspective.

## Potential Improvements
### Code Refactoring
* The current implementation of combat goes through several layers of abstraction. It is possible that reducing the abstraction may make the code more understandable for someone who is not already familiar with the combat system.
* Currently an exception is thrown when getNewSkillTask is called whilst the skill is on cooldown, which just leads to unnecessary exception handling.