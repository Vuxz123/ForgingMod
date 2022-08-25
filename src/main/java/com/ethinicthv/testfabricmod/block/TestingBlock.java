package com.ethinicthv.testfabricmod.block;


import com.ethinicthv.testfabricmod.block.blockentity.TestingBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TestingBlock extends Block implements BlockEntityProvider {

    public static String KEY = "testing_block";

    public static final BooleanProperty COVERED = BooleanProperty.of("covered");

    public TestingBlock() {
        super(FabricBlockSettings.of(Material.STONE).nonOpaque());
        setDefaultState(getStateManager().getDefaultState().with(COVERED, false));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestingBlockEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BlockEntityProvider.super.getTicker(world, state, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return BlockEntityProvider.super.getGameEventListener(world, blockEntity);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,1,1);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.cuboid(0,0,0,1,1,1);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,1,1);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0,0,0,1,1,1);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.cuboid(0,0,0,1,1,1);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(COVERED) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COVERED);

        super.appendProperties(builder);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(true){
            BlockEntity e = world.getBlockEntity(pos);
            ItemStack i = player.getMainHandStack().copy();
            if (e != null) {
                ((TestingBlockEntity) e).onClick(i);
            }
            boolean check = !( i.getItem().equals(Items.AIR) || ! (i.getItem() instanceof BlockItem));
            player.sendMessage(Text.of( "" + check));
            world.setBlockState(pos, state.with(TestingBlock.COVERED, check), Block.NOTIFY_ALL);

        }
        return ActionResult.SUCCESS;
    }


}
