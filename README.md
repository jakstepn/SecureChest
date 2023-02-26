# SecureChest
## About
This spigot plugin is created to provide security for players resources. It allows users to craft a special chests which
both the location and combination are saved by the plugin in order to launch a protection mechanism 
like a 6 color wool pin when opened.
  
## Events
### OnBlockPlace
When placed block matches one of the craft-able chests it's location and password is saved in the JSON file.
### OnBlockDestroy
When destroyed block is a chest, and it is a secured chest it is removed from the secured chest list.
### OnChestOpen
When the chest is opened the gui with a password input field is opened.

## Functional requirements
* Chest can be secured with 1 to 8 wool combination.
* Chest can be destroyed with an explosion.
* Chest cannot be destroyed by someone else than its owner.
* Chest can be opened only by its owner.
* Chest cannot be looted with hoopers.
* Chest when moved by a piston stays protected.
* Protection can be removed from the chest.
* SecureChest can be placed only once.