package pl.wolny.admincontrol.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.wolny.admincontrol.formatMessage
import pl.wolny.admincontrol.service.AdminService


@CommandAlias("admin")
class AdminCommand: BaseCommand() {

    @field:Dependency("adminService")
    private lateinit var adminService: AdminService

    @Subcommand("help")
    @CatchUnknown
    @Default
    @HelpCommand
    @CommandPermission("admincontrol.use")
    fun getHelp(sender: CommandSender){
        sender.sendMessage(formatMessage("<green>Użycie: <red>pvp | tnt | chat"))
    }


    @Subcommand("pvp")
    @CommandPermission("admincontrol.use")
    fun setPvp(sender: CommandSender) {
        adminService.setPvp(sender)
    }

    @Subcommand("tnt")
    @CommandPermission("admincontrol.use")
    fun setTnt(sender: CommandSender) {
        adminService.setTnt(sender)
    }

    @Subcommand("chat")
    @CommandPermission("admincontrol.use")
    fun setChat(sender: CommandSender) {
        adminService.setChat(sender)
    }

    @Subcommand("status")
    fun getStatus(sender: CommandSender){
        val status = adminService.status()
        sender.sendMessage(formatMessage("<green>Status:")
            .append(Component.newline())
            .append(formatStatus("PVP", status[0]))
            .append(Component.newline())
            .append(formatStatus("TNT", status[1]))
            .append(Component.newline())
            .append(formatStatus("chat", status[2]))
        )
    }

    private fun formatStatus(thing: String, status: Boolean) = if(status) formatMessage("<green>${thing} <blue>► <dark_green>✓") else formatMessage("<red>${thing} <blue>► <dark_red>✗")

}

