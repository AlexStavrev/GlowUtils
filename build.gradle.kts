import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.8.0")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://repo.dmulloy2.net/repository/public/")
    }
}

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    name = "GlowUtils"
    main = "me.wrexbg.glowutils.GlowUtilsPlugin"
    apiVersion = "1.19"
    authors = listOf("WrexBG")
    description = "Utilities to make a player glow"
    version = "1.0.0"
    depend = listOf("ProtocolLib")
}