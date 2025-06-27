package com.iafenvoy.random.command.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class OverrideSizeInventory implements Inventory {
    private final Inventory parent;
    private final int size;

    public OverrideSizeInventory(Inventory parent, int size) {
        this.parent = parent;
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.parent.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot < this.parent.size() ? this.parent.getStack(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return slot < this.parent.size() ? this.parent.removeStack(slot, amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return slot < this.parent.size() ? this.parent.removeStack(slot) : ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < this.parent.size()) this.parent.setStack(slot, stack);
    }

    @Override
    public void markDirty() {
        this.parent.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.parent.canPlayerUse(player);
    }

    @Override
    public void clear() {
        this.parent.clear();
    }
}
