package net.xandork.gyrobladesmod.recipe;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CustomRecipeBuilder {
    private final String group;
    private final CraftingBookCategory category;
    private final Item result;
    private final int outputCount;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<String, CriterionTriggerInstance> unlockCriteria = new HashMap<>();

    public CustomRecipeBuilder(String group, CraftingBookCategory category, Item result, int outputCount) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.outputCount = outputCount;
    }

    // Add ingredients using tags
    public CustomRecipeBuilder requires(TagKey<Item> tag) {
        ingredients.add(Ingredient.of(tag));
        return this;
    }


    // Set unlock criteria
    public CustomRecipeBuilder unlockedBy(String key, CriterionTriggerInstance criterion) {
        unlockCriteria.put(key, criterion);
        return this;
    }

    // Save the recipe
    public void save(RecipeOutput pRecipeOutput, String id) {
        // Hardcode ingredients directly as Ingredient objects
        List<Ingredient> hardcodedIngredients = new ArrayList<>();
        hardcodedIngredients.add(Ingredient.of(ModItems.RING_ITEM.get()));
        hardcodedIngredients.add(Ingredient.of(ModItems.DISK_ITEM.get()));
        hardcodedIngredients.add(Ingredient.of(ModItems.DRIVER_ITEM.get()));
        hardcodedIngredients.add(Ingredient.of(ModItems.BLADE_ITEM.get()));

        // Create the ItemStack result with the output count
        ItemStack resultStack = new ItemStack(result, outputCount);

        // Create the recipe instance with hardcoded ingredients
        Recipe<?> recipe = new GyrobladeRecipe(
                group,
                CraftingBookCategory.MISC,
                hardcodedIngredients, // Directly hardcoded
                resultStack
        );

        // Use pRecipeOutput.accept() to register the recipe
        pRecipeOutput.accept(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, id), recipe, null);

        // Debugging: Print out the ingredients to verify they're being passed correctly
        System.out.println("Hardcoded Ingredients: " + hardcodedIngredients);
    }




    // Example for setting the unlockedBy condition using InventoryChangeTrigger
    public static CriterionTriggerInstance has(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item).triggerInstance();  // Correct trigger instance
    }
}