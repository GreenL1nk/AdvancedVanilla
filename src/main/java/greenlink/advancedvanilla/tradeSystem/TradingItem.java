package greenlink.advancedvanilla.tradeSystem;

import greenlink.advancedvanilla.PlayerManager;
import lib.utils.MyObservable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Класс описывающий поведение предметов сделок в жителях. <br>
 * У покупок есть лимиты - {@link TradingItem#amplitudes}, после которых цена повышается на 10%. <br>
 * При этом цена продажи всегда меньше на 1 чем цена покупки <br>
 * Также через временные промежутки - {@link TradingItem#timeAmplitude} цены стремятся вернуться к {@link TradingItem#basePrice}
 */
public class TradingItem extends MyObservable {
    private Material material;
    private int count;
    private int basePrice;

    /**
     * [0] - |nowTradeLevel| = 1
     * [1] - |nowTradeLevel| = 2
     * [2] - |nowTradeLevel| = 3
     */
    private int nowTradeLevel;

    private int[] amplitudes; // амплитуды колличеств предметов для изменения уровня цен
    private boolean canBuy;
    private boolean canSell;
    private long lastPriceChange;
    private int leftForLevelChange;
    private long[] timeAmplitude; // временные амплитуды изменения цен предметов к базовой цене

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
     * Описывает попытку игроком купить предмет
     *
     * @param player  покупатель
     * @param singleItem true - если пытается купить 1 предмет, false - пытается купить стак
     * @return true - если сделка прошла успешно, false - ничего не куплено
     */
    public boolean tryBuyItem(Player player, boolean singleItem) {

        if (!canBuy) return false;
        int test = 0;
        int countOfBuyingItems = 1;

        if (!singleItem) countOfBuyingItems = Math.min(64, amplitudes[Math.abs(nowTradeLevel)] - leftForLevelChange);
        if (!PlayerManager.getInstance().getPlayer(player.getUniqueId()).takeMoney(getNowBuyPrice() * countOfBuyingItems))
            return false;
        leftForLevelChange += countOfBuyingItems;

        if (Math.abs(leftForLevelChange) == amplitudes[Math.abs(nowTradeLevel)]) {
            lastPriceChange = System.currentTimeMillis();
            if (nowTradeLevel < 3) {
                nowTradeLevel++;
            }
            leftForLevelChange = 0;
        }

        HashMap<Integer, ItemStack> items = player.getInventory().addItem(new ItemStack(this.material, this.count * countOfBuyingItems));
        Location location = player.getLocation();
        Set<Map.Entry<Integer, ItemStack>> entries = items.entrySet();
        int dropingItems = 0;
        for (Map.Entry<Integer, ItemStack> entry : entries) {
            dropingItems = entry.getKey();
        }
        if (dropingItems != 0)
            location.getWorld().dropItemNaturally(location, new ItemStack(this.material, dropingItems));
        notifyObservers();
        return true;
    }

    /**
     * @param player продавец
     * @param singleItem true - если пытается продать 1 предмет, false - пытается продать стак
     * @return если сделка прошла успешно, false - ничего не продано
     */
    public boolean trySellItem(Player player, boolean singleItem) {
        if (!canSell) return false;

        int countOfBuyingItems = 1;

        if (!singleItem) countOfBuyingItems = Math.min(64, amplitudes[Math.abs(nowTradeLevel)] + leftForLevelChange);
        if (!player.getInventory().contains(this.material, countOfBuyingItems * count)) return false;
        leftForLevelChange -= countOfBuyingItems;

        PlayerManager.getInstance().getPlayer(player.getUniqueId()).addMoney(countOfBuyingItems * (this.getNowBuyPrice() - 1));
        countOfBuyingItems *= count;
        for (ItemStack content : player.getInventory().getContents()) {
            if (content != null && content.getType() == this.material) {
                //todo lore check
                int temp = Math.min(content.getAmount(), countOfBuyingItems);
                content.setAmount(content.getAmount() - temp);
                countOfBuyingItems -= temp;
                if (countOfBuyingItems == 0) break;
            }
        }
        //player.getInventory().removeItem(new ItemStack(this.material, countOfBuyingItems * count));

        if (Math.abs(leftForLevelChange) == amplitudes[Math.abs(nowTradeLevel)]) {
            lastPriceChange = System.currentTimeMillis();
            if (nowTradeLevel > -3) {
                nowTradeLevel--;
            }
            leftForLevelChange = 0;
        }

        notifyObservers();
        return true;
    }

    /**
     * @return текущая цена покупки предмета, рассчитанная от {@link TradingItem#nowTradeLevel}
     */
    public int getNowBuyPrice() {
        return basePrice * ((100 + 15 * (nowTradeLevel)) / 10) / 10;
    }

    /**
     * @return тикающий метод для проверки времени обновления {@link TradingItem#nowTradeLevel}.
     */
    public boolean timeCheck() {
        if (nowTradeLevel != 0 && System.currentTimeMillis() - lastPriceChange > timeAmplitude[Math.abs(nowTradeLevel) - 1]) {
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

    public int getNowAmplitude() {
        //System.out.println(nowTradeLevel + "nowTradeLevel");
        return amplitudes[Math.abs(nowTradeLevel)];
    }

}
