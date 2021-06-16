# ***Combat Attack Tasks***
Combat Attack Tasks existing to:
1) Spawn projectiles for attacks;
2) Perform any non-projectile based logic; and
3) Perform attack related logic over time.

## Rationale
This class is separated from the Skill class to provide better encapsulation, and provide the potential for multiple skills to use the same base AttackTasks. It also provides a mean to execute the logic over time, this is utilised for example in the IceBreathAttack.

## Existing Attacks
| ***Task Name*** | ***Javadoc Link*** |
| --------------- | ------------------ |
| FireballAttackTask | _unavailable_ |
| FireBombAttackTask | _unavailable_ |
| IceballAttackTask | _unavailable_ |
| MeleeAttackTask | _unavailable_ |
| RangedAttackTask | _unavailable_ |
| SandTornadoAttackTask | _unavailable_ |
| ScorpionStingAttackTask | _unavailable_ |

* _Note: at the time of writing Jenkins was unavailable, preventing linking to Javadoc pages._