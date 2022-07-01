package cc.tweaked_programs.injectgold.entity;

import cc.tweaked_programs.injectgold.Main;
import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class InjectorBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final float MAXTIME = 200.0f;
    public static final int MAXFUEL = 16;
    private Integer fuel = 0;
    private float injectTime = 0.0f;

    public InjectorBlockEntity(BlockPos pos, BlockState state) { super(Main.INJECTOR_BLOCK_ENTITY, pos, state); }

    public static void tick(World world, BlockPos pos, BlockState state, InjectorBlockEntity entity) {
        if (!world.isClient()) {
            Optional<InjectRecipe> match = world.getRecipeManager()
                    .getFirstMatch(InjectRecipe.Type.INSTANCE, entity, world);

            int presentFuel = entity.getFuel();
            if (match.isPresent() && presentFuel >= match.get().getFuelAmount()
            && (entity.getStack(1).isEmpty() || match.get().getOutput().getItem() == entity.getStack(1).getItem())
            && entity.getStack(1).getCount()+match.get().getAmount() < 64) {
                entity.injectTime++;
                if (entity.injectTime >= MAXTIME) {
                    entity.injectTime = 0;
                    entity.setFuel(presentFuel - match.get().getFuelAmount());
                    ItemStack input = entity.getStack(0);
                    ItemStack output = entity.getStack(1);

                    if (output.isEmpty()) {
                        output = match.get().getOutput().copy();
                    } else {
                        output.setCount(output.getCount() + match.get().getAmount());
                    }

                    entity.clear();
                    if (input.getCount()-1 > 0) {
                        input.setCount(input.getCount() - 1);
                        entity.setStack(0, input);
                    }
                    entity.setStack(1, output);
                }
                entity.markDirty();
            } else if (entity.injectTime > 0) {
                entity.injectTime = 0;
                entity.markDirty();
            }
        }
    }

    private int getFuel() { return fuel; }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public float getInjectTime() {
        return injectTime;
    }

    public Pair<Text,Text> getLabel() {
        Text top = Text.translatable("block." + Main.MOD_ID + ".injector_block.display.ready");
        Text bottom = Text.translatable("block."+Main.MOD_ID+".injector_block.display.fuel").append(Text.of(fuel.toString())).formatted(Formatting.GOLD).append("/"+MAXFUEL);
        Optional<InjectRecipe> match = world.getRecipeManager()
                .getFirstMatch(InjectRecipe.Type.INSTANCE, this, world);
        if (items.get(1) != ItemStack.EMPTY) {
            top = Text.translatable("block." + Main.MOD_ID + ".injector_block.display.done").formatted(Formatting.GREEN);
        }
        if (items.get(0) != ItemStack.EMPTY) {
            if (match.isPresent()) {
                top = Text.translatable(match.get().getOutput().getTranslationKey()).formatted(Formatting.YELLOW);
                if (fuel < match.get().getFuelAmount()) {
                    Integer fuelNeeded = match.get().getFuelAmount() - fuel;
                    bottom = Text.translatable("category."+Main.MOD_ID+".fuel.needed").append(Text.of(fuelNeeded.toString())).formatted(Formatting.RED);
                }
            } else if (injectTime == 0) {
                top = Text.translatable("block." + Main.MOD_ID + ".injector_block.display.error.404").formatted(Formatting.RED);
            }
        }
        return new Pair<>(top,bottom);
    }

    public boolean addFuel() {
        if (fuel < MAXFUEL) {
            fuel += 1;
            return true;
        }
        return false;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        items.clear();
        Inventories.readNbt(nbt, items);
        fuel = nbt.getInt("fuel");
        injectTime = nbt.getFloat("injectTime");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        nbt.putInt("fuel", fuel);
        nbt.putFloat("injectTime", injectTime);
        super.writeNbt(nbt);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.hasWorld() && !world.isClient())
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public DefaultedList<ItemStack> getItems() { return items; }

    public Direction getFacing() {
        if(this.world.getBlockState(this.pos).getBlock() == Main.INJECTOR_BLOCK)
            return this.world.getBlockState(this.pos).get(HORIZONTAL_FACING);
        return Direction.NORTH;
    }
}
