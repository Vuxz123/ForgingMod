package com.ethinicthv.testfabricmod.block;

import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.client.thread.HammerTimer;
import com.ethinicthv.testfabricmod.initializing.Util;
import com.ethinicthv.testfabricmod.item.AbstractTestItem;
import com.ethinicthv.testfabricmod.item.HammerItem;
import com.ethinicthv.testfabricmod.item.tool.ToolItemImplement;
import com.ethinicthv.testfabricmod.networking.packet.PacketIdentifier;
import net.fabricmc.fabric.api.block.BlockAttackInteractionAware;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class ForgingAnvil extends HorizontalFacingBlock implements BlockEntityProvider, BlockAttackInteractionAware {
    private static final VoxelShape[] shape = new VoxelShape[4];
    private static final VoxelShape[] hit_shape = new VoxelShape[2];

    public ForgingAnvil() {
        super(FabricBlockSettings.of(Material.METAL).nonOpaque().resistance(1200.0f).hardness(5));
        shape[0] = VoxelShapes.cuboid(0,0,0,0,0,0);

        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));

        VoxelShape top1 = VoxelShapes.cuboid(-0.25, 0.5, 0.25, 1.25,1,0.75);
        VoxelShape top2 = VoxelShapes.cuboid(-0.25, 0.5, 0.25, 1.25,1.25,0.75);
        VoxelShape top3 = VoxelShapes.cuboid(0.25, 0.5, -0.25, 0.75,1,1.25);
        VoxelShape top4 = VoxelShapes.cuboid(0.25, 0.5, -0.25, 0.75,1.25,1.25);
        VoxelShape mid = VoxelShapes.cuboid(0.3, 0.25, 0.3, 0.7,0.5,0.7);
        VoxelShape left1 = VoxelShapes.cuboid(0.7,0.25,0.33,0.91,0.375,0.67);
        VoxelShape right1 = VoxelShapes.cuboid(0.09,0.25,0.33,0.3,0.375,0.67);
        VoxelShape left2 = VoxelShapes.cuboid(0.33,0.25,0.7,0.67,0.375,0.91);
        VoxelShape right2 = VoxelShapes.cuboid(0.33,0.25,0.09,0.67,0.375,0.3);
        VoxelShape bot = VoxelShapes.cuboid(0.06,0,0.06,0.94,0.25,0.94);

        shape[0] = VoxelShapes.union(top1,mid,left1,right1,bot);
        shape[1] = VoxelShapes.union(top2,mid,left1,right1,bot);
        shape[2] = VoxelShapes.union(top3,mid,left2,right2,bot);
        shape[3] = VoxelShapes.union(top4,mid,left2,right2,bot);

        hit_shape[0] = VoxelShapes.cuboid(0.25,1,0.25,0.75, 1.1,0.75);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgingAnvilEntity(pos,state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int p = 0;
        Direction dir = state.get(FACING);
        switch (dir){
            case EAST, WEST -> p = 2;
        }
        return shape[p];
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape sp;
        int p = 0;
        Direction dir = state.get(FACING);
        switch (dir){
            case EAST, WEST -> p = 2;
        }

        sp = shape[p];

        if (((EntityShapeContext) context).getEntity() instanceof PlayerEntity player){
            if(!(player.getStackInHand(player.getActiveHand()).getItem() instanceof HammerItem)){
                double distance = 2;
                BlockHitResult result = Util.checkHit(hit_shape[0],player,pos,distance);
                if(result != null && result.getType() != HitResult.Type.MISS){
                    sp = VoxelShapes.union(sp, hit_shape[0]);
                }
            }
        }
        return sp;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of(Direction.fromRotation(MinecraftClient.getInstance().player.getBodyYaw()).asString()));
        return this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.fromRotation(MinecraftClient.getInstance().player.getBodyYaw()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof ForgingAnvilEntity entity){
            ItemStack i = player.getStackInHand(player.getActiveHand());
            ItemStack i2 = i.copy();

            double distance = 2;
            BlockHitResult result =  Util.checkHit(hit_shape[0],player,pos,distance);

            if(result != null && result.getType() != HitResult.Type.MISS){
                if(entity.getStoredItem() != ItemStack.EMPTY){
                    ItemEntity itemEntity = new ItemEntity(world,pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, entity.getStoredItem().copy(),0,0.15,0);
                    if(!world.isClient()){
                        world.spawnEntity(itemEntity);
                    }
                    entity.setStoredItem(ItemStack.EMPTY, world, state);
                    world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                }

                if(i != ItemStack.EMPTY){
                    Item type = i2.getItem();
                    boolean bl = (type instanceof AbstractTestItem || !(type instanceof BlockItem));
                    if(bl){
                        entity.setStoredItem(i2,world,state);
                        player.setStackInHand(hand,ItemStack.EMPTY);

                    }
                }

                return ActionResult.SUCCESS;
            }

        }
        return ActionResult.PASS;
    }

    @Override
    public boolean onAttackInteraction(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction direction) {
        HammerTimer.p = player;
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.getItem() instanceof HammerItem){
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof ForgingAnvilEntity forgingAnvil){
                ItemStack i = forgingAnvil.getStoredItem();
                if(i.getItem() instanceof ToolItem ti){
                    ToolItemImplement t = (ToolItemImplement) ti;
                    if(i.isDamaged()){
                        if(t.checkHit(pos.up(),player,2) != -1){
                            this.func_1(state,world,pos,forgingAnvil,itemStack,player);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean canUse(BlockPos pos, World world){
        ForgingAnvilEntity entity = Objects.requireNonNull((ForgingAnvilEntity) world.getBlockEntity(pos));
        return entity.getStoredItem().isDamaged();

    }

    public static boolean checkHit(PlayerEntity player, BlockPos pos, double distance, boolean checkhitshape){
        boolean check = false;
        for(VoxelShape shape1 : shape){
            if(check = Util.checkHit(shape1, player, pos, distance).getType() != HitResult.Type.MISS){
                return check;
            }
        }
        if(checkhitshape){
            for(VoxelShape shape1 : hit_shape){
                if(check = Util.checkHit(shape1, player, pos, distance).getType() != HitResult.Type.MISS){
                    return check;
                }
            }
        }
        return check;
    }

    public static ItemStack getItem(BlockPos pos, World world){
        if(world.getBlockEntity(pos) instanceof ForgingAnvilEntity entity){
            return entity.getStoredItem();
        }
        return ItemStack.EMPTY;
    }

    public void func_1(BlockState state, World world, BlockPos pos, ForgingAnvilEntity forgingAnvil, ItemStack itemStack, PlayerEntity player){
        if(HammerTimer.a != null){
            if(HammerTimer.isCoolDown()){
                return;
            }
        }
        int power = 1;
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(power);
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(PacketIdentifier.FORGING,buf);
        ItemStack stack = forgingAnvil.getStoredItem().copy();
        int temp = stack.getDamage();
        stack.setDamage(temp + power);
        forgingAnvil.setStoredItem(stack, world, world.getBlockState(pos));
        itemStack.damage(1, player, player1 -> {});
        HammerTimer.runTimer();
    }

}
