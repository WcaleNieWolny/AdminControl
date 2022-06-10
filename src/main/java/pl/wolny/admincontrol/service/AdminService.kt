package pl.wolny.admincontrol.service

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.type.TNT
import org.bukkit.command.CommandSender
import org.bukkit.entity.Arrow
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.Explosive
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.util.BoundingBox
import pl.wolny.admincontrol.data.DatabaseFile
import pl.wolny.admincontrol.data.PersistenceController
import pl.wolny.admincontrol.formatMessage
import java.io.File
import java.util.StringJoiner

class AdminService(private val `data folder`: File): Listener {

    private val persistenceController = PersistenceController(`data folder`)
    private val databaseFile: DatabaseFile
        get() {
            return persistenceController.databaseFile //We want the latest one (Internally in the persistenceController databaseFile changes)
        }

    fun init(){
        persistenceController.init()
    }

    fun setPvp(sender: CommandSender){
        databaseFile.pvp = !databaseFile.pvp
        sender.sendMessage(formatMessage("<green>Ustawiono PVP na: <red>${databaseFile.pvp}"))
        announceChange("PVP", databaseFile.pvp)
        persistenceController.flush()
    }

    fun setTnt(sender: CommandSender){
        databaseFile.tnt = !databaseFile.tnt
        sender.sendMessage(formatMessage("<green>Ustawiono TNT na: <red>${databaseFile.tnt}"))
        announceChange("TNT", databaseFile.tnt)
        persistenceController.flush()
    }

    fun setChat(sender: CommandSender) {
        databaseFile.chat = !databaseFile.chat
        sender.sendMessage(formatMessage("<green>Ustawiono chat na: <red>${databaseFile.chat}"))
        announceChange("Używanie chatu", databaseFile.chat)
        persistenceController.flush()
    }

    fun announceChange(thing: String, value: Boolean){
        if(value){
            Bukkit.broadcast(formatMessage("<dark_green>✓ <blue>| <green>${thing} zostało włączone!"))
        }else{
            Bukkit.broadcast(noMessage("$thing zostało wyłączone"))
        }
    }

    fun noMessage(msg: String) = formatMessage("<dark_red>✗ <blue>| <red>${msg}")

    fun sendNoPvp(player: Player) = player.sendMessage(noMessage("PVP jest wyłączone!"))

    @EventHandler(ignoreCancelled = true)
    private fun onPlayerDamage(event: EntityDamageByEntityEvent){
        val player = event.entity
        if(player !is Player || databaseFile.pvp){
            return
        }

        val damager = event.damager
        if(damager is Player){
            event.isCancelled = true
            sendNoPvp(player)
        }

        if(damager is Projectile){
            if(damager.shooter is Player){
                event.isCancelled = true
                sendNoPvp(player)
            }
        }
    }

    @EventHandler
    private fun onBlockPlace(event: BlockPlaceEvent){
        if(databaseFile.pvp){
            return
        }

        if(event.block.type == Material.LAVA || event.block.type == Material.FIRE){
            val loc = event.block.location
            if(event.block.location.world.getNearbyEntities(
                    BoundingBox.of(
                        loc.clone().add(3.0, 3.0, 3.0),
                        loc.clone().subtract(3.0, 3.0, 3.0)
                    )
                ).filterIsInstance<Player>().any{
                    it != event.player
                }
            ){
                sendNoPvp(event.player)
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    private fun onEntityPrimeEvent(event: ExplosionPrimeEvent){
        if(databaseFile.tnt){
            return
        }
        if(event.entity !is Explosive){
            return
        }
        event.isCancelled = true
    }

    @EventHandler
    private fun onChatAsnyc(event: AsyncChatEvent){
        if(databaseFile.chat){
            return
        }
        if(event.player.hasPermission("admincontrol.chat")){
            return
        }
        event.isCancelled = true
        event.player.sendMessage(noMessage("chat jest wyłączony!"))
    }

    fun status() = arrayOf(databaseFile.pvp, databaseFile.tnt, databaseFile.chat)

}