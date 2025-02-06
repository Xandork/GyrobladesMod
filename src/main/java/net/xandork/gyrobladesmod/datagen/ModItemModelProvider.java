package net.xandork.gyrobladesmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GyrobladesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.GYRITE_CRYSTAL.get());
        basicItem(ModItems.RAW_GYRITE.get());

        //basicItem(ModItems.STONE_BEIGOMA.get());
        basicItem(ModItems.RIPCORD.get());

        basicItem(ModItems.BAI_SHELL.get());
        basicItem(ModItems.SHELL_BEIGOMA.get());
        basicItem(ModItems.GYRO_ARENA_ITEM.get());


    }
}
