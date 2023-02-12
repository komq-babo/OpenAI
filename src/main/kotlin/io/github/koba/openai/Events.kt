package io.github.koba.openai

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.CompletionChoice
import com.theokanning.openai.completion.CompletionRequest
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.plugin.Plugin
import java.util.function.Consumer


class Events: Listener {

    private fun getInstance(): Plugin {
        return openAIPlugin.instance
    }

    @EventHandler
    fun onChat(event: PlayerChatEvent) {
        val prefix = getInstance().config.getString("prefix").toString()
        val prefixLength = prefix.length
        if(event.message == prefix) return
        if(event.message.startsWith("$prefix ")) {
            event.isCancelled = true
            Bukkit.broadcastMessage("<${event.player.name}> ${event.message}")
            val service = OpenAiService(getInstance().config.getString("token"))
            val completionRequest = CompletionRequest.builder()
                .prompt(event.message.substring(prefixLength + 1)) //TODO
                .model("text-davinci-003")
                .echo(true)
                .build()
            service.createCompletion(completionRequest).choices.forEach(Consumer { x: CompletionChoice? ->
                Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                    Bukkit.broadcastMessage("<${getInstance().config.getString("AINick")}> ${x?.text}")
                }, 10L)
            })
        }
    }
}