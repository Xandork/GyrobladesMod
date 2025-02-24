package net.xandork.gyrobladesmod.recipe;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.NonNullList;
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
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CustomRecipeBuilder {
    private final String group;
    private final CraftingBookCategory category;
    private final Item result;
    private final int outputCount;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, CriterionTriggerInstance> unlockCriteria = new HashMap<>();

    public CustomRecipeBuilder(String group, CraftingBookCategory category, Item result, int outputCount) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.outputCount = outputCount;
    }

    public CustomRecipeBuilder requires(TagKey<Item> tag) {
        ingredients.add(Ingredient.of(tag));
        return this;
    }


    public CustomRecipeBuilder unlockedBy(String key, CriterionTriggerInstance criterion) {
        unlockCriteria.put(key, criterion);
        return this;
    }

    public void save(RecipeOutput pRecipeOutput, String id) {
        /*NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(ModTags.Items.RING_TAG))));
        ingredients.add(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(ModTags.Items.DISK_TAG))));
        ingredients.add(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(ModTags.Items.DRIVER_TAG))));
        ingredients.add(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(ModTags.Items.BLADE_TAG))));*/

        ItemStack resultStack = new ItemStack(result, outputCount);

        Recipe<?> recipe = new GyrobladeRecipe(
                group,
                CraftingBookCategory.MISC,
                ingredients, // Uses tags now
                resultStack
        );

        pRecipeOutput.accept(ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, id), recipe, null);

        System.out.println("Generated Ingredients: " + ingredients);
    }





    public static CriterionTriggerInstance has(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item).triggerInstance();  // Correct trigger instance
    }
}