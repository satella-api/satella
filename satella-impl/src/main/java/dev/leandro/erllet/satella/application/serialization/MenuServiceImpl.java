package dev.leandro.erllet.satella.application.serialization;


import dev.leandro.erllet.satella.model.MenuItem;
import dev.leandro.erllet.satella.service.MenuService;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public Gui createMenuFromList(String menuName, Material menuIcon, Integer itemLength, Integer itemHeight, List<MenuItem> menuItems) {
        return createMenuFromList(menuName, menuIcon, 9, 6, itemLength, itemHeight, menuItems);
    }

    @Override
    public Gui createMenuFromList(String menuName, Material menuIcon, List<MenuItem> menuItems) {
        return createMenuFromList(menuName, menuIcon, 9, 6, 7, 2, menuItems);
    }



    @Override
    public Gui createMenuFromList(String menuName, Material menuIcon, Integer glassLength, Integer glassHeight, Integer itemLength, Integer itemHeight, List<MenuItem> menuItems) {
        val gui = new Gui( 6, menuName);
        val itemsPerPage = itemHeight * itemLength;
        val paneGlass = new OutlinePane(0, 0, glassLength, glassHeight);
        paneGlass.addItem(getItem(Material.BLACK_STAINED_GLASS_PANE, "&a"));
        paneGlass.setRepeat(true);

        val pane = new PaginatedPane(1, 2, itemLength, itemHeight);
        val pages = (int) Math.ceil(menuItems.stream().filter(Objects::nonNull).count() / (double) itemsPerPage);
        val pageValue = new AtomicReference<>(0);
        IntStream
                .rangeClosed(0, pages - 1)
                .mapToObj(operand -> menuItems.stream()
                        .skip(operand * itemsPerPage)
                        .limit(itemsPerPage)
                        .collect(Collectors.toList()))
                .map(this::getOutlinePane)
                .forEach(outlinePane -> pane.addPane(pageValue.getAndSet(pageValue.get() + 1), outlinePane));

        val close = new StaticPane(4, 5, 1, 1);
        close.addItem(new GuiItem(getItemStack(Material.BARRIER, "&cFechar"), event -> {
            event.setCancelled(true);
            event.getView().close();
        }), 0, 0);

        val yourSkills = new StaticPane(4, 0, 1, 1);
        yourSkills.addItem(getItem(menuIcon, menuName), 0, 0);

        gui.addPane(paneGlass);
        gui.addPane(pane);
        gui.addPane(yourSkills);
        gui.addPane(close);
        if(pages > 1) {
            formatPages(gui, pane);
        }
        return gui;
    }


    private GuiItem getItem(Material blackStainedGlassPane, String s) {
        return new GuiItem(getItemStack(blackStainedGlassPane, s), event -> event.setCancelled(true));
    }

    private void formatPages(Gui gui, PaginatedPane pane) {
        val back = new StaticPane(2, 5, 1, 1);
        val forward = new StaticPane(6, 5, 1, 1);

        back.addItem(new GuiItem(getItemStack(Material.ARROW, "&6Página anterior!"), event -> {
            pane.setPage(pane.getPage() - 1);
            if (pane.getPage() == 0) {
                back.setVisible(false);
            }
            forward.setVisible(true);
            gui.update();
        }), 0, 0);

        back.setVisible(false);

        forward.addItem(new GuiItem(getItemStack(Material.ARROW, "&6Próxima página!"), event -> {
            pane.setPage(pane.getPage() + 1);
            if (pane.getPage() == pane.getPages() - 1) {
                forward.setVisible(false);
            }
            back.setVisible(true);
            gui.update();
        }), 0, 0);

        gui.addPane(back);
        gui.addPane(forward);
    }

    private OutlinePane getOutlinePane(List<MenuItem> menuItems) {
        OutlinePane outlinePane = new OutlinePane(0, 0, 7, 2);
        menuItems.forEach(menuItem -> outlinePane.addItem(new GuiItem(menuItem.getIcon(), menuItem.getAction())));
        return outlinePane;
    }


    public ItemStack getItemStack(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(itemMeta);
        return item;
    }
}
