package commands.minecraft

import minecraft_server_ping.MinecraftPing
import minecraft_server_ping.MinecraftPingOptions
import minecraft_server_ping.MinecraftPingReply
import java.io.IOException


class McServerPinger(val ip: String, val port: Int = 25565) {

    private var onlineStatus: Boolean = false
        set(value) {
            if (onlineStatus != value) {
                onChange?.invoke(this)
            }
            field = value
        }
    private var lastInfo : MinecraftPingReply? = null

    var onChange: ((McServerPinger) -> Unit)? = null

    var enabled: Boolean = true
    val serverInfo : MinecraftPingReply?
        get() {return lastInfo}
    val isOnline : Boolean
        get() {return onlineStatus}


    init {
        Thread {
            while (enabled) {
                check()
                Thread.sleep(5000)
            }
        }.start()
    }

    private fun check() {
        val pingOptions = MinecraftPingOptions()
        pingOptions.hostname = ip
        pingOptions.port = port
//        pingOptions.timeout = TIMEOUT_LENGTH
        onlineStatus = try {
            val pingResult = MinecraftPing().getPing(pingOptions)
            lastInfo = pingResult
            pingResult.description.text.contains("Collaide")
        } catch (e : IOException) {
            e.printStackTrace()
            false
        }
    }


    companion object {
        private const val TIMEOUT_LENGTH = 2000
    }
}
