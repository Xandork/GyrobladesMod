package net.xandork.gyrobladesmod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeManager;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("listrecipes")
                .executes(context -> {
                    MinecraftServer server = context.getSource().getServer();
                    RecipeManager recipeManager = server.getRecipeManager();

                    System.out.println("=== Loaded Recipes ===");
                    recipeManager.getRecipes().forEach(recipe ->
                            System.out.println(recipe.id()));

                    return 1;
                })
        );
    }
}