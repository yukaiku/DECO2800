The Hotbar components comprised of two main parts:
- The first part displays the **5 wizard** skills along with a wizard icon of the left side.
- The second part has only **one** skill box which is the **mech/knight skill** or we could say the ultimate skill and we also have a knight icon stick with it as well 

# HotbarComponent

```java
        this.playerEntity = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();

        batch.begin();

        wizardIcon.setPosition(overlayRenderer.getX() + 0.15f * overlayRenderer.getWidth(), overlayRenderer.getY());
        wizardIcon.draw(batch);
        lastRenderedSprite = wizardIcon;

        this.renderSkills(batch, this.playerEntity.getWizardSkills());

        knightIcon.setPosition(lastRenderedSprite.getX() + lastRenderedSprite.getWidth() + 0.05f * overlayRenderer.getWidth(), lastRenderedSprite.getY());
        knightIcon.draw(batch);
        lastRenderedSprite = knightIcon;

        this.renderMechSkill(batch, this.playerEntity.getMechSkill());

        batch.end();
```

- In the main `render()` method which would be called continuously, we would assign the `playerEntity` to the main player in order to make it easier to access.

## User testing strategy
1. Choose the users who have experience in RPG game and have knowledge about skills system
2. Define the tasks for the observation:
   - Point out the position of the hotbar
   - Classify between wizard skill set and knight skill set
   - What is the current selected skill in the wizard skill set ?
   - Choose another skill in the wizard skill set
   - Use the ultimate/knight skill and looking for the cooldown
3. Take note during the user doing the observation
4. Prepare some related questions in case the use had difficulty during the interaction process
5. Say "Thank you" and start to arrange the collected details and prepare a new form for new user

# Documentation by @jayhuynh239
