package net.xandork.gyrobladesmod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LayeredItem extends Item {

    //private final ToolDefinition toolDefinition;

    private ItemStack toolForRendering;

    public LayeredItem(Properties pProperties) {
        super(pProperties);
    }
    //@Override
    public ItemStack getRenderTool() {
        if (toolForRendering == null) {
            //toolForRendering = ToolBuildHandler.buildToolForRendering(this, this.getToolDefinition());
        }
        return toolForRendering;
    }
}
