package zeropointnothing.foods_galore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodsGalore implements ModInitializer {
	public static final String MOD_ID = "foods-galore";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// POTIONS
	public static final Potion BERRY_WINE = Registry.register(
			Registries.POTION,
			new Identifier("foods-galore", "berry_wine"),
			new Potion(
					new StatusEffectInstance(
							ModItems.DRUNK_EFFECT,
							20 * 60,
							0
					)
			)
	);
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello from FG!!");

		ModItems.initialize();
		Registry.register(Registries.STATUS_EFFECT, new Identifier("foods-galore", "drunk"), ModItems.DRUNK_EFFECT);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.WATER, Items.SWEET_BERRIES, BERRY_WINE);
	}
}