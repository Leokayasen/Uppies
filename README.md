# Uppies

A simple Paper 1.21.8+ Plugin for sitting on blocks and players

## Mod Details
- **Name:** Uppies
- **Version:** 1.0.0
- **Author:** k4i (BitWave Studios)

## Features
- `/sit` command to sit on the block you are standing on
- `/sit <player>` command to sit on another player
- `/ride <player>` alternative command to sit on another player
- Permission-based access control
- Configurable messages for various actions
- Configurable whitelist for blocks that players can sit on

## Permissions
- `uppies.sit` - Allows a player to use the `/sit` command
- `uppies.sit.others` - Allows a player to use the `/sit <player>` command
- `uppies.ride` - Allows a player to use the `/ride <player>` command
- `uppies.bypass` - Allows a player to sit on any block, bypassing the whitelist
- `uppies.reload` - Allows a player to reload the plugin configuration (requires OP)

## Configuration
See `config.yml` for configurable options such as messages and block whitelist.

## Installation
1. Download from the [releases page](https://github.com/Leokayasen/Uppies/releases).
2. Place the JAR file in your server's `plugins` directory.
3. Restart the server to generate the default configuration file.
4. Customize the `config.yml` as needed.
5. Reload the plugin using `/uppies reload` or restart the server.
6. Use the `/sit` and `/ride` commands to start sitting!

## Support
For support, please open an issue on the [GitHub repository](https://github.com/Leokayasen/Uppies/issues) or contact me directly via [Discord](https://discord.gg/2qDFMGb9Eb)
