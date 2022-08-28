# GlowUtils
A lightweight Minecraft API to hangle player glowing
This does not handle the color in which players glow. This will be determited by the Minecraft team they are in.

Tested Minecraft Version: 1.19.2\
Dependencies: [ProtocolLib 4.8.0+](https://www.spigotmc.org/resources/protocollib.1997/) 


API Class: me.wrexbg.glowutils.GlowUtilsAPI
## Making a player glow
```java code
GlowUtilsAPI.makeGlow(glowPlayer, player);
```
You can also do multiple entires at once by providing a Set<Player> in both parameters

## Removing a glow
```java code
GlowUtilsAPI.removeGlow(glowPlayer, player);
```
You can also remove multiple entires at once by providing a Set<Player> in both parameters

## Checking a glow
```java code
if(GlowUtilsAPI.isGlowing(glowPlayer, player)) {
    //code
}
```
You can also check multiple entires at once by providing a Set<Player> in both parameters
