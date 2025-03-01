package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

//just because I can
record OrTest(List<PositionRuleTest> predicates) implements PositionRuleTest {

    public static final String NAME = "or";
    public static final Codec<OrTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PositionRuleTest.CODEC.listOf().fieldOf("predicates").forGetter(OrTest::predicates)
    ).apply(instance, OrTest::new));

    static final Type<OrTest> TYPE =
            new Type<>(OrTest.CODEC, OrTest.NAME);

    @Override
    public Type<OrTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        for(var p : predicates){
            if(p.test(biome,pos, level))return true;
        }
        return false;
    }
}
