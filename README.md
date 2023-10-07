# Playstyles
Made for the BeyondAI Minecraft Server because people wanted it

## Commands
There's only one command:
`/playstyle` (Also `/ps` for short)

Executing will print the available playstyles in chat.
You can click on them to select them.

## Permissions
There's only one permission:
`playstyles.use`

## Playstyles
Currently four playstyles implemented
- **Difficult** - You take 1.5x damage
- **Normal** - Nothing Changes
- **Retain** - You keep your inventory on death
- **Peaceful** - Mobs won't target you

Other changes for each playstyle can be found in-game after hovering over each available playstyle when listed using `/playstyle`

## Building
This project uses [Maven](https://maven.apache.org/) to build.
To build, follow these steps:
1. Clone the repository
2. Run `mvn clean package` in the root directory
3. The built jar will be in the `target` directory

## Contributing
If you want to contribute, feel free to open a pull request.
If the project gains enough traction I'll also add a CONTRIBUTING.md but right now it's not needed.

## Credits
[PaperMC](https://papermc.io/) for [Paper](https://papermc.io/software/paper)  
[Aikar](https://github.com/aikar) for [acf](https://github.com/aikar/commands)  
[Google](https://github.com/google) for [Gson](https://github.com/google/gson)


## License
This project is licensed under the [GNU GPLv3](https://choosealicense.com/licenses/gpl-3.0/) license.

If you use **ANY** code from the source:
- You must disclose the source code of your modified work and the source code you took from this project. This means you are not allowed to use code from this project (even partially) in a closed-source and/or obfuscated application.
- You must state clearly and obviously to all end users that you are using code from this project.
- Your application must also be licensed under the same license.
