package pl.wolny.admincontrol

import co.aikar.commands.*
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.admincontrol.command.AdminCommand
import pl.wolny.admincontrol.service.AdminService


class AdminControl : JavaPlugin() {

    private lateinit var commandManager: PaperCommandManager

    private val adminService = AdminService(dataFolder)

    override fun onEnable() {
        // Plugin startup logic
        adminService.init()
        registerCommands()

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun registerCommands(){
        this.commandManager = PaperCommandManager(this)
        commandManager
        commandManager.enableUnstableAPI("brigadier")
        commandManager.enableUnstableAPI("help")

        commandManager.defaultExceptionHandler =
            ExceptionHandler { command: BaseCommand, _: RegisteredCommand<*>?, _: CommandIssuer?, _: List<String?>?, _: Throwable? ->
                logger.warning("Error occurred while executing command " + command.name)
                false
            }

        commandManager.registerDependency(AdminService::class.java, "adminService", adminService)

        commandManager.registerCommand(AdminCommand())
    }
}