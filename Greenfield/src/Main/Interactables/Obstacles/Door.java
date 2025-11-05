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
        if (key.equals(requiredKey)) {
            locked = false;
        }
    }

    @Override
    public void interact() {}
}
