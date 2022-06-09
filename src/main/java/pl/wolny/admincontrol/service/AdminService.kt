package pl.wolny.admincontrol.service

import org.bukkit.command.CommandSender
import pl.wolny.admincontrol.data.PersistenceController
import pl.wolny.admincontrol.formatMessage
import java.io.File

class AdminService(private val `data folder`: File) {

    val persistenceController = PersistenceController(`data folder`)

    fun init(){
        persistenceController.init()
    }

    fun setPvp(sender: CommandSender){
        val databaseFile = persistenceController.databaseFile
        databaseFile.pvp = !databaseFile.pvp
        sender.sendMessage(formatMessage("<green>Ustawiono PVP na: <red>${databaseFile.pvp}"))
        persistenceController.flush()
    }
}