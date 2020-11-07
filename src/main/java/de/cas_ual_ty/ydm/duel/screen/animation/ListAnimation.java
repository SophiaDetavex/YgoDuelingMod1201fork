package de.cas_ual_ty.ydm.duel.screen.animation;

import java.util.LinkedList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

public class ListAnimation extends Animation
{
    public final List<Animation> animations;
    public final List<Runnable> runnablesOnStart;
    public final List<Runnable> runnablesOnEnd;
    
    public ListAnimation(List<Animation> animations)
    {
        this.animations = animations;
        
        this.runnablesOnStart = new LinkedList<>();
        this.runnablesOnEnd = new LinkedList<>();
        
        this.maxTickTime = 1;
        
        for(Animation a : this.animations)
        {
            this.maxTickTime = Math.max(this.maxTickTime, a.maxTickTime);
            this.runnablesOnStart.add(a.onStart);
            this.runnablesOnEnd.add(a.onEnd);
            a.onStart = null;
            a.onEnd = null;
        }
        
        this.onStart = () ->
        {
            for(Runnable r : this.runnablesOnStart)
            {
                r.run();
            }
        };
        
        this.onEnd = () ->
        {
            for(Runnable r : this.runnablesOnEnd)
            {
                r.run();
            }
        };
    }
    
    @Override
    public Animation setOnStart(Runnable onStart)
    {
        this.runnablesOnStart.add(onStart);
        return super.setOnStart(onStart);
    }
    
    @Override
    public Animation setOnEnd(Runnable onEnd)
    {
        this.runnablesOnEnd.add(onEnd);
        return super.setOnEnd(onEnd);
    }
    
    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks)
    {
        for(Animation a : this.animations)
        {
            if(!a.ended())
            {
                a.render(ms, mouseX, mouseY, partialTicks);
            }
        }
    }
    
    @Override
    public void tick()
    {
        super.tick();
        
        for(Animation a : this.animations)
        {
            a.tick();
        }
    }
}
