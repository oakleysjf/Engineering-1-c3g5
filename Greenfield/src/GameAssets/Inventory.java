package GameAssets;

import java.util.List;

import GameAssets.Items.Item;

import java.util.HashMap;

public class Inventory {
    public List<HashMap<String, Item>> inventory;

    public Inventory(List<HashMap<String, Item>> inventory){
        this.inventory = inventory;
    }

    /**
     * @param item item to check if the player has it in their inventory.
     * @return true if yes, false if no.
     */
    @SuppressWarnings("unlikely-arg-type")
    boolean isInInventory(Item item){
        if(this.inventory.contains(item)){
            return true;
        }else{return false;}
    }

    /**
     * Empties the inventory of the player.
     */
    void clearInventory(){
        this.inventory.clear();
    }

    void removeItem(Item item){
    }

}
