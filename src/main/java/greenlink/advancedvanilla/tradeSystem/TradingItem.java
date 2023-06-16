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
        leftForLevelChange = amplitudes[0];
    }

    /**
     * @param player trader
     * @param singleItem true - one Item, false - stack
     * @return true, if trade some items, false if nothing changes
     */


    public boolean tryBuyitem(Player player, boolean singleItem){
        if (!canBuy) return false;

        if (singleItem)
        {
            if ( !PlayerManager.getInstance().getPlayer(player.getUniqueId()).takeMoney(getNowBuyPrice()) ) return false;
            leftForLevelChange--;
            if (leftForLevelChange == 0) {
                lastPriceChange = System.currentTimeMillis();
                int temp = nowTradeLevel;
                if ( temp < 0 ) temp *=-1;
                if (temp != 3) {
                    nowTradeLevel--;
                }
                leftForLevelChange = amplitudes[Math.abs(nowTradeLevel)];
            }
            HashMap<Integer, ItemStack> items = player.getInventory().addItem(new ItemStack(this.material, this.count));
            Location location = player.getLocation();
            Set<Map.Entry<Integer, ItemStack>> entries = items.entrySet();
            int dropingItems = 0;
            for (Map.Entry<Integer, ItemStack> entry : entries) {
                dropingItems = entry.getKey();
            }
            if (dropingItems != 0) location.getWorld().dropItemNaturally(location, new ItemStack(this.material, dropingItems));
        }
        else {
            int countOfBuyingItems = Math.min(64, leftForLevelChange);
            if ( !PlayerManager.getInstance().getPlayer(player.getUniqueId()).takeMoney(getNowBuyPrice()*countOfBuyingItems) ) return false;
            leftForLevelChange-=countOfBuyingItems;

            if (leftForLevelChange == 0) {
                lastPriceChange = System.currentTimeMillis();
                int temp = nowTradeLevel;
                if ( temp < 0 ) temp *=-1;
                if (temp != 3) {
                    nowTradeLevel--;
                }
                leftForLevelChange = amplitudes[Math.abs(nowTradeLevel)];
            }
            HashMap<Integer, ItemStack> items = player.getInventory().addItem(new ItemStack(this.material, this.count*countOfBuyingItems));
            Location location = player.getLocation();
            Set<Map.Entry<Integer, ItemStack>> entries = items.entrySet();
            int dropingItems = 0;
            for (Map.Entry<Integer, ItemStack> entry : entries) {
                dropingItems = entry.getKey();
            }
            if (dropingItems != 0) location.getWorld().dropItemNaturally(location, new ItemStack(this.material, dropingItems));
        }
        return true;
    }

    public int getNowBuyPrice(){
        return basePrice * ((100 + 15*(nowTradeLevel))/10)/10;
    }

    public boolean timeCheck(){
        if (nowTradeLevel!=0 && System.currentTimeMillis() - lastPriceChange > timeAmplitude[Math.abs(nowTradeLevel)-1] ) {
            if (nowTradeLevel < 0) {
                nowTradeLevel++;
            } else {
                nowTradeLevel--;
            }
            leftForLevelChange = amplitudes[Math.abs(nowTradeLevel)];
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
