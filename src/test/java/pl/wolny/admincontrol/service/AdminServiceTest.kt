package pl.wolny.admincontrol.service

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.audience.Audience
import org.bukkit.Material
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.plugin.Plugin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import pl.wolny.admincontrol.AdminControl
import pl.wolny.admincontrol.formatMessage
import pl.wolny.admincontrol.service.mock.MockArrow


internal class AdminServiceTest{

    private lateinit var serverMock: ServerMock
    private lateinit var plugin: AdminControl

    private lateinit var player1: PlayerMock
    private lateinit var player2: PlayerMock

    @BeforeEach
    fun setUp() {
        serverMock = MockBukkit.mock()
        //plugin = MockBukkit.createMockPlugin()
        plugin = MockBukkit.load(AdminControl::class.java)

        //setup plugin
        player1 = serverMock.addPlayer("playerOne")
        player2 = serverMock.addPlayer("playerTwo")
        plugin.adminService.setPvp(player1) //disable pvp
        plugin.adminService.setTnt(player1) //disable tnt
        plugin.adminService.setChat(player1) //disable chat
    }

    @Test
    fun testPvpPlayerLock(){
        val event = EntityDamageByEntityEvent(player1, player2, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 10.0)
        serverMock.pluginManager.callEvent(event)

        serverMock.pluginManager.assertEventFired(EntityDamageByEntityEvent::class.java)
        assertTrue(event.isCancelled)
    }


    @Test
    fun testPvpArrowLock(){
        val arrow = MockArrow(serverMock)
        arrow.shooter = player2

        val event = EntityDamageByEntityEvent(arrow, player1, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 10.0)
        serverMock.pluginManager.callEvent(event)

        serverMock.pluginManager.assertEventFired(EntityDamageByEntityEvent::class.java)
        assertTrue(event.isCancelled)
    }

    @Test
    @DisplayName("Test pvp-lava place lock")
    fun testPvpLavaPlaceLock(){
        plugin.adminService.setPvp(player1) //enable pvp

        val event = player1.simulateBlockPlace(Material.LAVA, player2.location)!!

        serverMock.pluginManager.assertEventFired(BlockPlaceEvent::class.java)
        assertTrue(!event.isCancelled)
    }

    @Test
    @DisplayName("Test pvp-fire place lock")
    fun testPvpFirePlaceLock(){
        plugin.adminService.setPvp(player1) //enable pvp

        val event = player1.simulateBlockPlace(Material.FIRE, player1.location)!!

        serverMock.pluginManager.assertEventFired(BlockPlaceEvent::class.java)
        assertTrue(!event.isCancelled)
    }

    @Test
    @DisplayName("Test tnt lock")
    fun testTntLock(){

        val event = ExplosionPrimeEvent(Mockito.mock(TNTPrimed::class.java))
        serverMock.pluginManager.callEvent(event)

        serverMock.pluginManager.assertEventFired(ExplosionPrimeEvent::class.java)
        assertTrue(event.isCancelled)
    }

    @Test
    @DisplayName("Test chat lock")
    fun testChatLock(){

        val event = AsyncChatEvent(false, player1, setOf<Audience>(player1, player2), ChatRenderer.defaultRenderer(), formatMessage("abc"), formatMessage("abc"))
        serverMock.pluginManager.callEvent(event)

        serverMock.pluginManager.assertEventFired(AsyncChatEvent::class.java)
        assertTrue(event.isCancelled)

        player1.isOp = true

        val allowedEvent = AsyncChatEvent(false, player1, setOf<Audience>(player1, player2), ChatRenderer.defaultRenderer(), formatMessage("abc"), formatMessage("abc"))
        serverMock.pluginManager.callEvent(allowedEvent)

        assertFalse(allowedEvent.isCancelled)
    }


    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

}