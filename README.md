This was my first ever personal project since learning I started learning computer science, the project is written in Object oriented programming design in java. UI and graphical element is done using JSwing library.

To play the game, clone the repo and run the VirtualPetSimulator.jar exe.

# *Virtual Pet Simulator*

## Enjoy the experience of having a pet virtually.

Features planned to implement:
- obtain different kinds of pet with randomize seed, perhaps introduce rarity/achievement later on.
- condition monitor system(display closeness, hungriness, etc.)
- pet keeping interactions through buttons

## Overview of program structure:

Program follows OOP design. Basically cat and dog are all class with parent class of animal. Player class can have collection of animals and functions to interact with animals.
Event log is used to keep track of all player actions.
Save and load is done through Json library.
Graphical element, UI features are done through JSwing library.


The application will simulate a normal pet's behavior by having several state it can be in such as relaxing, wandering around, sleeping etc.
User will be able to interact the pet with food, petting , play mini games with them.

## Project progress

## User Stories Phase 1:

- As a user, I want to be able to add multiple pets to my house.
- As a user, I want to be able to feed food to pet.
- As a user, I want to be able to change player, pet name.
- As a user, I want to be able to view my pet collection and their condition.
- 
## User Stories Phase 2:

- As a user, I want to be able to save my game through save option.
- As a user, I want to be able to reload into my game.

## User Stories Phase 3:
- As a user, I want to be able to add pet to my pet collection from a button.
- As a user, I want to be able to feed food to pet with feed button.
- As a user, I want to be able to save/load my game through button.
- As a user, I want to be able to see pets condition on screen.

## Phase 4: Tasks implemented

- Added event log to keep record of all actions.


## Reflection & improvement on programming practices:
I think I could've refactored out the pet class, and just have player with access to a collection of animal instead.
Right now pet class acts as a way to choose to produce either Cat/Dog objects. However, I could modify animal class
so that a type parameter is passed in when constructing an animal object which leads to choosing the specified
type(cat or dog) to create such object.
