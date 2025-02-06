package net.xandork.gyrobladesmod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;

public class GyrobladeEntity extends BeigomaEntity{
    public GyrobladeEntity(EntityType<? extends Wolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
