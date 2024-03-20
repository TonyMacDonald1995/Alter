package org.alter.plugins.service.sql.controllers

import io.github.oshai.kotlinlogging.KLogging
import org.alter.game.model.entity.Client
import org.alter.game.service.serializer.json.JsonPlayerSerializer

abstract class Controller {

    private val debug = false

    protected fun getContainers(client: Client): List<JsonPlayerSerializer.PersistentContainer> {
        val containers = mutableListOf<JsonPlayerSerializer.PersistentContainer>()

        client.containers.forEach { (key, container) ->
            if(!container.isEmpty) {
                containers.add(JsonPlayerSerializer.PersistentContainer(key.name, container.toMap()))
            }
        }

        return containers
    }

    fun log(message: String) {
        if (debug)
            logger.info { message }
    }

    companion object: KLogging()
}