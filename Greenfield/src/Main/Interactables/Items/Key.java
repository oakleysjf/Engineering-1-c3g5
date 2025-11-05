package Main.Interactables.Items;

import Main.Interactables.Obstacles.Door;

public class Key extends Item {

    public Key(String name) {
        super(name);
    }

    /**
     * Unlocks the specified door if it is locked.
     */
    public void unlock(Door door) {
        if (door.isLocked()) {
            door.unlock(this);
        }
    }
}
