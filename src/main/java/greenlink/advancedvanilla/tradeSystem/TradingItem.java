package greenlink.advancedvanilla.tradeSystem;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TradingItem {
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
            /*
            todo Checking having enought money and take them
             */
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
        }
        else {
            int countOfBuyingItems = Math.min(64, leftForLevelChange);
             /*
            todo Checking having enought money and take them
             */
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

        }



        return true;
    }

    private int getNowBuyPrice(){
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
            return true;
        } else
            return false;
    }

}
