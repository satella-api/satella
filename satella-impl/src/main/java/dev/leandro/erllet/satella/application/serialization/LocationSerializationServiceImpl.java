package dev.leandro.erllet.satella.application.serialization;

import dev.leandro.erllet.satella.service.LocationSerializationService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LocationSerializationServiceImpl implements LocationSerializationService {

    @Override
    public String serializeLocation(Location l) {
        if (l == null) {
            return "";
        }
        String s = "";
        s = s + "@w;" + Objects.requireNonNull(l.getWorld()).getName();
        s = s + ":@x;" + l.getX();
        s = s + ":@y;" + l.getY();
        s = s + ":@z;" + l.getZ();
        s = s + ":@p;" + l.getPitch();
        s = s + ":@ya;" + l.getYaw();
        return s;
    }

    @Override
    public Location deserializeLocation(String s) {
        try {
            Location l = new Location(Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D);
            String[] att = s.split(":");
            String[] arrayOfString1;
            int j = (arrayOfString1 = att).length;
            for (int i = 0; i < j; i++) {
                String attribute = arrayOfString1[i];
                String[] split = attribute.split(";");
                if (split[0].equalsIgnoreCase("@w")) {
                    l.setWorld(Bukkit.getWorld(split[1]));
                }
                if (split[0].equalsIgnoreCase("@x")) {
                    l.setX(Double.parseDouble(split[1]));
                }
                if (split[0].equalsIgnoreCase("@y")) {
                    l.setY(Double.parseDouble(split[1]));
                }
                if (split[0].equalsIgnoreCase("@z")) {
                    l.setZ(Double.parseDouble(split[1]));
                }
                if (split[0].equalsIgnoreCase("@p")) {
                    l.setPitch(Float.parseFloat(split[1]));
                }
                if (split[0].equalsIgnoreCase("@ya")) {
                    l.setYaw(Float.parseFloat(split[1]));
                }
            }
            return l;
        } catch (Exception ignored) {
        }
        return null;
    }
}
