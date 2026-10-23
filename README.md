# Mute Mod 🔇

A lightweight, client-side Fabric mod for Minecraft 26.3 that allows you to instantly toggle master volume mute on and off with a single keybind.

---

## ✨ Features

- **Toggle Mute Keybind:** Quickly mute and unmute master audio using a rebindable key (configurable in *Options -> Key Binds*).
- **Non-Destructive Muting:** Uses an internal audio mixin to silence the sound engine dynamically. It **never** alters or overwrites your vanilla `options.txt` volume setting.
- **Restores Exact Volume:** Unmuting reliably returns your sound to your exact previous volume percentage (never resets to 100%).
- **Smart Slider Detection:** If you adjust the Master Volume slider manually in *Options -> Music & Sounds* while muted, the mod automatically detects this and unmutes to your new level.
- **Auto-Unmute on Disconnect:** Automatically resets mute when disconnecting from a server or quitting a singleplayer world, preventing accidental silence on the main menu.
- **Action Bar Feedback:** Shows clean, subtle overlay notifications (e.g. *"Muted"* and *"Master Volume: 60%"*) in the action bar.

---

## 📦 Requirements

- **Minecraft:** 26.3
- **Fabric Loader:** >= 0.20.1
- **Fabric API:** 0.165.0+26.3
- **Fabric Language Kotlin:** 1.14.2+
- **Java:** 25

---

## 🚀 Installation

1. Make sure you have [Fabric Loader](https://fabricmc.net/) installed for Minecraft 26.3.
2. Download `mute-mod-1.1.0.jar` from the [Releases](https://github.com/yukentsii/mute-mod/releases) page.
3. Place the `.jar` file into your `.minecraft/mods` directory (or your launcher's mod folder).
4. Launch the game, head to *Options -> Controls -> Key Binds*, and configure the **Toggle Mute** key under the **Mute Mod** category (unbound by default).

---

## 🛠️ Building from Source

```bash
git clone https://github.com/yukentsii/mute-mod.git
cd mute-mod
./gradlew build
```

The compiled mod JAR will be located in `build/libs/`.

---

## 📄 License

This project is licensed under the [CC0 1.0 Universal](LICENSE) license.
