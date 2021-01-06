package de.cas_ual_ty.ydm.clientutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.cas_ual_ty.ydm.YDM;
import de.cas_ual_ty.ydm.YdmItems;
import de.cas_ual_ty.ydm.set.CardSet;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.ItemTextureQuadConverter;

@SuppressWarnings("deprecation")
public class FinalCardSetBakedModel implements IBakedModel
{
    private IBakedModel mainModel;
    private ItemStack activeItemStack;
    private Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    
    private List<BakedQuad> singleBackList = null;
    private List<BakedQuad> partneredBackList = null;
    private List<BakedQuad> blancList = null;
    
    private final float distance = 0.002F;
    
    public FinalCardSetBakedModel(IBakedModel mainModel)
    {
        this.mainModel = mainModel;
        this.setActiveItemStack(ItemStack.EMPTY);
        this.textureGetter = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    }
    
    public FinalCardSetBakedModel setActiveItemStack(ItemStack itemStack)
    {
        this.activeItemStack = itemStack;
        return this;
    }
    
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
    {
        if(ClientProxy.itemsUseSetImagesActive)
        {
            CardSet set = YdmItems.SET.getCardSet(this.activeItemStack);
            
            if(set != CardSet.DUMMY)
            {
                List<BakedQuad> list = new ArrayList<>();
                
                ResourceLocation front = set.getItemImageResourceLocation();
                TextureAtlasSprite spriteFront = this.textureGetter.apply(front);
                list.addAll(ItemTextureQuadConverter.convertTexture(TransformationMatrix.identity(), spriteFront, spriteFront, 0.5F + this.distance, Direction.SOUTH, 0xFFFFFFFF, 1));
                
                return list;
            }
        }
        
        return this.getSetList();
    }
    
    @Override
    public boolean isAmbientOcclusion()
    {
        return this.mainModel.isAmbientOcclusion();
    }
    
    @Override
    public boolean isGui3d()
    {
        return this.mainModel.isGui3d();
    }
    
    @Override
    public boolean isSideLit()
    {
        return this.mainModel.isSideLit();
    }
    
    @Override
    public boolean isBuiltInRenderer()
    {
        return this.mainModel.isBuiltInRenderer();
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return this.mainModel.getParticleTexture();
    }
    
    @Override
    public ItemOverrideList getOverrides()
    {
        return this.mainModel.getOverrides();
    }
    
    @Override
    public IBakedModel handlePerspective(TransformType t, MatrixStack mat)
    {
        mat.push();
        
        switch(t)
        {
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
                // m.setTranslation(new Vector3f(0f, 3.5f, 0f));
                
                mat.scale(0.5F, 0.5F, 0.5F);
                mat.translate(0, 0.35, 0);
                
                break;
            case GROUND:
                // m.setTranslation(new Vector3f(0f, 4f, 0f));
                mat.scale(0.5F, 0.5F, 0.5F);
                break;
            case FIXED:
                mat.rotate(new Quaternion(Direction.UP.toVector3f(), 180F, true));
                break;
            default:
                break;
        }
        
        return this;
    }
    
    private List<BakedQuad> getSetList()
    {
        if(this.singleBackList == null)
        {
            ResourceLocation rl = new ResourceLocation(YDM.MOD_ID, "item/" + YDM.proxy.addCardItemTag("card_back"));
            TextureAtlasSprite sprite = this.textureGetter.apply(rl);
            this.singleBackList = ItemTextureQuadConverter.convertTexture(TransformationMatrix.identity(), sprite, sprite, 0.5F, Direction.SOUTH, 0xFFFFFFFF, 1);
        }
        
        return this.singleBackList;
    }
}
