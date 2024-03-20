package org.alter.plugins.content.mechanics.music

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.alter.game.Server
import org.alter.game.Server.Companion.logger
import org.alter.game.model.World
import org.alter.game.service.Service
import java.nio.file.Files
import java.nio.file.Paths

class MusicService : Service {

    val music = ObjectArrayList<Music>()

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val MusicFile = Paths.get(serviceProperties.get("area-music") ?: "../data/cfg/music/music.json")

        Files.newBufferedReader(MusicFile).use { reader ->
            val music = Gson().fromJson<ObjectArrayList<Music>>(reader, object: TypeToken<ObjectArrayList<Music>>() {}.type)
            this.music.addAll(music)
        }

        logger.info { "Loaded ${music.size} musics." }
    }

    override fun postLoad(server: Server, world: World) {}
    override fun bindNet(server: Server, world: World) {}
    override fun terminate(server: Server, world: World) {}
}