package pl.wolny.admincontrol.service.mock

import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.UnimplementedOperationException
import be.seeseemelk.mockbukkit.entity.ProjectileMock
import org.bukkit.Color
import org.bukkit.block.Block
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.UUID

class MockArrow(serverMock: ServerMock): ProjectileMock(serverMock, UUID.randomUUID()), Arrow {
    override fun getKnockbackStrength(): Int {
        throw UnimplementedOperationException()
    }

    override fun setKnockbackStrength(knockbackStrength: Int) {
        throw UnimplementedOperationException()
    }

    override fun getDamage(): Double {
        throw UnimplementedOperationException()
    }

    override fun setDamage(damage: Double) {
        throw UnimplementedOperationException()
    }

    override fun getPierceLevel(): Int {
        throw UnimplementedOperationException()
    }

    override fun setPierceLevel(pierceLevel: Int) {
        throw UnimplementedOperationException()
    }

    override fun isCritical(): Boolean {
        throw UnimplementedOperationException()
    }

    override fun setCritical(critical: Boolean) {
        throw UnimplementedOperationException()
    }

    override fun isInBlock(): Boolean {
        throw UnimplementedOperationException()
    }

    override fun getAttachedBlock(): Block? {
        throw UnimplementedOperationException()
    }

    override fun getPickupStatus(): AbstractArrow.PickupStatus {
        throw UnimplementedOperationException()
    }

    override fun setPickupStatus(status: AbstractArrow.PickupStatus) {
        throw UnimplementedOperationException()
    }

    override fun isShotFromCrossbow(): Boolean {
        throw UnimplementedOperationException()
    }

    override fun setShotFromCrossbow(shotFromCrossbow: Boolean) {
        throw UnimplementedOperationException()
    }

    override fun getItemStack(): ItemStack {
        throw UnimplementedOperationException()
    }

    override fun setNoPhysics(noPhysics: Boolean) {
        throw UnimplementedOperationException()
    }

    override fun hasNoPhysics(): Boolean {
        throw UnimplementedOperationException()
    }

    override fun setBasePotionData(data: PotionData) {
        throw UnimplementedOperationException()
    }

    override fun getBasePotionData(): PotionData {
        throw UnimplementedOperationException()
    }

    override fun getColor(): Color? {
        throw UnimplementedOperationException()
    }

    override fun setColor(color: Color?) {
        throw UnimplementedOperationException()
    }

    override fun hasCustomEffects(): Boolean {
        throw UnimplementedOperationException()
    }

    override fun getCustomEffects(): MutableList<PotionEffect> {
        throw UnimplementedOperationException()
    }

    override fun addCustomEffect(effect: PotionEffect, overwrite: Boolean): Boolean {
        throw UnimplementedOperationException()
    }

    override fun removeCustomEffect(type: PotionEffectType): Boolean {
        throw UnimplementedOperationException()
    }

    override fun hasCustomEffect(type: PotionEffectType?): Boolean {
        throw UnimplementedOperationException()
    }

    override fun clearCustomEffects() {
        throw UnimplementedOperationException()
    }
}