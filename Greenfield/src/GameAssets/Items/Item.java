package GameAssets.Items;

public abstract class Item {
    public String name;
    
    public Item(String name){
        this.name = name;
    }

    
    /**
     * Activate the ability of the item. 
     */
    public void useAbility(){
        this.itemAbility();
    }

    /**
     * Defines the behaviour of the item.
     */
    public abstract void itemAbility();


}
