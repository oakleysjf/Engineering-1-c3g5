package Main.Interactables.Obstacles;

import Main.Interactables.Items.Key;

public class Door extends Interactable {

    private boolean locked = true;
    private Key requiredKey;

    public Door(Key requiredKey) {
        this.requiredKey = requiredKey;
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock(Key key) {
        // Compare by name to allow different Key instances with the same logical key name
        if (key != null && requiredKey != null && key.getName() != null && key.getName().equals(requiredKey.getName())) {
            locked = false;
        }
    }

    @Override
    public void interact() {}
}
