package net.xandork.gyrobladesmod.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GyrobladesMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, GyrobladesMod.MOD_ID);

    // Register the Recipe Serializer
    public static final RegistryObject<RecipeSerializer<GyrobladeRecipe>> GYROBLADE_RECIPE_SERIALIZER =
            SERIALIZERS.register("gyroblade_item", GyrobladeRecipeSerializer::new);

    // Register the Recipe Type
    public static final RegistryObject<RecipeType<GyrobladeRecipe>> GYROBLADE_RECIPE_TYPE =
            RECIPE_TYPES.register("gyroblade_item", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return GyrobladesMod.MOD_ID + ":gyroblade_item";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        System.out.println("[ModRecipes] Registered recipe type: " + GYROBLADE_RECIPE_TYPE.getId());
    }
}