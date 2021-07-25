package io.nationkit.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class playerHead {
    public ItemStack playerHead(String player, String displayName, String lore){
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();

        playerHeadMeta.setOwner(player);
        playerHeadMeta.setDisplayName(displayName);
        playerHeadMeta.setLore(Arrays.asList(lore));
        playerHead.setItemMeta(playerHeadMeta);

        return playerHead;
    }
}
