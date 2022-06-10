package pl.wolny.admincontrol

import co.aikar.commands.*
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import pl.wolny.admincontrol.command.AdminCommand
import pl.wolny.admincontrol.service.AdminService
import java.io.File


class AdminControl : JavaPlugin {

    private lateinit var commandManager: PaperCommandManager

    val adminService = AdminService(dataFolder)

    constructor(){
    }

    protected constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File
    ): super(loader, description, dataFolder, file)



    override fun onEnable() {
        // Plugin startup logic
        adminService.init()
        Bukkit.getPluginManager().registerEvents(adminService, this)
        if(Bukkit.getServer()::class.java.name != "be.seeseemelk.mockbukkit.ServerMock"){
            registerCommands() //ACF does not work with mocking
        }


    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun registerCommands(){
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