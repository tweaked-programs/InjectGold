package cc.tweaked_programs.injectgold.recipe;

import cc.tweaked_programs.injectgold.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InjectRecipeSerializer implements RecipeSerializer<InjectRecipe> {
    private InjectRecipeSerializer() { }
    public static final InjectRecipeSerializer INSTANCE = new InjectRecipeSerializer();
    public static final Identifier ID = new Identifier(Main.MOD_ID, "inject_recipe");

    static class InjectRecipeJsonFormat {
        String input;
        String output;
        int outputAmount;
        int fuelAmount;
    }

    @Override
    public InjectRecipe read(Identifier id, JsonObject json) {
        InjectRecipeJsonFormat recipeJson = new Gson().fromJson(json, InjectRecipeJsonFormat.class);

        // Validate all fields are there
        if (recipeJson.input == null || recipeJson.output == null)
            throw new JsonSyntaxException("A input/output attribute is missing!");
        // Required Gold can be between 1 and 128 + a minimum of one output is given
        if (recipeJson.outputAmount < 1) recipeJson.outputAmount = 1;
        if (recipeJson.fuelAmount < 1) recipeJson.fuelAmount = 1;
        else if (recipeJson.fuelAmount > 128) recipeJson.fuelAmount = 128;

        Item inputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.input))
                .orElseThrow(() -> new JsonSyntaxException("No such item: " + recipeJson.input));
        ItemStack input = new ItemStack(inputItem, 1);

        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.output))
                .orElseThrow(() -> new JsonSyntaxException("No such item: " + recipeJson.output));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new InjectRecipe(input, output, recipeJson.fuelAmount, recipeJson.outputAmount, id);
    }
    @Override
    public void write(PacketByteBuf packetData, InjectRecipe recipe) {
        packetData.writeItemStack(recipe.getInput());
        packetData.writeItemStack(recipe.getOutput());
        packetData.writeInt(recipe.getFuelAmount());
        packetData.writeInt(recipe.getAmount());
    }

    @Override
    public InjectRecipe read(Identifier id, PacketByteBuf packetData) {
        ItemStack input = packetData.readItemStack();
        ItemStack output = packetData.readItemStack();
        int fuelAmount = packetData.readInt();
        int outputAmount = packetData.readInt();
        return new InjectRecipe(input, output, fuelAmount, outputAmount, id);
    }
}