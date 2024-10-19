package de.cas_ual_ty.ydm.cardsupply;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.YDM;
import de.cas_ual_ty.ydm.YdmDatabase;
import de.cas_ual_ty.ydm.card.CardHolder;
import de.cas_ual_ty.ydm.cardbinder.CardButton;
import de.cas_ual_ty.ydm.cardinventory.CardInventory;
import de.cas_ual_ty.ydm.clientutil.CardRenderUtil;
import de.cas_ual_ty.ydm.clientutil.widget.ImprovedButton;
import de.cas_ual_ty.ydm.rarity.Rarities;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CardSupplyScreen extends AbstractContainerScreen<CardSupplyContainer>
{
    private static final ResourceLocation CARD_SUPPLY_GUI_TEXTURE = new ResourceLocation(YDM.MOD_ID, "textures/gui/card_supply.png");
    
    public static final int ROWS = 6;
    public static final int COLUMNS = 9;
    public static final int PAGE = CardSupplyScreen.ROWS * CardSupplyScreen.COLUMNS;
    
    public List<CardHolder> cardsList;
    public EditBox textField;
    protected Button prevButton;
    protected Button nextButton;
    public int page;
    
    public CardButton[] cardButtons;
    
    public CardSupplyScreen(CardSupplyContainer screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, titleIn);
        imageWidth = 176;
        imageHeight = 114 + 6 * 18; //222
        cardsList = new ArrayList<>(YdmDatabase.getTotalCardsAndVariants());
    }
    
    @Override
    protected void init()
    {
        super.init();
        
        addRenderableWidget(textField = new EditBox(font, leftPos + imageWidth - 80 - 8 - 1, topPos + 6 - 1, 80 + 2, font.lineHeight + 2, Component.empty()));
        
        int index;
        CardButton button;
        cardButtons = new CardButton[CardSupplyScreen.PAGE];
        
        for(int y = 0; y < CardInventory.DEFAULT_PAGE_ROWS; ++y)
        {
            for(int x = 0; x < CardInventory.DEFAULT_PAGE_COLUMNS; ++x)
            {
                index = x + y * 9;
                button = new CardButton(leftPos + 7 + (x * 18), topPos + 17 + y * 18, 18, 18, index, this::onCardClicked, this::getCard);
                cardButtons[index] = button;
                addRenderableWidget(button);
            }
        }
        
        addRenderableWidget(prevButton = new ImprovedButton(leftPos + imageWidth - 80 - 8, topPos + imageHeight - 96, 40, 12, Component.translatable("container.ydm.card_supply.prev"), this::onButtonClicked));
        addRenderableWidget(nextButton = new ImprovedButton(leftPos + imageWidth - 40 - 8, topPos + imageHeight - 96, 40, 12, Component.translatable("container.ydm.card_supply.next"), this::onButtonClicked));
        
        applyName();
        updateCards();
    }
    
    public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks)
    {
        super.render(ms, mouseX, mouseY, partialTicks);
        renderTooltip(ms, mouseX, mouseY);
        
        for(CardButton button : cardButtons)
        {
            if(button.isHoveredOrFocused())
            {
                if(button.getCard() != null)
                {
                    CardRenderUtil.renderCardInfo(ms.pose(), button.getCard(), this);
                    
                    List<Component> list = new LinkedList<>();
                    button.getCard().addInformation(list);
                    
                    Object tooltip = new ArrayList<>(list.size());
                    ItemStack[] CastedList = (ItemStack[]) ((List<?>) ((Component) list).toFlatList()).toArray(new ItemStack[0]);
                    for(ItemStack t : (ItemStack[]) CastedList)
                    {
                        ((Inventory) tooltip).add(t);
                    }
                    
                    //renderTooltip
                    renderWithTooltip(ms, (int) tooltip, mouseX, mouseY);
                }
                
                break;
            }
        }
    }
    
    protected void renderBg(PoseStack ms, float partialTicks, int x, int y)
    {
        RenderSystem.setShaderTexture(0, CardSupplyScreen.CARD_SUPPLY_GUI_TEXTURE);
        blit(ms, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
    
    protected static void renderLabels(Font ms, int mouseX, int mouseY)
    {
         GuiGraphics.drawString(ms, title, (int) 8.0F, (int) 6.0F, (int) 0x404040);
         GuiGraphics.drawString(ms, playerInventoryTitle.getVisualOrderText(), (int) 8.0F, (int) (imageHeight - 96 + 2), 0x404040);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if(textField != null && textField.isFocused())
        {
            if(keyCode == GLFW.GLFW_KEY_ENTER)
            {
                applyName();
                return true;
            }
            else
            {
                return textField.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        else
        {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
    
    protected void onButtonClicked(Button button)
    {
        int minPage = 0;
        int maxPage = cardsList.size() / CardSupplyScreen.PAGE + 1;
        
        if(button == prevButton)
        {
            --page;
            
            if(page < minPage)
            {
                page = maxPage;
            }
        }
        else if(button == nextButton)
        {
            ++page;
            
            if(page > maxPage)
            {
                page = minPage;
            }
        }
    }
    
    public void applyName()
    {
        String name = textField.getValue().toLowerCase();
        
        cardsList.clear();
        page = 0;
        
        YdmDatabase.forAllCardVariants((card, imageIndex) ->
        {
            if(card.getName().toLowerCase().contains(name))
            {
                cardsList.add(new CardHolder(card, imageIndex, Rarities.SUPPLY.name));
            }
        });
    }
    
    public void updateCards()
    {
        page = 0;
        cardsList.clear();
        
        YdmDatabase.forAllCardVariants((card, imageIndex) ->
        {
            cardsList.add(new CardHolder(card, imageIndex, Rarities.SUPPLY.name));
        });
    }
    
    protected void onCardClicked(CardButton button, int index)
    {
        if(button.getCard() != null && button.getCard().getCard() != null)
        {
            YDM.channel.send(PacketDistributor.SERVER.noArg(), new CardSupplyMessages.RequestCard(button.getCard().getCard(), button.getCard().getImageIndex()));
        }
    }
    
    protected CardHolder getCard(int index0)
    {
        int index = scopeIndex(index0);
        return index < cardsList.size() ? cardsList.get(index) : null;
    }
    
    protected int scopeIndex(int cardButtonIndex)
    {
        return page * CardSupplyScreen.PAGE + cardButtonIndex;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    }
}
