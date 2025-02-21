package net.xandork.gyrobladesmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GyrobladesMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GYROBLADES_TAB = CREATIVE_MODE_TABS.register("gyroblades_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RIPCORD.get()))
                    .title(Component.translatable("creativetab.gyroblades_tab"))
                    .displayItems((pParameters,pOutput) -> {
                        pOutput.accept(ModItems.RIPCORD.get());
                        pOutput.accept(ModItems.STONE_BEIGOMA.get());
                        pOutput.accept(ModItems.SHELL_BEIGOMA.get());
                        pOutput.accept(ModItems.BAI_SHELL.get());
                        pOutput.accept(ModItems.RAW_GYRITE.get());
                        pOutput.accept(ModItems.GYRITE_CRYSTAL.get());
                        pOutput.accept(ModBlocks.GYRO_TABLE.get());
                        pOutput.accept(ModBlocks.GYRITE_ORE.get());
                        pOutput.accept(ModBlocks.GYRITE_DEEPSLATE_ORE.get());
                        pOutput.accept(ModBlocks.GYRITE_BLOCK.get());
                        pOutput.accept(ModBlocks.GYRO_ARENA_CORE.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> GYROBLADES_PARTS_TAB = CREATIVE_MODE_TABS.register("gyroblades_parts_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(PartItems.GOLD_RING1.get()))
                    .title(Component.translatable("creativetab.gyroblades_tab"))
                    .displayItems((pParameters,pOutput) -> {
                        PartItems.ITEMS.getEntries().forEach(item -> pOutput.accept(item.get()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
