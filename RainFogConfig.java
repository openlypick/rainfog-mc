package com.example.rainfog;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class RainFogConfig {
    /** config/rainfog.properties icinde enabled=false yapip oyunu yeniden baslatirsan yagmur kapanir. */
    public static boolean enabled = true;

    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("rainfog.properties");

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try (Reader r = Files.newBufferedReader(FILE)) {
            Properties p = new Properties();
            p.load(r);
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("enabled", Boolean.toString(enabled));
        try (Writer w = Files.newBufferedWriter(FILE)) {
            p.store(w, "Eternal Rain (client only)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RainFogConfig() {}
}
