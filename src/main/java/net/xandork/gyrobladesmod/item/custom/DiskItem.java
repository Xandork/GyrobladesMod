package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.world.item.Item;

public class DiskItem extends Item {
    int attack;
    int health;
    int impact;
    int agility;
    int weight;
    public DiskItem(Properties pProperties, int at, int he, int im, int ag, int we) {
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
