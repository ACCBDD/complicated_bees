package com.accbdd.complicated_bees.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BeeParticle extends TextureSheetParticle {
    protected BeeParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.lifetime = 20;
        this.xd = pXSpeed + (Math.random() * 2.0 - 1.0) * 0.4F;
        this.yd = pYSpeed + (Math.random() * 2.0 - 1.0) * 0.4F;
        this.zd = pZSpeed + (Math.random() * 2.0 - 1.0) * 0.4F;
        double d0 = (Math.random() + Math.random() + 1.0) * 0.05F;
        double d1 = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        this.xd = this.xd / d1 * d0 * 0.4F;
        this.yd = this.yd / d1 * d0 * 0.4F;
        this.zd = this.zd / d1 * d0 * 0.4F;
        this.friction = 1;
        scale(0.25f);
        setColor(1, 0.8f, 0);
        setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);

            this.xd *= this.friction;
            this.yd *= this.friction;
            this.zd *= this.friction;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BeeParticle(worldIn, x, y, z, spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}
