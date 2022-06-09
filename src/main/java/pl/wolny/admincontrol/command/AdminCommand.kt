package pl.wolny.admincontrol.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.wolny.admincontrol.formatMessage
import pl.wolny.admincontrol.service.AdminService


@CommandAlias("admin")
@CommandPermission("admincontrol.useo")
class AdminCommand: BaseCommand() {

    @field:Dependency("adminService")
    private lateinit var adminService: AdminService

    @Subcommand("help")
    @CatchUnknown
    @Default
    @HelpCommand
    fun getHelp(sender: CommandSender){
        sender.sendMessage(formatMessage("<green>Użycie: <red>pvp | tnt | chat"))
    }


    @Subcommand("pvp")
    fun setPvp(sender: CommandSender) {
        adminService.setPvp(sender)
    }
}

