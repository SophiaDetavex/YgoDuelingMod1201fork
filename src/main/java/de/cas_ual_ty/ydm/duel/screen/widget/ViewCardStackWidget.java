package de.cas_ual_ty.ydm.duel.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.card.CardSleevesType;
import de.cas_ual_ty.ydm.clientutil.CardRenderUtil;
import de.cas_ual_ty.ydm.clientutil.ScreenUtil;
import de.cas_ual_ty.ydm.duel.playfield.DuelCard;
import de.cas_ual_ty.ydm.duel.screen.DuelScreenDueling;
import de.cas_ual_ty.ydm.duel.screen.IDuelScreenContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ViewCardStackWidget extends Button
{
    public final IDuelScreenContext context;
    public DuelCard hoverCard;
    protected int cardsTextureSize;
    protected int rows;
    protected int columns;
    protected int currentRow;
    protected List<DuelCard> cards;
    protected boolean forceFaceUp;
    @Override
    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }
    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    }
    public ViewCardStackWidget(IDuelScreenContext context, int x, int y, int width, int height, Component title, Consumer<ViewCardStackWidget> onPress, OnTooltip onTooltip)
    {
        super(x, y, width, height, title, (button) -> onPress.accept((ViewCardStackWidget) button), onTooltip);
        this.context = context;
        hoverCard = null;
        rows = 0;
        columns = 0;
        currentRow = 0;
        deactivate();
    }
    
    public ViewCardStackWidget setRowsAndColumns(int cardsTextureSize, int rows, int columns)
    {
        this.cardsTextureSize = cardsTextureSize;
        this.rows = Math.max(1, rows);
        this.columns = Math.max(1, columns);
        return this;
    }
    
    public void activate(List<DuelCard> cards, boolean forceFaceUp)
    {
        active = true;
        visible = true;
        currentRow = 0;
        this.cards = cards;
        this.forceFaceUp = forceFaceUp;
    }
    
    public void forceFaceUp()
    {
        forceFaceUp = true;
    }
    
    public void deactivate()
    {
        cards = null;
        visible = false;
        active = false;
    }
    
    public int getCurrentRow()
    {
        return currentRow;
    }
    
    public int getMaxRows()
    {
        if(cards != null && columns > 0)
        {
            return Math.max(0, Mth.ceil(cards.size() / (float) columns) - rows);
        }
        else
        {
            return 0;
        }
    }
    
    public void decreaseCurrentRow()
    {
        currentRow = Math.max(0, currentRow - 1);
    }
    
    public void increaseCurrentRow()
    {
        currentRow = Math.min(getMaxRows(), currentRow + 1);
    }
    
    public boolean getForceFaceUp()
    {
        return forceFaceUp;
    }
    
    public List<DuelCard> getCards()
    {
        return cards;
    }
    
    public void renderButton(PoseStack ms, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1F, 1F, 1F, alpha);
        
        if(!cards.isEmpty())
        {
            hoverCard = renderCards(ms, mouseX, mouseY);
        }
        else
        {
            hoverCard = null;
        }
        
        int j = getFGColor();
        Screen.drawCenteredString(ms, fontrenderer, getMessage(), getX(), getY(), j | Mth.ceil(alpha * 255.0F) << 24);
    }
    
    @Nullable
    public DuelCard renderCards(PoseStack ms, int mouseX, int mouseY)
    {
        DuelCard hoveredCard = null;
        int hoverX = 0, hoverY = 0;
        
        int index = currentRow * columns;
        int x, y;
        DuelCard c;
        
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns && index < cards.size(); ++j)
            {
                x = this.getX() + j * cardsTextureSize;
                y = this.getY() + i * cardsTextureSize;
                
                c = cards.get(index++);
                
                if(drawCard(ms, c, x, y, cardsTextureSize, cardsTextureSize, mouseX, mouseY))
                {
                    hoverX = x;
                    hoverY = y;
                    hoveredCard = c;
                }
            }
        }
        
        if(hoveredCard != null)
        {
            if(hoveredCard.getCardPosition().isFaceUp || forceFaceUp || (context.getClickedZone() != null && context.getZoneOwner() == context.getClickedZone().getOwner() && !context.getClickedZone().getType().getIsSecret()))
            {
                context.renderCardInfo(ms, hoveredCard);
            }
            
            ScreenUtil.renderHoverRect(ms, hoverX, hoverY, cardsTextureSize, cardsTextureSize);
        }
        
        if(!active)
        {
            return null;
        }
        else
        {
            return hoveredCard;
        }
    }
    
    protected boolean drawCard(PoseStack ms, DuelCard duelCard, int renderX, int renderY, int renderWidth, int renderHeight, int mouseX, int mouseY)
    {
        if(context.getClickedCard() == duelCard)
        {
            if(context.getOpponentClickedCard() == duelCard)
            {
                DuelScreenDueling.renderBothSelectedRect(ms, renderX, renderY, renderWidth, renderHeight);
            }
            else
            {
                DuelScreenDueling.renderSelectedRect(ms, renderX, renderY, renderWidth, renderHeight);
            }
        }
        else
        {
            if(context.getOpponentClickedCard() == duelCard)
            {
                DuelScreenDueling.renderEnemySelectedRect(ms, renderX, renderY, renderWidth, renderHeight);
            }
            else
            {
                //
            }
        }
        
        CardRenderUtil.renderDuelCardCentered(ms, context.getClickedZone() != null ? context.getClickedZone().getSleeves() : CardSleevesType.CARD_BACK, mouseX, mouseY, renderX, renderY, renderWidth, renderHeight, duelCard, forceFaceUp);
        
        return isHoveredOrFocused() && mouseX >= renderX && mouseX < renderX + renderWidth && mouseY >= renderY && mouseY < renderY + renderHeight;
    }
}