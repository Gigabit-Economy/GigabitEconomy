# GigabitEconomy


## GamePlay Instruction

### Description
You are a delivery driver aiming to do your round. Deliver to all the houses you can without falling victim to the dangers of suburbia. If worst comes to worst though you can always _borrow_ what the content of your parcels. Just remember in the gig-economy the deliveries you make (and the ones you don't) affect the score your company gives you at the end of the day. 

### Controls
- move using `W/A/S/D` or the `arrow-keys`
- attack with `spacebar`
- open parcels with `tab`
- open the pause menu with `esc`

## Dev Info

### Branch policy
**No commits should be made directly to main branch.**

- Commit to a feature branch *(feature/[name])* and then open a pull request once functionality is complete
- Remember to make clear commit messages and add comments and updated to the GitHub issues when appropriate
- Do not independently push / merge to main without having your code reviewed
- Remember to interact with and update the issue pages. This helps keep a clear log of progress

### File breakdown 
- **GigabitEconomy.java** is used to control what's actually shown and active essentially and extends the Game LibGDX class. This is where we set the active screen etc.
- **screens/LevelScreen.java** is used as an abstract class for all level classes to inherit from (includes all the code shared between levels such as hit collision and adding background textures etc). _screens/MenuScreen_ doesn't inherit from it as it is just a Scene2d UI screen
- **sprites/tiled/MovingSprite.java** is used as an abstract class for all moving sprite classes (currently Player and Enemy) to inherit from
- **sprites/tiled/StaticSprite.java** is used as a superclass for all static sprites, but unlike *MovingSprite* can be instantiated itself without any subclass
- **sprites/GameObject.java** is used as a superclass for any game objects using screen coordinates as opposed to tiles for placement; these are always static and cannot be moved once first placed

### Adding elements to a level
Each level should extend the _LevelScreen_ class, passing the following arguments to super()'s constructor:
- **director**          the instance of the game director (GigabitEconomy.java)
- **player**            the player character for the level (Player)
- **enemies**           an ArrayList containing all enemy characters for the level
- **staticSprites**     an ArrayList containing all static sprites, including Houses, for the level
- **backgroundTexture** the background graphic (png texture) of the level

Each of these should be instantiated as constant properties in your subclass to LevelScreen.

The screen can then be added to the _game director (GigabitEconomy.java)_ class's `create()` method by adding it to the `screens` hashmap. To show the screen to the user, use `switchScreen()` in the same game director class, passing the name of the screen as defined in the hashmap.
