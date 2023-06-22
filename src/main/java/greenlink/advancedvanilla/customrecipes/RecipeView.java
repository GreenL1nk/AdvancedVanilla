package greenlink.advancedvanilla.customrecipes;


import org.bukkit.Material;

public class RecipeView {

    public enum RecipeType{
        CRAFT,
        FURNACE,
        DISABLE
    };
    private Material[] from;
    private Material result;
    private int resultCount;
    private RecipeType recipeType;

    public RecipeView(Material[] from, Material result, int resultCount, RecipeType recipeType){
        this.from = from;
        this.result = result;
        this.resultCount = resultCount;
        this.recipeType = recipeType;
    }

    public Material[] getFrom() {
        return from;
    }

    public Material getResult() {
        return result;
    }

    public int getResultCount() {
        return resultCount;
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }
}
