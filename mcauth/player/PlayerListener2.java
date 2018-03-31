package mcauth.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import mcauth.MCAuth;

@SuppressWarnings("deprecation")
public class PlayerListener2 implements Listener {
	private MCAuth plugin;

	public PlayerListener2() {
		this.plugin = MCAuth.getPlugin();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth()) {
			if (!plugin.cm.isAllowBeforeAuth(event.getMessage()))
				event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPickup(PlayerPickupItemEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDrop(PlayerDropItemEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEditBook(PlayerEditBookEvent event) {
		if (!plugin.pm.getAuth(event.getPlayer().getName()).isAuth())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (!plugin.pm.getAuth(event.getEntity().getName()).isAuth())
				event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (!plugin.pm.getAuth(event.getDamager().getName()).isAuth())
				event.setCancelled(true);
		}
	}
}
