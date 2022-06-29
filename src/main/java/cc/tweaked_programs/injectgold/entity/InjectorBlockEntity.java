package cc.tweaked_programs.injectgold.entity;

import cc.tweaked_programs.injectgold.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class InjectorBlockEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int gold = 0;

    public InjectorBlockEntity(BlockPos pos, BlockState state) {
        super(Main.INJECTOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world1, BlockPos pos, BlockState state1, InjectorBlockEntity be) { }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        gold = nbt.getInt("number");

        // If we are on the client, re-render the Block because of the changed NBT Data
        /*if (world != null && world.isClient()) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.execute(() -> {
                Main.LOGGER.info("Update Client Block");
                client.world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            });
        }*/
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        nbt.putInt("number", gold);
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
}
