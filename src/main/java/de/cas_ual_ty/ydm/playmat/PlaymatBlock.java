package de.cas_ual_ty.ydm.playmat;

import de.cas_ual_ty.ydm.YdmTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PlaymatBlock extends Block
{
    public PlaymatBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(worldIn.isRemote)
        {
            return ActionResultType.SUCCESS;
        }
        else
        {
            INamedContainerProvider inamedcontainerprovider = this.getContainerFromTE(worldIn, pos);
            
            if(inamedcontainerprovider != null)
            {
                NetworkHooks.openGui((ServerPlayerEntity)player, inamedcontainerprovider, pos);
            }
            
            return ActionResultType.SUCCESS;
        }
    }
    
    public INamedContainerProvider getContainerFromTE(World world, BlockPos pos)
    {
        return this.getTE(world, pos);
    }
    
    public PlaymatTileEntity getTE(World world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        return te instanceof PlaymatTileEntity ? (PlaymatTileEntity)te : null;
    }
    
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return YdmTileEntityTypes.PLAYMAT.create();
    }
}
