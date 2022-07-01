package cc.tweaked_programs.injectgold.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class InjectRecipe implements Recipe<Inventory> {
    private final ItemStack input;
    private final ItemStack output;
    private final int fuelAmount;
    private final int amount;
    private final Identifier id;

    public InjectRecipe(ItemStack input, ItemStack output, int fuelAmount, int amount, Identifier id) {
        this.input = input;
        this.output = output;
        this.fuelAmount = fuelAmount;
        this.amount = amount;
        this.id = id;
    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        if(inv.size() < 1) return false;
        return input.getItem() == inv.getStack(0).getItem();
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public int getAmount() {
        return amount;
    }

    public int getFuelAmount() { return fuelAmount; };

    @Override
    public Identifier getId() {
        return id;
    }



    public static class Type implements RecipeType<InjectRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "inject_recipe";
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InjectRecipeSerializer.INSTANCE;
    }
}

