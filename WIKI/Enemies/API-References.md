## Table of Contents
[[_TOC_]]

# API Documentation
### Public API Methods
```java
public void setPosition(float x, float y)
```
Sets the position of the enemy to the given coordinates.

```java
public AgentEntity getTarget()
```
Returns the current entity being targeted by the enemy, or null if is there no target.

```java
public Texture getIcon()
```
Gets the current minimap icon of this enemy. 

```java
public void setTarget(AgentEntity target)
```
Sets the current AgentEntity being targeted to the target passed as a parameter.

```java
public void attackPlayer()
```
A call to this method will cause the enemy to attempt to attack its current target with a melee attack by creating a MeleeAttackTask if the current target is not null.
 
```java
public void deepCopy()
```
Depends on whether subclasses have implemented this method, may return null if not.\
If implemented, when called on a subclass of an EnemyPeon, returns an EnemyPeon of the same subclass with the same height, speed, maximum health and texture.

# UML Diagrams

### Enemy Dependency UML
![Package_enemies2](uploads/4c70d889d0c58b4e5546f7401b51fbb8/Package_enemies2.png)

### Enemy Package UML
![Package_enemies](uploads/a5f09b6760199f7d1fe0f769bafb4ffd/Package_enemies.png)

# Appendix

* **Last edited**: 25 Oct (Sprint 4)
* **Authors**: Yiyun Zhang (@fzmi), Wilson Yu (@wyu17)
