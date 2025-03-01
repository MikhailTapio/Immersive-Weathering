package com.ordana.immersive_weathering.data.fluid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.position_tests.PositionRuleTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OtherFluidGenerator implements IFluidGenerator {

    public static final Codec<OtherFluidGenerator> CODEC = RecordCodecBuilder.<OtherFluidGenerator>create(
            instance -> instance.group(
                    Registry.FLUID.byNameCodec().fieldOf("fluid").forGetter(OtherFluidGenerator::getFluid),
                    FluidType.CODEC.optionalFieldOf("fluid_type", FluidType.BOTH).forGetter(OtherFluidGenerator::getFluidType),
                    BlockState.CODEC.fieldOf("generate").forGetter(OtherFluidGenerator::getGrowth),
                    RuleTest.CODEC.fieldOf("target").forGetter(OtherFluidGenerator::getTarget),
                    PositionRuleTest.CODEC.optionalFieldOf("additional_target_check").forGetter(OtherFluidGenerator::getExtraCheck),
                    Codec.INT.optionalFieldOf("priority", 0).forGetter(OtherFluidGenerator::getPriority)
            ).apply(instance, OtherFluidGenerator::new));

    public static final IFluidGenerator.Type<OtherFluidGenerator> TYPE = new IFluidGenerator.Type<>(CODEC, "target_other");

    private final Fluid fluid;
    private final FluidType fluidType;
    private final BlockState growth;
    private final RuleTest target;
    private final Optional<PositionRuleTest> extraCheck;
    private final int priority;

    public OtherFluidGenerator(Fluid fluid,FluidType fluidType, BlockState growth,
                               RuleTest target, Optional<PositionRuleTest> positionRuleTests, int priority) {
        this.fluid = fluid;
        this.fluidType = fluidType;
        this.growth = growth;
        this.target = target;
        this.extraCheck = positionRuleTests;
        this.priority = priority;
    }

    @Override
    public FluidType getFluidType() {
        return fluidType;
    }

    @Override
    public Type<?> getType() {
        return TYPE;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public RuleTest getTarget() {
        return target;
    }

    public BlockState getGrowth() {
        return growth;
    }

    public Optional<PositionRuleTest> getExtraCheck() {
        return extraCheck;
    }

    public int getPriority() {
        return priority;
    }

    public Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {
        Holder<Biome> b = extraCheck.isPresent() ? level.getBiome(pos) : null;

        for (Direction d : possibleFlowDir) {
            BlockPos p = pos.relative(d);
            BlockState state = neighborCache.computeIfAbsent(d, c -> level.getBlockState(p));
            if (target.test(state, level.random)) {
                if(b != null && !extraCheck.get().test(b, p, level)) continue;
                level.setBlockAndUpdate(p, this.growth);
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}
