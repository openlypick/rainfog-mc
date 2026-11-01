package com.example.rainfog;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class RainFogConfig {
    /** Yağmur açık/kapalı. */
    public static boolean enabled = true;
    /** Gökyüzü sunucuda gündüz olsa bile gece gibi görünsün (sadece senin ekranında). */
    public static boolean night = false;
    /** Yağmur şiddeti 0.0 - 1.0 */
    public static float rainIntensity = 1.0f;
    /** Yağmur (ve gök gürültüsü) ses seviyesi 0.0 - 1.0 */
    public static float rainVolume = 1.0f;

    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("rainfog.properties");

    private static float clamp(float v) {
        return Math.max(0.0f, Math.min(1.0f, v));
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try (Reader r = Files.newBufferedReader(FILE)) {
            Properties p = new Properties();
            p.load(r);
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
            night = Boolean.parseBoolean(p.getProperty("night", "false"));
            rainIntensity = clamp(Float.parseFloat(p.getProperty("rainIntensity", "1.0")));
            rainVolume = clamp(Float.parseFloat(p.getProperty("rainVolume", "1.0")));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("enabled", Boolean.toString(enabled));
        p.setProperty("night", Boolean.toString(night));
        p.setProperty("rainIntensity", Float.toString(rainIntensity));
        p.setProperty("rainVolume", Float.toString(rainVolume));
        try (Writer w = Files.newBufferedWriter(FILE)) {
            p.store(w, "Eternal Rain (client only)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RainFogConfig() {}
}
