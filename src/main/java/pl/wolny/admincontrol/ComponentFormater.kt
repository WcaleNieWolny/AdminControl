package pl.wolny.admincontrol

import net.kyori.adventure.text.minimessage.MiniMessage

val MINI_MESSAGE = MiniMessage.miniMessage()

fun formatMessage(string: String) = MINI_MESSAGE.deserialize(string)