package cc.tweaked_programs.injectgold.block;

import cc.tweaked_programs.injectgold.Main;
import cc.tweaked_programs.injectgold.entity.InjectorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class InjectorBlock extends BlockWithEntity {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    public InjectorBlock(Settings settings) { super(settings.nonOpaque()); }

    public BlockEntity InjectorBlock(BlockPos pos, BlockState state) { return new InjectorBlockEntity(pos, state); }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InjectorBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;
        InjectorBlockEntity blockEntity = (InjectorBlockEntity)world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty()) {
            if (blockEntity.getStack(0).isEmpty()) {
                blockEntity.setStack(0, player.getStackInHand(hand).copy());
                player.getStackInHand(hand).setCount(0);
            }
        } else if (!blockEntity.getStack(0).isEmpty()) {
            player.getInventory().offerOrDrop(blockEntity.getStack(0));
            blockEntity.removeStack(0);
        }

        blockEntity.markDirty();
        return ActionResult.SUCCESS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Main.INJECTOR_BLOCK_ENTITY, (world1, pos, state1, be) -> InjectorBlockEntity.tick(world1, pos, state1, be));
    }
}