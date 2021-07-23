package io.nationkit.ui.nationsMenu;

import io.nationkit.files.mainConfig;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class governmentMenu {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    private mainConfig data2;

    public void governmentMenu(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        this.data2 = new mainConfig(plugin.getPlugin(operator.class));
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String govType = data.getConfig().getString("nations." + name + ".govType");
        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
        int civicPointsRequired1 = data2.getConfig().getInt("settings.civicPointsRequired.cityState");
        int civicPointsRequired2 = data2.getConfig().getInt("settings.civicPointsRequired.feudalState");
        int civicPointsRequired3 = data2.getConfig().getInt("settings.civicPointsRequired.monarchy");
        int civicPointsRequired4 = data2.getConfig().getInt("settings.civicPointsRequired.theocracy");
        int civicPointsRequired5 = data2.getConfig().getInt("settings.civicPointsRequired.merchantRepublic");
        int civicPointsRequired6 = data2.getConfig().getInt("settings.civicPointsRequired.aristocracy");
        int civicPointsRequired7 = data2.getConfig().getInt("settings.civicPointsRequired.colonialEmpire");
        int civicPointsRequired8 = data2.getConfig().getInt("settings.civicPointsRequired.socialistRepublic");
        int civicPointsRequired9 = data2.getConfig().getInt("settings.civicPointsRequired.communistState");
        int civicPointsRequired10 = data2.getConfig().getInt("settings.civicPointsRequired.democracy");
        int civicPointsRequired11 = data2.getConfig().getInt("settings.civicPointsRequired.federalRepublic");
        int civicPointsRequired12 = data2.getConfig().getInt("settings.civicPointsRequired.totalitarianState");
        int civicPointsRequired13 = data2.getConfig().getInt("settings.civicPointsRequired.neoliberalDemocracy");

        Inventory inventory = Bukkit.createInventory(null, 27, "Government Type");

        ItemStack tribal = new ItemStack(Material.CRAFTING_TABLE);
        ItemStack cityState = new ItemStack(Material.ARROW);
        ItemStack feudal = new ItemStack(Material.WHEAT);
        ItemStack monarchy = new ItemStack(Material.DIAMOND);
        ItemStack theocracy = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack merchantRepublic = new ItemStack(Material.EMERALD);
        ItemStack aristocracy = new ItemStack(Material.QUARTZ_PILLAR);
        ItemStack colonialEmpire = new ItemStack(Material.WHITE_BANNER);
        ItemStack socialistRepublic = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemStack communistState = new ItemStack(Material.IRON_PICKAXE);
        ItemStack democracy = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
        ItemStack federalRepublic = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemStack totalitarianism = new ItemStack(Material.DIAMOND_AXE);
        ItemStack neoliberalDemocracy = new ItemStack(Material.ENCHANTED_BOOK);
        ItemStack currentGovernment = new ItemStack(Material.valueOf(govTypeMat));
        ItemStack locked = new ItemStack(Material.NETHERITE_HELMET);
        ItemStack filler1 = new ItemStack(Material.BOOKSHELF);
        ItemStack filler2 = new ItemStack(Material.IRON_BARS);
        ItemStack back = new ItemStack(Material.IRON_NUGGET);

        ItemMeta tribal_meta = tribal.getItemMeta();
        ItemMeta cityState_meta = cityState.getItemMeta();
        ItemMeta feudal_meta = feudal.getItemMeta();
        ItemMeta monarchy_meta = monarchy.getItemMeta();
        ItemMeta theocracy_meta = theocracy.getItemMeta();
        ItemMeta merchantRepublic_meta = merchantRepublic.getItemMeta();
        ItemMeta aristocracy_meta = aristocracy.getItemMeta();
        ItemMeta colonialEmpire_meta = colonialEmpire.getItemMeta();
        ItemMeta socialistRepublic_meta = socialistRepublic.getItemMeta();
        ItemMeta communistState_meta = communistState.getItemMeta();
        ItemMeta democracy_meta = democracy.getItemMeta();
        ItemMeta federalRepublic_meta = federalRepublic.getItemMeta();
        ItemMeta totalitarianism_meta = totalitarianism.getItemMeta();
        ItemMeta neoliberalDemocracy_meta = neoliberalDemocracy.getItemMeta();
        ItemMeta currentGovernmentMeta = currentGovernment.getItemMeta();
        ItemMeta locked_meta = locked.getItemMeta();
        ItemMeta filler1_meta = filler1.getItemMeta();
        ItemMeta filler2_meta = filler2.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        tribal_meta.setDisplayName(ChatColor.GOLD + "Tribal");
        tribal_meta.setLore(Arrays.asList(ChatColor.WHITE + "Ancient Government", ChatColor.AQUA + "1 civic point",
                ChatColor.GREEN + "+Nation power regenerates 5% faster"));
        tribal.setItemMeta(tribal_meta);

        cityState_meta.setDisplayName(ChatColor.GRAY + "City State");
        String cityLock;
        if(civicPoints > civicPointsRequired1 || civicPoints == civicPointsRequired1){
            cityLock = ChatColor.YELLOW + "Available";
        }else{
            cityLock = ChatColor.RED + "More civic points needed";
        }
        cityState_meta.setLore(Arrays.asList(ChatColor.WHITE + "Ancient Government", cityLock, ChatColor.AQUA + "1 civic point",
                ChatColor.GREEN + "+Daily gold production increased by 25%", ChatColor.RED + "-Nation power regenerates 15% slower"));
        cityState.setItemMeta(cityState_meta);

        feudal_meta.setDisplayName(ChatColor.GOLD + "Feudal State");
        String feudalLock;
        if(civicPoints > civicPointsRequired2 || civicPoints == civicPointsRequired2){
            feudalLock = ChatColor.YELLOW + "Available";
        }else{
            feudalLock = ChatColor.RED + "More civic points needed";
        }
        feudal_meta.setLore(Arrays.asList(ChatColor.WHITE + "Medieval Government", feudalLock, ChatColor.AQUA + "3 civic points",
                ChatColor.GREEN + "+All taxes yield 20% more", ChatColor.RED + "-Happiness regenerates 5% slower"));
        feudal.setItemMeta(feudal_meta);

        monarchy_meta.setDisplayName(ChatColor.AQUA + "Monarchy");
        String monLock;
        if(civicPoints > civicPointsRequired3 || civicPoints == civicPointsRequired3){
            monLock = ChatColor.YELLOW + "Available";
        }else{
            monLock = ChatColor.RED + "More civic points needed";
        }
        monarchy_meta.setLore(Arrays.asList(ChatColor.WHITE + "Medieval Government", monLock, ChatColor.AQUA + "5 civic points",
                ChatColor.GREEN + "+Daily gold production increased by 50%", ChatColor.GREEN + "+Nation power regenerates 25% faster",
                ChatColor.RED + "-Loyalty regenerates 50% slower"));
        monarchy.setItemMeta(monarchy_meta);

        theocracy_meta.setDisplayName(ChatColor.RED + "Theocracy");
        String theoLock;
        if(civicPoints > civicPointsRequired4 || civicPoints == civicPointsRequired4){
            theoLock = ChatColor.YELLOW + "Available";
        }else{
            theoLock = ChatColor.RED + "More civic points needed";
        }
        theocracy_meta.setLore(Arrays.asList(ChatColor.WHITE + "Medieval Government", theoLock, ChatColor.AQUA + "5 civic points",
                ChatColor.GREEN + "+Influence increased by 50%", ChatColor.RED + "-Happiness decreased by 25%"));
        theocracy.setItemMeta(theocracy_meta);

        merchantRepublic_meta.setDisplayName(ChatColor.GREEN + "Merchant Republic");
        String mercLock;
        if(civicPoints > civicPointsRequired5 || civicPoints == civicPointsRequired5){
            mercLock = ChatColor.YELLOW + "Available";
        }else{
            mercLock = ChatColor.RED + "More civic points needed";
        }
        merchantRepublic_meta.setLore(Arrays.asList(ChatColor.WHITE + "Medieval Government", mercLock, ChatColor.AQUA + "10 civic points",
                ChatColor.GREEN + "+Daily gold production doubled", ChatColor.GREEN + "+Loyalty increased by 50%",
                ChatColor.RED + "-Nation power regenerates 25% slower"));
        merchantRepublic.setItemMeta(merchantRepublic_meta);

        aristocracy_meta.setDisplayName(ChatColor.WHITE + "Aristocracy");
        String ariLock;
        if(civicPoints > civicPointsRequired6 || civicPoints == civicPointsRequired6){
            ariLock = ChatColor.YELLOW + "Available";
        }else{
            ariLock = ChatColor.RED + "More civic points needed";
        }
        aristocracy_meta.setLore(Arrays.asList(ChatColor.WHITE + "Medieval Government", ariLock, ChatColor.AQUA + "10 civic points",
                ChatColor.GREEN + "+Influence increased by 25%", ChatColor.GREEN + "+Daily gold production increased by 25%",
                ChatColor.RED + "-Happiness decreased by 15%", ChatColor.RED + "-Loyalty decreased by 40%"));
        aristocracy.setItemMeta(aristocracy_meta);

        colonialEmpire_meta.setDisplayName(ChatColor.BLUE + "Colonial Empire");
        String colLock;
        if(civicPoints > civicPointsRequired7 || civicPoints == civicPointsRequired7){
            colLock = ChatColor.YELLOW + "Available";
        }else{
            colLock = ChatColor.RED + "More civic points needed";
        }
        colonialEmpire_meta.setLore(Arrays.asList(ChatColor.WHITE + "Contemporary Government", colLock, ChatColor.AQUA + "15 civic points",
                ChatColor.GREEN + "+Influence doubled", ChatColor.GREEN + "+Daily gold production increased by 15%",
                ChatColor.RED + "-Happiness decreased by 50%"));
        colonialEmpire_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        colonialEmpire.setItemMeta(colonialEmpire_meta);

        socialistRepublic_meta.setDisplayName(ChatColor.GOLD + "Socialist Republic");
        String socLock;
        if(civicPoints > civicPointsRequired8 || civicPoints == civicPointsRequired8){
            socLock = ChatColor.YELLOW + "Available";
        }else{
            socLock = ChatColor.RED + "More civic points needed";
        }
        socialistRepublic_meta.setLore(Arrays.asList(ChatColor.WHITE + "Contemporary Government", socLock, ChatColor.AQUA + "20 civic points",
                ChatColor.GREEN + "+Influence increased by 50%", ChatColor.GREEN + "+Happiness increased by 25%",
                ChatColor.GREEN + "+Loyalty increased by 25%", ChatColor.RED + "-Daily gold production decreased by 50%"));
        socialistRepublic.setItemMeta(socialistRepublic_meta);

        communistState_meta.setDisplayName(ChatColor.RED + "Communist State");
        String comLock;
        if(civicPoints > civicPointsRequired9 || civicPoints == civicPointsRequired9){
            comLock = ChatColor.YELLOW + "Available";
        }else{
            comLock = ChatColor.RED + "More civic points needed";
        }
        communistState_meta.setLore(Arrays.asList(ChatColor.WHITE + "Contemporary Government", comLock, ChatColor.AQUA + "20 civic points",
                ChatColor.GREEN + "+Loyalty tripled", ChatColor.GREEN + "+Influence doubled",
                ChatColor.RED + "-Happiness decreased by 25%", ChatColor.RED + "-Daily gold production decreased by 50%"));
        communistState_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        communistState.setItemMeta(communistState_meta);

        democracy_meta.setDisplayName(ChatColor.GOLD + "Democracy");
        String demoLock;
        if(civicPoints > civicPointsRequired10 || civicPoints == civicPointsRequired10){
            demoLock = ChatColor.YELLOW + "Available";
        }else{
            demoLock = ChatColor.RED + "More civic points needed";
        }
        democracy_meta.setLore(Arrays.asList(ChatColor.WHITE + "Contemporary Government", demoLock, ChatColor.AQUA + "20 civic points",
                ChatColor.GREEN + "+Loyalty quadrupled", ChatColor.GREEN + "+Daily gold production increased by 50%",
                ChatColor.GREEN + "+Happiness increased by 35%", ChatColor.RED + "-Influence decreased by 90%"));
        democracy.setItemMeta(democracy_meta);

        federalRepublic_meta.setDisplayName(ChatColor.GREEN + "Federal Republic");
        String fedLock;
        if(civicPoints > civicPointsRequired11 || civicPoints == civicPointsRequired11){
            fedLock = ChatColor.YELLOW + "Available";
        }else{
            fedLock = ChatColor.RED + "More civic points needed";
        }
        federalRepublic_meta.setLore(Arrays.asList(ChatColor.WHITE + "Modern Government", fedLock, ChatColor.AQUA + "30 civic points",
                ChatColor.GREEN + "+Loyalty doubled", ChatColor.GREEN + "+Influence increased by 50%",
                ChatColor.GREEN + "+Happiness increased by 25%", ChatColor.RED + "-Daily gold production decreased by 60%"));
        federalRepublic.setItemMeta(federalRepublic_meta);

        totalitarianism_meta.setDisplayName(ChatColor.BLUE + "Totalitarian State");
        String totLock;
        if(civicPoints > civicPointsRequired12 || civicPoints == civicPointsRequired12){
            totLock = ChatColor.YELLOW + "Available";
        }else{
            totLock = ChatColor.RED + "More civic points needed";
        }
        totalitarianism_meta.setLore(Arrays.asList(ChatColor.WHITE + "Modern Government", totLock, ChatColor.AQUA + "30 civic points",
                ChatColor.GREEN + "+Power regenerates 500% faster", ChatColor.GREEN + "+Daily gold production quadrupled",
                ChatColor.RED + "-Happiness set to 0", ChatColor.RED + "-Loyalty decreased by 75%"));
        totalitarianism_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        totalitarianism.setItemMeta(totalitarianism_meta);

        neoliberalDemocracy_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Neoliberal Democracy");
        String neoLock;
        if(civicPoints > civicPointsRequired13 || civicPoints == civicPointsRequired13){
            neoLock = ChatColor.YELLOW + "Available";
        }else{
            neoLock = ChatColor.RED + "More civic points needed";
        }
        neoliberalDemocracy_meta.setLore(Arrays.asList(ChatColor.WHITE + "Modern Government", neoLock, ChatColor.AQUA + "30 civic points",
                ChatColor.GREEN + "+Maximum happiness at all times", ChatColor.GREEN + "+Loyalty increased by 100%", ChatColor.RED + "-Daily gold production decreased by 55%",
                ChatColor.RED + "-Power regenerates 75% slower"));
        neoliberalDemocracy_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        neoliberalDemocracy.setItemMeta(neoliberalDemocracy_meta);

        currentGovernmentMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', govType));
        currentGovernmentMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Current government type"));
        currentGovernmentMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        currentGovernment.setItemMeta(currentGovernmentMeta);

        locked_meta.setDisplayName(ChatColor.GRAY + "LOCKED");
        locked_meta.setLore(Arrays.asList(ChatColor.RED + "Locked because: insert reason"));
        locked.setItemMeta(locked_meta);

        filler1_meta.setDisplayName(" ");
        filler1.setItemMeta(filler1_meta);

        filler2_meta.setDisplayName(" ");
        filler2.setItemMeta(filler2_meta);

        back_meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "BACK");
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);
        inventory.setItem(1, tribal);
        inventory.setItem(2, cityState);
        inventory.setItem(3, feudal);
        inventory.setItem(4, monarchy);
        inventory.setItem(5, theocracy);
        inventory.setItem(6, merchantRepublic);
        inventory.setItem(7, aristocracy);
        inventory.setItem(8, filler1);
        inventory.setItem(9, filler1);
        inventory.setItem(10, colonialEmpire);
        inventory.setItem(11, socialistRepublic);
        inventory.setItem(12, communistState);
        inventory.setItem(13, democracy);
        inventory.setItem(14, federalRepublic);
        inventory.setItem(15, totalitarianism);
        inventory.setItem(16, neoliberalDemocracy);
        inventory.setItem(17, filler1);
        inventory.setItem(18, filler1);
        inventory.setItem(19, filler2);
        inventory.setItem(20, filler2);
        inventory.setItem(21, filler2);
        inventory.setItem(22, currentGovernment);
        inventory.setItem(23, filler2);
        inventory.setItem(24, filler2);
        inventory.setItem(25, filler2);
        inventory.setItem(26, filler1);

        player.openInventory(inventory);
    }
}
