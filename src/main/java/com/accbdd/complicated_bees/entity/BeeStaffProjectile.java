package com.accbdd.complicated_bees.entity;

import com.accbdd.complicated_bees.registry.EntitiesRegistration;
import com.accbdd.complicated_bees.registry.EsotericRegistration;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BeeStaffProjectile extends Projectile implements ItemSupplier {
    public double speedX;
    public double speedY;
    public double speedZ;

    public BeeStaffProjectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    public BeeStaffProjectile(Level level, double x, double y, double z, double speedX, double speedY, double speedZ) {
        this(EntitiesRegistration.BEE_STAFF_MOUNT.get(), level);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
    }

    public BeeStaffProjectile(Level level, LivingEntity shooter, double speedX, double speedY, double speedZ) {
        this(level, shooter.getX(), shooter.getY(), shooter.getZ(), speedX, speedY, speedZ);
        this.setOwner(shooter);
        this.setRot(shooter.getYRot(), shooter.getXRot());
    }

    @Override
    protected void defineSynchedData() {
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().isAreaLoaded(this.blockPosition(), 2)) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double dX = this.getX() + vec3.x;
            double dY = this.getY() + vec3.y;
            double dZ = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            if (this.isInWater())
                this.discard();
            this.setDeltaMovement(vec3.add(this.speedX, this.speedY, this.speedZ));
            this.level().addParticle(EsotericRegistration.BEE_PARTICLE.get(), dX, dY, dZ, 0.0, 0.0, 0.0);
            this.level().addParticle(EsotericRegistration.BEE_PARTICLE.get(), dX, dY, dZ, 0.0, 0.0, 0.0);
            this.setPos(dX, dY, dZ);
        } else {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity hit = pResult.getEntity();
        if (hit instanceof LivingEntity livingHit && this.getOwner() instanceof LivingEntity owner) {
            if (owner instanceof Player player)
                livingHit.hurt(level().damageSources().playerAttack(player), 5);
            else
                livingHit.hurt(level().damageSources().mobAttack(owner), 5);
            livingHit.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 2));
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(
                this.getId(),
                this.getUUID(),
                this.getX(),
                this.getY(),
                this.getZ(),
                this.getXRot(),
                this.getYRot(),
                this.getType(),
                i,
                new Vec3(this.speedX, this.speedY, this.speedZ),
                0.0
        );
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        double d0 = pPacket.getXa();
        double d1 = pPacket.getYa();
        double d2 = pPacket.getZa();
        this.speedX = d0;
        this.speedY = d1;
        this.speedZ = d2;
    }
}
