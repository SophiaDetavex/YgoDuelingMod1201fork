package de.cas_ual_ty.ydm.duel.screen.animation;

import org.joml.Quaternionf;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.cas_ual_ty.ydm.YDM;
import de.cas_ual_ty.ydm.clientutil.ClientProxy;
import de.cas_ual_ty.ydm.clientutil.YdmBlitUtil;
import net.minecraft.resources.ResourceLocation;

public class SpecialSummonAnimation extends Animation
{
    public float centerPosX;
    public float centerPosY;
    public int size;
    public int endSize;
    
    public SpecialSummonAnimation(float centerPosX, float centerPosY, int size, int endSize)
    {
        super(ClientProxy.specialAnimationLength);
        
        this.centerPosX = centerPosX;
        this.centerPosY = centerPosY;
        this.size = size;
        this.endSize = endSize;
    }
    @Override
    public boolean apply()
    {
        return true;
    }
    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks)
    {
        double relativeTickTime = (tickTime + partialTicks) / maxTickTime;
        
        // [0, 1/2pi]
        double cosTime1 = 0.5D * Math.PI * relativeTickTime;
        // [0, 1]
        float alpha = (float) (Math.cos(cosTime1));
        
        float rotation = 90F + 360F * (float) relativeTickTime;
        
        float size = (float) relativeTickTime * (endSize - this.size) + this.size;
        float halfSize = 0.5F * size;
        
        ms.pushPose();
        
        ms.translate(centerPosX, centerPosY, 0);
        ms.mulPose(new Quaternionf());
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1F, 1F, 1F, alpha);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        
        RenderSystem.setShaderTexture(0, getTexture());
        YdmBlitUtil.fullBlit(ms, -halfSize, -halfSize, size, size);
        
        RenderSystem.disableBlend();
        
        ms.popPose();
    }
    
    public ResourceLocation getTexture()
    {
        return new ResourceLocation(YDM.MOD_ID, "textures/gui/action_animations/special_summon.png");
    }
}
