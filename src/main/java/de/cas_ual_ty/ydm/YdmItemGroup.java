package de.cas_ual_ty.ydm;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class YdmItemGroup extends CreativeModeTab
{
    private Supplier<Item> supplier;
    
    public YdmItemGroup(Builder modId, Supplier<Item> supplier)
    {
        super(modId);
        this.supplier = supplier;
    }
    public YdmItemGroup(Builder string, RegistryObject<Item> supplier)
    {
        super(string);
        this.supplier = supplier;
    }

    public ItemStack makeIcon()
    {
        return new ItemStack(supplier.get());
    }
}
