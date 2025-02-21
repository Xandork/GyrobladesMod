package net.xandork.gyrobladesmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class GyrobladeRecipe extends CustomRecipe {
    public static final Codec<GyrobladeRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(GyrobladeRecipe::getGroup),
            CraftingBookCategory.CODEC.fieldOf("category").forGetter(GyrobladeRecipe::getCategory),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(GyrobladeRecipe::getIngredients),
            ItemStack.CODEC.fieldOf("result").forGetter(GyrobladeRecipe::getResult)
    ).apply(instance, GyrobladeRecipe::new));
    private final String group;
    private final CraftingBookCategory category;
    private final List<Ingredient> ingredients; // List of ResourceLocation for tags
    private final ItemStack result;
    // Constructor - Pass the crafting category to the superclass
    public GyrobladeRecipe(String group, CraftingBookCategory category, List<Ingredient> ingredients, ItemStack result) {
        super(category);
        this.group = group;
        this.category = category;
        this.ingredients = ingredients;
        this.result = result;

        System.out.println("GyrobladeRecipe created with ingredients: " + this.ingredients);
    }

    @Override
    public String getGroup() {
        return group;
    }

    public CraftingBookCategory getCategory() {
        return this.category;
    }
    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }
    public List<Ingredient> getTagIngredients() {
        return ingredients;
    }

    // Getter for result (this is the one that was missing)
    public ItemStack getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "GyrobladeRecipe{ingredients=" + ingredients + ", result=" + result + "}";
    }

    @Override
    public boolean matches(CraftingInput pInput, Level pLevel) {
        // Define the required ingredients (using Ingredient.of to handle tags or items)
        Ingredient ringIngredient = Ingredient.of(ModItems.RING_ITEM.get());
        Ingredient diskIngredient = Ingredient.of(ModItems.DISK_ITEM.get());
        Ingredient driverIngredient = Ingredient.of(ModItems.DRIVER_ITEM.get());
        Ingredient bladeIngredient = Ingredient.of(ModItems.BLADE_ITEM.get());

        if (pInput instanceof CraftingContainer craftingContainer) {
            boolean foundRing = false;
            boolean foundDisk = false;
            boolean foundDriver = false;
            boolean foundBlade = false;

            // Debugging: Print the grid contents
            System.out.println("Crafting Container Items:");
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack stack = craftingContainer.getItem(i);
                if (!stack.isEmpty()) {
                    // Get the registry name using ItemStack's getItem()
                    System.out.println("Slot " + i + ": " + stack.getItem().getDescriptionId());
                }
            }

            // Loop through the crafting grid (craftingContainer)
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack stack = craftingContainer.getItem(i);
                if (stack.isEmpty()) continue;  // Skip empty slots

                // Debugging: Print the item being tested
                System.out.println("Testing item: " + stack.getItem().getDescriptionId());

                // Test each stack against the ingredients
                if (ringIngredient.test(stack)) {
                    foundRing = true;
                } else if (diskIngredient.test(stack)) {
                    foundDisk = true;
                } else if (driverIngredient.test(stack)) {
                    foundDriver = true;
                } else if (bladeIngredient.test(stack)) {
                    foundBlade = true;
                }
            }

            // Debugging: Print whether all ingredients were found
            System.out.println("Found ingredients:");
            System.out.println("Ring: " + foundRing);
            System.out.println("Disk: " + foundDisk);
            System.out.println("Driver: " + foundDriver);
            System.out.println("Blade: " + foundBlade);

            // All ingredients must be found in the grid
            return foundRing && foundDisk && foundDriver && foundBlade;
        }

        return false;
    }





    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        ItemStack result = new ItemStack(ModItems.GYROBLADE_ITEM.get());

        // List to store texture paths
        List<ResourceLocation> texturesToMerge = new ArrayList<>();

        // Iterate over the crafting grid (pInput) and collect texture paths
        if (pInput instanceof CraftingContainer craftingContainer) {
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack ingredient = craftingContainer.getItem(i);
                if (!ingredient.isEmpty()) {
                    ResourceLocation itemID = ForgeRegistries.ITEMS.getKey(ingredient.getItem());
                    if (itemID != null) {
                        // Construct texture path for the ingredient
                        ResourceLocation texturePath = ResourceLocation.fromNamespaceAndPath(itemID.getNamespace(), "textures/item/" + itemID.getPath() + ".png");

                        // Add texture paths in the correct order: DRIVER -> DISK -> RING -> BLADE
                        if (ingredient.is(ModTags.Items.DRIVER_TAG)) {
                            texturesToMerge.add(texturePath); // Driver first
                        } else if (ingredient.is(ModTags.Items.DISK_TAG)) {
                            texturesToMerge.add(texturePath); // Disk second
                        } else if (ingredient.is(ModTags.Items.RING_TAG)) {
                            texturesToMerge.add(texturePath); // Ring third
                        } else if (ingredient.is(ModTags.Items.BLADE_TAG)) {
                            texturesToMerge.add(texturePath); // Blade fourth
                        }
                    }
                }
            }
        }

        // If we found textures, update the DataComponent
        if (!texturesToMerge.isEmpty()) {
            result.set(ModDataComponentTypes.TEXTURE_PATHS.get(), texturesToMerge);
        } else {
            // Fallback: If no textures found, set default textures
            List<ResourceLocation> defaultTextures = List.of(
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/iron_balance.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/stone_disk0.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/wooden_ring1.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/iron_claw.png")
            );
            result.set(ModDataComponentTypes.TEXTURE_PATHS.get(), defaultTextures);
        }

        return result;
    }










    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        // We assume it's a 3x3 grid for this recipe
        return pWidth == 3 && pHeight == 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        // Return the custom recipe serializer
        return ModRecipes.GYROBLADE_RECIPE_SERIALIZER.get();
    }
}