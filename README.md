# Android Game
Creating an Android game from scratch using the libgdx framework.

### The Game
The idea of the game is to show a pattern (selected from a list of patterns) to a user and the user has to imitate the same pattern. User gets points by hitting the right tiles. Running out of time or hitting the wrong tiles result in a game over.

### Assets

#### Patterns
There are over 70+ patterns so far. They're all saved in a text file inside the asset folder.    
The format for the pattern is:    

> [name of level/pattern],[1st tile row],[1st tile col],[2nd tile row],[2nd tiel col],...,[16th tile row],[16th tile col];     

A semicolon (;) indicates the end of a pattern.

#### Sounds
Each tile has their own sound. These sounds are saved inside a folder inside the asset folder. Their named sound#.wav where # = 1-16 (corresponding to the number of a tile).

#### Font and Tiles
I used [texture packer](https://code.google.com/p/libgdx-texturepacker-gui/) to package the font and tiles    
These can be changed to anything. In the code the default regions are:
- **light** for the selected/hit tiles
- **dark** for the default tiles
- **fontsheet** for the font sprite sheet

### Running
- [Setup your development environment](https://github.com/libgdx/libgdx/wiki)
- Clone the repository or download and extract the ZIP file
- Import project into preferred development environment
