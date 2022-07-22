package de.cas_ual_ty.ydm.deckbox;

import de.cas_ual_ty.ydm.YdmItems;
import de.cas_ual_ty.ydm.card.CardSleevesItem;
import de.cas_ual_ty.ydm.card.properties.Properties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class DeckBoxContainer extends Container
{
    public ItemStack itemStack;
    public IItemHandler itemHandler;
    public Slot cardSleevesSlot;
    
    public DeckBoxContainer(ContainerType<?> type, int id, PlayerInventory playerInventory)
    {
        this(type, id, playerInventory, DeckBoxItem.getActiveDeckBox(playerInventory.player));
    }
    
    public DeckBoxContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, ItemStack itemStack)
    {
        super(type, id);
        
        this.itemStack = itemStack;
        
        itemHandler = YdmItems.BLACK_DECK_BOX.getItemHandler(this.itemStack);
        
        final int itemsPerRow = 15;
        
        // main deck
        for(int y = 0; y < DeckHolder.MAIN_DECK_SIZE / itemsPerRow; ++y)
        {
            for(int x = 0; x < itemsPerRow && x + y * itemsPerRow < DeckHolder.MAIN_DECK_SIZE; ++x)
            {
                addSlot(new DeckBoxSlot(itemHandler, x + y * itemsPerRow + DeckHolder.MAIN_DECK_INDEX_START, 8 + x * 18, 18 + y * 18));
            }
        }
        
        // extra deck
        for(int x = 0; x < DeckHolder.EXTRA_DECK_SIZE; ++x)
        {
            addSlot(new DeckBoxSlot(itemHandler, x + DeckHolder.EXTRA_DECK_INDEX_START, 8 + x * 18, 104));
        }
        
        // side deck
        for(int x = 0; x < DeckHolder.SIDE_DECK_SIZE; ++x)
        {
            addSlot(new DeckBoxSlot(itemHandler, x + DeckHolder.SIDE_DECK_INDEX_START, 8 + x * 18, 136));
        }
        
        addSlot(cardSleevesSlot = new Slot(new Inventory(1), 0, 8 + 12 * 18, 168 + 0 * 18)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.getItem() instanceof CardSleevesItem;
            }
            
            @Override
            public int getMaxStackSize()
            {
                return 1;
            }
        });
        
        cardSleevesSlot.set(YdmItems.BLACK_DECK_BOX.getCardSleeves(itemStack));
        
        // player inventory
        for(int y = 0; y < 3; ++y)
        {
            for(int x = 0; x < 9; ++x)
            {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 168 + y * 18));
            }
        }
        
        // player hot bar
        Slot s;
        for(int x = 0; x < 9; ++x)
        {
            s = new Slot(playerInventory, x, 8 + x * 18, 226);
            
            if(s.getItem() == this.itemStack)
            {
                s = new Slot(playerInventory, s.getSlotIndex(), s.x, s.y)
                {
                    @Override
                    public boolean mayPickup(PlayerEntity playerIn)
                    {
                        return false;
                    }
                };
            }
            
            addSlot(s);
        }
    }
    
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        Slot slot = slots.get(index);
        ItemStack original = slot.getItem().copy();
        
        if(index < DeckHolder.TOTAL_DECK_SIZE || index == cardSleevesSlot.index)
        {
            //deck box slot or sleeves slot into inventory
            ItemStack itemStack = slot.getItem();
            
            if(moveItemStackTo(itemStack, cardSleevesSlot.index + 1, slots.size(), false))
            {
                slot.set(ItemStack.EMPTY);
                return ItemStack.EMPTY;
            }
            
            return ItemStack.EMPTY;
        }
        else if(original.getItem() == YdmItems.CARD)
        {
            //inventory to deck box
            
            Properties card = YdmItems.CARD.getCardHolder(original).getCard();
            boolean isExtraDeck = card.getIsInExtraDeck();
            
            int minTarget;
            int maxTarget;
            
            if(!isExtraDeck)
            {
                minTarget = DeckHolder.MAIN_DECK_INDEX_START;
                maxTarget = DeckHolder.MAIN_DECK_INDEX_END;
            }
            else
            {
                minTarget = DeckHolder.EXTRA_DECK_INDEX_START;
                maxTarget = DeckHolder.EXTRA_DECK_INDEX_END;
            }
            
            ItemStack itemStack = slot.getItem().split(1);
            
            if(moveItemStackTo(itemStack, minTarget, maxTarget, false))
            {
                return slot.getItem();
            }
            // side deck
            else if(moveItemStackTo(itemStack, DeckHolder.SIDE_DECK_INDEX_START, DeckHolder.SIDE_DECK_INDEX_END, false))
            {
                return slot.getItem();
            }
            
            slot.set(original);
        }
        else if(original.getItem() instanceof CardSleevesItem && !cardSleevesSlot.hasItem())
        {
            cardSleevesSlot.set(slot.getItem().split(1));
            return slot.getItem();
        }
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean stillValid(PlayerEntity playerIn)
    {
        return true;
    }
    
    @Override
    public void removed(PlayerEntity playerIn)
    {
        // TODO can be removed when capabilities work again
        ((DeckBoxItem) itemStack.getItem()).saveItemHandlerToNBT(itemStack, itemHandler);
        ((DeckBoxItem) itemStack.getItem()).saveCardSleevesToNBT(itemStack, cardSleevesSlot.getItem());
        super.removed(playerIn);
    }
}
