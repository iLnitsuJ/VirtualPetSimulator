# *Virtual Pet Simulator*

## Enjoy the experience of having a pet virtually.

Features to implement:
- different kinds of pet with randomize seed(perhaps introduce rarity/achievement later on?).
- condition monitor system(display closeness, hungriness, etc.)
- pet interaction via text(graphical later on?)

The application will simulate a normal pet's behavior. User will be able to interact the pet with food, petting , play games with them and much more awesome stuff.


##User Stories P1:

- As a user, I want to be able to add multiple pets to my house.
- As a user, I want to be able to feed food to pet.
- As a user, I want to be able to change player, pet name.
- As a user, I want to be able to view my pet collection and their condition.
- 
##User Stories P2:

- As a user, I want to be able to save my game through save option.
- As a user, I want to be able to reload into my game.

##User Stories P3:
- As a user, I want to be able to add pet to my pet collection from a button.
- As a user, I want to be able to feed food to pet with feed button.
- As a user, I want to be able to save/load my game through button.
- As a user, I want to be able to see pets condition on screen.

##Phase 4: Task 2
Event log cleared.
yoshi is added to player's pet collection
jack is added to player's pet collection

If no events are logged when program runs, it could be that no new pets were added, or no food were fed
to pet during the time program runs. 

##Phase 4: Task 3
I think I could've refactored out the pet class, and just have player with access to a collection of animal instead.
Right now pet class acts as a way to choose to produce either Cat/Dog objects. However, I could modify animal class
so that a type parameter is passed in when constructing an animal object which leads to choosing the specified
type(cat or dog) to create such object.

