package de.cas_ual_ty.ydm;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class YdmItemGroup extends CreativeModeTab
{
    private Supplier<Item> supplier;
    
    public YdmItemGroup(String string, Supplier<Item> supplier)
    {
        super(string);
        this.supplier = supplier;
    }
    
    public YdmItemGroup(String string, RegistryObject<Item> blancCard) {
        //TODO Auto-generated constructor stub
    }

    public ItemStack makeIcon()
    {
        return new ItemStack(supplier.get());
    }
}
