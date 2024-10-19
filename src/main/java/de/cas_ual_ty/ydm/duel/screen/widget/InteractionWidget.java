package de.cas_ual_ty.ydm.duel.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.clientutil.ScreenUtil;
import de.cas_ual_ty.ydm.clientutil.YdmBlitUtil;
import de.cas_ual_ty.ydm.duel.action.ActionIcon;
import de.cas_ual_ty.ydm.duel.playfield.ZoneInteraction;
import de.cas_ual_ty.ydm.duel.screen.IDuelScreenContext;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class InteractionWidget extends Button
{
    public final ZoneInteraction interaction;
    public final IDuelScreenContext context;
    @Override
    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }
    public InteractionWidget(ZoneInteraction interaction, IDuelScreenContext context, int x, int y, int width, int height, Component title, Consumer<InteractionWidget> onPress, OnTooltip onTooltip)
    {
        super(x, y, width, height, title, (w) -> onPress.accept((InteractionWidget) w), onTooltip);
        this.interaction = interaction;
        this.context = context;
    }
    
    public InteractionWidget(ZoneInteraction interaction, IDuelScreenContext context, int x, int y, int width, int height, Consumer<InteractionWidget> onPress, OnTooltip onTooltip)
    {
        super(x, y, width, height, interaction.icon.getLocal(), (w) -> onPress.accept((InteractionWidget) w), onTooltip);
        this.interaction = interaction;
        this.context = context;
    }
    
    public void renderButton(PoseStack ms, int mouseX, int mouseY, float partialTicks)
    {
        ms.pushPose();
        ms.translate(0, 0, 5);
        
        ActionIcon icon = interaction.icon;
        
        int iconWidth = icon.iconWidth;
        int iconHeight = icon.iconHeight;
        
        if(iconHeight >= height)
        {
            iconWidth = height * iconWidth / iconHeight;
            iconHeight = height;
        }
        
        if(iconWidth >= width)
        {
            iconHeight = width * iconHeight / iconWidth;
            iconWidth = width;
        }
        
        ScreenUtil.white();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        
        RenderSystem.setShaderTexture(0, icon.sourceFile);
        YdmBlitUtil.blit(ms, getX() + (width - iconWidth) / 2, getY() + (height - iconHeight) / 2, iconWidth, iconHeight, icon.iconX, icon.iconY, icon.iconWidth, icon.iconHeight, icon.fileSize, icon.fileSize);
        
        if(isHoveredOrFocused() && active)
        {
            ScreenUtil.renderHoverRect(ms, getX(), getY(), width, height);
            renderwithTooltip(ms, mouseX, mouseY);
        }
        
        ms.popPose();
    }
}