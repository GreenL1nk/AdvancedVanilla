package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.PlayerManager;
import lib.utils.MyObservable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TradingItem extends MyObservable {
    private Material material;
    private int count;
    private int basePrice;

    private int nowTradeLevel;
    /**
     * [0] - |nowTradeLevel| = 1
     * [1] - |nowTradeLevel| = 2
     * [2] - |nowTradeLevel| = 3
     */
    private int[] amplitudes;
    private boolean canBuy;
    private boolean canSell;
    private long lastPriceChange;
    private int leftForLevelChange;
    private long[] timeAmplitude;

    public TradingItem(Material material, int count, int basePrice, int[] amplitudes, boolean canBuy, boolean canSell, long[] timeAmplitude) {
        this.material = material;
        this.count = count;
        this.basePrice = basePrice;
        this.amplitudes = amplitudes;
        this.canBuy = canBuy;
        this.canSell = canSell;
        this.timeAmplitude = timeAmplitude;

        nowTradeLevel = 0;
        leftForLevelChange = 0;
    }

    /**
     * @param player trader
     * @param singleItem true - one Item, false - stack
     * @return true, if trade some items, false if nothing changes
     */


    public boolean tryBuyitem(Player player, boolean singleItem){
        //System.out.println(this.material + " " + canBuy);
        if (!canBuy) return false;
        int test = 0;
        //System.out.println("tgg" + ++test);
        int countOfBuyingItems = 1;

        if (!singleItem) countOfBuyingItems = Math.min(64, amplitudes[Math.abs(nowTradeLevel)]-leftForLevelChange );
        //System.out.println("tgg" + ++test);
        if ( !PlayerManager.getInstance().getPlayer(player.getUniqueId()).takeMoney(getNowBuyPrice()*countOfBuyingItems) ) return false;
        //System.out.println("tgg" + ++test);
        leftForLevelChange+=countOfBuyingItems;

        if ( Math.abs(leftForLevelChange) == amplitudes[Math.abs(nowTradeLevel)]) {
            lastPriceChange = System.currentTimeMillis();
            if ( nowTradeLevel < 3)  {
                nowTradeLevel++;
            }
            leftForLevelChange = 0;
        }

        HashMap<Integer, ItemStack> items = player.getInventory().addItem(new ItemStack(this.material, this.count*countOfBuyingItems));
        Location location = player.getLocation();
        Set<Map.Entry<Integer, ItemStack>> entries = items.entrySet();
        int dropingItems = 0;
        for (Map.Entry<Integer, ItemStack> entry : entries) {
            dropingItems = entry.getKey();
        }
        if (dropingItems != 0) location.getWorld().dropItemNaturally(location, new ItemStack(this.material, dropingItems));
        notifyObservers();
        return true;
    }

    public boolean trySellItem(Player player, boolean singleItem){
        if (!canSell) return false;

        int countOfBuyingItems = 1;

        if (!singleItem) countOfBuyingItems = Math.min(64, amplitudes[Math.abs(nowTradeLevel)]+leftForLevelChange );
        if ( !player.getInventory().contains(this.material, countOfBuyingItems * count)) return false;
        leftForLevelChange-=countOfBuyingItems;

        PlayerManager.getInstance().getPlayer(player.getUniqueId()).addMoney(countOfBuyingItems * (this.getNowBuyPrice()-1));
        player.getInventory().removeItem(new ItemStack(this.material, countOfBuyingItems * count));

        if ( Math.abs(leftForLevelChange) == amplitudes[Math.abs(nowTradeLevel)]) {
            lastPriceChange = System.currentTimeMillis();
            if ( nowTradeLevel > -3)  {
                nowTradeLevel--;
            }
            leftForLevelChange = 0;
        }



        notifyObservers();
        return true;
    }

    public int getNowBuyPrice(){
        return basePrice * ((100 + 15*( (-1)* nowTradeLevel ))/10)/10;
    }

    public boolean timeCheck(){
        if (nowTradeLevel!=0 && System.currentTimeMillis() - lastPriceChange > timeAmplitude[Math.abs(nowTradeLevel)-1] ) {
            if (nowTradeLevel < 0) {
                nowTradeLevel++;
            } else {
                nowTradeLevel--;
            }
            leftForLevelChange = 0;
            lastPriceChange = System.currentTimeMillis();
            this.notifyObservers();
            return true;
        } else
            return false;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public boolean isCanSell() {
        return canSell;
    }

    public int getLeftForLevelChange() {
        return leftForLevelChange;
    }

}
