package net.xandork.gyrobladesmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GyrobladesMod.MOD_ID);

    public static final RegistryObject<EntityType<BeigomaEntity>> BEIGOMA =
            ENTITY_TYPES.register("beigoma", () -> EntityType.Builder.<BeigomaEntity>of(BeigomaEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("beigoma"));

    public static final RegistryObject<EntityType<BeigomaProjectileEntity>> BEIGOMAPROJECTILE =
            ENTITY_TYPES.register("beigoma_projectile", () -> EntityType.Builder.<BeigomaProjectileEntity>of(BeigomaProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("beigoma_projectile"));

    public static void register(IEventBus eventBus) {ENTITY_TYPES.register(eventBus);}
}
