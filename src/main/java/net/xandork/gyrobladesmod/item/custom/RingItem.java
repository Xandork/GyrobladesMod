package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.xandork.gyrobladesmod.item.client.ColorShiftedItemRenderer;

import java.util.function.Consumer;

public class RingItem extends Item {
    int attack;
    int health;
    int impact;
    int agility;
    int weight;
    public RingItem(Properties pProperties, int at, int he, int im, int ag, int we) {
        super(pProperties);
        attack = at;
        health = he;
        impact = im;
        agility = ag;
        weight = we;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
