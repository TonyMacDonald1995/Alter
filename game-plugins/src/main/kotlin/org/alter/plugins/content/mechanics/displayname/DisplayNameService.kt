package org.alter.plugins.content.mechanics.displayname

import gg.rsmod.util.ServerProperties
import org.alter.game.Server
import org.alter.game.model.World
import org.alter.game.service.Service

class DisplayNameService : Service {
    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
    }

    override fun bindNet(server: Server, world: World) {}
    override fun postLoad(server: Server, world: World) {}
    override fun terminate(server: Server, world: World) {}
}