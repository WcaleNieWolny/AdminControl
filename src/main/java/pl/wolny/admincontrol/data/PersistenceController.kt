package pl.wolny.admincontrol.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class PersistenceController(datafolder: File) {

    private val dataFile = File(datafolder, "data.json")
    var databaseFile = DatabaseFile()

    fun init(){
        var exists = true

        if (!dataFile.parentFile.exists()){
            dataFile.parentFile.mkdirs()
            exists = false
        }

        if (!dataFile.exists()){
            dataFile.createNewFile()
            flush()
            exists = false
        }

        if(exists){
            databaseFile = Json.decodeFromString(dataFile.readText(Charsets.UTF_8))
        }
    }

    fun flush(){
        dataFile.writeText(Json.encodeToString(databaseFile), Charsets.UTF_8)
    }

}