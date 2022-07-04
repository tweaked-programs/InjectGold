package cc.tweaked_programs.injectgold.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static cc.tweaked_programs.injectgold.Main.MOD_ID;

public class RecipeRegister {
    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, InjectRecipeSerializer.ID, InjectRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, InjectRecipe.Type.ID), InjectRecipe.Type.INSTANCE);
    }
}
