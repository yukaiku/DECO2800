# **Description:** 
The currency are used to buy items including weapons, armors, spells, consumables and miscellaneous items, after kill the boss and finish tutorial, player will corresponding coins

### Implementation
The implementation of currency is wholy managed within the `LoadedPeon` class (that the `PlayerPeon` class extends). All of the currency-manipulation methods are static to simplify access to the player of the current world.

### Public Methods
Retrieve the current value of the player's wallet.
```java
- public static float checkBalance()
```

Debit (take) money from the player's wallet. This is useful when purchasing items or losing money on death etc.
```java
- public static void debit(float amount)
```

Credit (add) money to the player's wallet. This is useful when killing monsters and receiving money on their death.
```java
- public static void credit(float amount)
```

Take money from the player. This method is used in conjunction with `debit` as a helper method.
```java
- public takeMoney(float amount)
```

Give money to the player. This method is used in conjunction with `credit` as a helper method.
```java
- public addMoney(float amount)
```