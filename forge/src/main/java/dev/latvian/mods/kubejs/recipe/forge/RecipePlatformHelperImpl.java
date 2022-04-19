package dev.latvian.mods.kubejs.recipe.forge;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.server.KubeJSReloadListener;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ConditionContext;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.Map;

public class RecipePlatformHelperImpl {

	public static Recipe<?> fromJson(RecipeJS self) throws Throwable {
		return self.type.serializer.fromJson(self.getOrCreateId(), self.json, (ICondition.IContext) KubeJSReloadListener.recipeContext);
	}

	public static Ingredient getCustomIngredient(JsonObject object) {
		return CraftingHelper.getIngredient(object);
	}

	public static boolean processConditions(RecipeManager recipeManager, JsonObject json, String key) {
		return !json.has(key) || CraftingHelper.processConditions(json, key, (ICondition.IContext) KubeJSReloadListener.recipeContext);
	}

	public static void pingNewRecipes(Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> map) {
	}

	public static Object createRecipeContext(ReloadableServerResources resources) {
		try {
			ICondition.IContext context = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, resources.getRecipeManager(), "context");

			if (context != null) {
				return context;
			}
		} catch (Exception ex) {
			ConsoleJS.SERVER.warn("Failed to get RecipeManager.context through reflection, falling back to new ConditionContext()");
		}

		return new ConditionContext(resources.tagManager);
	}
}
