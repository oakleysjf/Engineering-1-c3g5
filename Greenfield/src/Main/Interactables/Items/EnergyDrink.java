package Main.Interactables.Items;

import Main.Player;
import java.util.Timer;
import java.util.TimerTask;

public class EnergyDrink extends Item {
    public EnergyDrink(String name, int x, int y) {
        super(name, x, y);
    }

    public void interact(Player player) {
        int originalSpeed = getPlayerSpeed(player);
        setPlayerSpeed(player, originalSpeed + 1);
        removeFromMap();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setPlayerSpeed(player, originalSpeed);
            }
        }, 5000); // 5 seconds
    }

    private int getPlayerSpeed(Player player) {
        try {
            java.lang.reflect.Field speedField = Player.class.getDeclaredField("speed");
            speedField.setAccessible(true);
            return speedField.getInt(player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPlayerSpeed(Player player, int value) {
        try {
            java.lang.reflect.Field speedField = Player.class.getDeclaredField("speed");
            speedField.setAccessible(true);
            speedField.setInt(player, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
