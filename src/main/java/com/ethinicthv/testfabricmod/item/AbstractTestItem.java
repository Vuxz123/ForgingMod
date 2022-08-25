package com.ethinicthv.testfabricmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public abstract class AbstractTestItem extends Item {
    public AbstractTestItem(Settings settings) {
        super(settings);
    }
}
