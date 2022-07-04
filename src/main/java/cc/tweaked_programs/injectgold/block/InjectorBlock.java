package cc.tweaked_programs.injectgold.block;

import cc.tweaked_programs.injectgold.block.entity.InjectorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.tag.TagKey;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static cc.tweaked_programs.injectgold.Main.MOD_ID;
import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class InjectorBlock extends BlockWithEntity {
    public static final TagKey<Item> FUEL = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "injector_fuel"));
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public InjectorBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    //public BlockEntity InjectorBlock(BlockPos pos, BlockState state) { return new InjectorBlockEntity(pos, state); }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InjectorBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;
        InjectorBlockEntity entity = (InjectorBlockEntity)world.getBlockEntity(pos);

        if (player.getStackInHand(hand).isIn(FUEL)) {
            if (entity.addFuel()) {
                int count = player.getStackInHand(hand).getCount();
                player.getStackInHand(hand).setCount(count-1);
                entity.markDirty();
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        } else if (!entity.getStack(1).isEmpty())  {
            player.getInventory().offerOrDrop(entity.getStack(1));
            entity.removeStack(1);
        } else if (entity.getStack(0).isEmpty()) {
            entity.setStack(0, player.getStackInHand(hand).copy());
            player.getStackInHand(hand).setCount(0);
        } else {
            player.getInventory().offerOrDrop(entity.getStack(0));
            entity.removeStack(0);
        }
        entity.markDirty();
        return ActionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockRegister.INJECTOR_BLOCK_ENTITY, InjectorBlockEntity::tick);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)(entity));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HORIZONTAL_FACING);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }
}