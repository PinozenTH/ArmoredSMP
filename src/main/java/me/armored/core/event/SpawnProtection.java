package me.armored.core.event;

import me.armored.core.Armored;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnProtection implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){ // edited by Pinont_
        Player p = e.getPlayer();
        if (!(Armored.build.contains(p.getName()))) {
            if (Armored.spawn.contains(e.getBlock().getLocation())) {
                if (!Armored.barn.contains(e.getBlock().getLocation()) && !Armored.farm.contains(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onSplash(PotionSplashEvent e){
        Entity entity = e.getEntity();
        if((!Armored.barn.contains(entity.getLocation()) || !Armored.farm.contains(entity.getLocation())) && Armored.spawn.contains(entity.getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!(Armored.build.contains(p.getName()))) {
            if (Armored.spawn.contains(e.getBlockPlaced().getLocation())) {
                if (!Armored.barn.contains(e.getBlockPlaced().getLocation()) && !Armored.farm.contains(e.getBlockPlaced().getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            if(b == null){return;}
            if (b.getType() == Material.SPRUCE_TRAPDOOR || b.getType() == Material.CAMPFIRE || b.getType() == Material.CANDLE || b.getType() == Material.OAK_TRAPDOOR || b.getType() == Material.DAYLIGHT_DETECTOR || b.getBlockData() instanceof Waterlogged || b.getType().name().startsWith("POTTED_") || b.getType() == Material.FLOWER_POT) {
                if (!(Armored.build.contains(p.getName())) && (!Armored.barn.contains(b.getLocation()) && !Armored.farm.contains(b.getLocation())) && Armored.spawn.contains(b.getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if((!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void FrameEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                if (!(Armored.build.contains(p.getName())) && (!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())) {
                    e.setCancelled(true);
                }
            }
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    Player p = (Player) ((Projectile) e.getDamager()).getShooter();
                    if (!(Armored.build.contains(p.getName())) && (!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())) {
                        e.getDamager().remove();
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onChangeBlock(EntityChangeBlockEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!(Armored.build.contains(p.getName())) && (!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation()) && e.getBlock().getType() != Material.BIG_DRIPLEAF) {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onCommandWE(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        if(cmd.contains("//") || cmd.toLowerCase().contains("/br") || cmd.toLowerCase().contains("/wea")) {
            if (!(Armored.build.contains(p.getName()))) {
                p.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Arrow(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if ((!Armored.barn.contains(e.getEntity().getLocation()) || !Armored.farm.contains(e.getEntity().getLocation())) && Armored.spawn.contains(e.getEntity().getLocation())) {
                Arrow a = (Arrow) e.getEntity();
                a.remove();
            }
        }
    }
    @EventHandler
    public void Trident(ProjectileLaunchEvent e){
        if (e.getEntity() instanceof Trident) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player) e.getEntity().getShooter();
                if (p == null) {
                    return;
                }
                if (p.getWorld() == Bukkit.getServer().getWorld("world")) {
                    if ((!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void TridentHit(ProjectileHitEvent e){
        if (e.getEntity() instanceof Trident) {
            if (e.getEntity().getShooter() instanceof Player) {
                Trident t = (Trident) e.getEntity();
                Player p = (Player) e.getEntity().getShooter();
                if (p == null) {
                    t.remove();
                    return;
                }
                if (p.getWorld() == Bukkit.getServer().getWorld("world")) {
                    if ((!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())) {
                        t.remove();
                    }
                }
            }
        }
    }

    // code by aomsin ChasingClub PVP&Lobby Region protection
    // Optimized by Pinont_ via Armored Plugin

    @EventHandler
    public void projectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!(Armored.build.contains(p.getName())) && (!Armored.barn.contains(p.getLocation()) || !Armored.farm.contains(p.getLocation())) && Armored.spawn.contains(p.getLocation())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (Armored.spawn.contains(mob.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

}
