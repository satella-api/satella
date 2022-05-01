package dev.leandro.erllet.satella.service;


import dev.leandro.erllet.satella.model.MenuItem;
import com.github.stefvanschie.inventoryframework.Gui;
import org.bukkit.Material;

import java.util.List;


public interface MenuService {


    Gui createMenuFromList(String menuName, Material menuIcon, Integer itemLength, Integer itemHeight, List<MenuItem> menuItems);

    Gui createMenuFromList(String menuName, Material menuIcon, List<MenuItem> menuItems);

    Gui createMenuFromList(String menuName, Material menuIcon, Integer glassLength, Integer glassHeight, Integer itemLength, Integer itemHeight, List<MenuItem> menuItems);
}
