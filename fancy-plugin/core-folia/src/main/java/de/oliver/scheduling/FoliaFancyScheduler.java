package de.oliver.scheduling;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class FoliaFancyScheduler implements FancyScheduler {

    ScheduledTask scheduledTask;

    JavaPlugin plugin;

    public FoliaFancyScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull FancyScheduler runTask(Location location, Runnable task) {
        scheduledTask = plugin.getServer().getRegionScheduler().run(plugin, location, scheduledTask1 -> task.run());
        return this;
    }

    @Override
    public @NotNull FancyScheduler runTaskAsynchronously(Runnable task) {
        scheduledTask = plugin.getServer().getAsyncScheduler().runNow(plugin, scheduledTask1 -> task.run());
        return this;
    }

    @Override
    public @NotNull FancyScheduler runTaskLater(Location location, long delay, Runnable task) {
        scheduledTask = plugin.getServer().getRegionScheduler().runDelayed(plugin, location, scheduledTask1 -> task.run(), delay);
        return this;
    }

    @Override
    public @NotNull FancyScheduler runTaskLaterAsynchronously(long delay, Runnable task) {
        scheduledTask = plugin.getServer().getAsyncScheduler().runDelayed(plugin, scheduledTask1 -> task.run(), delay, TimeUnit.SECONDS);
        return this;
    }

    @Override
    public @NotNull FancyScheduler runTaskTimer(Location location, long delay, long period, Runnable task) {
        scheduledTask = plugin.getServer().getRegionScheduler().runAtFixedRate(plugin, location, scheduledTask1 -> task.run(), delay, period);
        return this;
    }

    @Override
    public @NotNull FancyScheduler runTaskTimerAsynchronously(long delay, long period, Runnable task) {
        scheduledTask = plugin.getServer().getAsyncScheduler().runAtFixedRate(plugin, scheduledTask1 -> task.run(), delay, period, TimeUnit.SECONDS);
        return this;
    }

    @Override
    public void cancel() {
        if (!scheduledTask.isCancelled()) {
            scheduledTask.cancel();
        }
    }
}
