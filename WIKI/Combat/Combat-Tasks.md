## Overview
There are two potential tasks that the [Combat Manager](/combat/combat-manager) can set for an [Combat Entities](/combat/combat-entities) on tick; RangedAttackTask and MeleeAttackTask. Both tasks are designed to check whether the entity has collided with another entity and then apply damage to that entity if possible. The RangedAttackTask also moves the target towards a SquareVector destination.
## Class Diagram
![CombatTaskClassDiagram](uploads/560691ec3f11f4f0266c28b55c05aa29/CombatTaskClassDiagram.PNG)
## Improvements
- Allow Combat Tasks to detect if an entity is friendly or not
- Rework the RanagedAttackTask so that it no longer uses a duplicate implementation of the MovementTask path finding but moves the Combat Entity in a linear direction towards the destination