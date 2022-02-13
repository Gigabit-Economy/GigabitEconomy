# GigabitEconomy
## Branch policy
**No commits should be made directly to main branch.**

- Commit to a feature branch *(feature/[name])* and then open a pull request once functionality is complete
- Remember to make clear commit messages and add comments and updated to the GitHub issues when appropriate
- Do not independently push / merge to main without having your code reviewed
- Remember to interact with and update the issue pages. This helps keep a clear log of progress

## File breakdown 
- **GigabitEconomy.java** is used to control what's actually shown and active essentially and extends the Game LibGDX class. This is where we set the active screen etc.
- **screens/LevelScreen.java** is used as an abstract class for all level classes to inherit from (includes all the code shared between levels such as hit collision and adding background textures etc). _screens/MenuScreen_ doesn't inherit from it as it is just a Scene2d UI screen
- **sprites/tiled/MovingSprite.java** is used as an abstract class for all moving sprite classes (currently Player and Enemy) to inherit from
- **sprites/tiled/StaticSprite.java** is used as a superclass for all static sprites, but unlike *MovingSprite* can be instantiated itself without any subclass\
- **sprites/GameObject.java** is used as a superclass for any game objects using screen coordinates as opposed to tiles for placement; these are always static and cannot be moved once first placed
