package dev.leandro.erllet.satella.service;

import org.bukkit.Location;


public interface LocationSerializationService {

    String serializeLocation(Location location);

    Location deserializeLocation(String location);
}
