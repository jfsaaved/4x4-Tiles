# Momo
Creating an Android game from scratch using the libgdx framework.


### The Game
The idea of the game is to show a pattern (selected from a list of patterns) to a user and the user has to imitate the same pattern. User gets points by hitting the right tiles. Running out of time or hitting the wrong tiles result in a game over.

### The Patterns
The patterns are saved in a text file in the asset folder. There are currently 70+ patterns.      
The format of a pattern is:     
[name of pattern],[1st tile row],[1st tile col],[2nd tile row],[2nd tile col],...,[16th tile row],[16th tile col];    
A semicolon (;) indicates the end of a pattern.

In **PlayState.java** patterns/level are created via **initPattern(int)**.

### The Sounds
The sounds are saved inside a folder in the asset folder. They are named sound#.wav where # = 1-16.
**loadSoundPack(String)** loads the String (folder's name) sounds.


### Mechanics    
TBD....
