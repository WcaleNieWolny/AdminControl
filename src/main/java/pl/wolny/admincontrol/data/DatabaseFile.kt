package pl.wolny.admincontrol.data

import kotlinx.serialization.Serializable

@Serializable
class DatabaseFile {
    var pvp = true
    var tnt = true
    var chat = true
}