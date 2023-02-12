package io.github.koba.openai

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class openAIPlugin: JavaPlugin() {
    companion object {
        lateinit var instance: openAIPlugin
    }

    init {
        instance = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(Events(), this)

        saveConfig()
        val configFile = File(dataFolder, "config.yml")
        if (configFile.length() == 0L) {
            config.options().copyDefaults(true)
            saveConfig()
        }
    }
}