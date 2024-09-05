package zeropointnothing.foods_galore;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.time.Duration;
import java.time.Instant;

public class ModItems {
    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(FoodsGalore.MOD_ID, id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    // EFFECTS

    public static final StatusEffect DRUNK_EFFECT = new DrunkEffect();

    public static class DrunkEffect extends StatusEffect {
        protected DrunkEffect() {
            super(StatusEffectCategory.BENEFICIAL, 5837859);
        }


        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            //FoodsGalore.LOGGER.info(deltaTime.toString());
            return true;
        }

        // Ran when the effect is initially applied.
        @Override
        public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
//            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, ))
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, entity.getStatusEffect(DRUNK_EFFECT).getDuration(), amplifier));
            super.onApplied(entity, attributes, amplifier);
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            //if (entity instanceof PlayerEntity & deltaTime.toSeconds() > 60 - ((amplifier+1)*10))  {

            if (entity instanceof PlayerEntity & entity.getStatusEffect(DRUNK_EFFECT).isDurationBelow(20*((amplifier+1)*10))) {
                // entity.setOnFireFor(1);

                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * ((amplifier+1)*10), 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * ((amplifier+1)*10), 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * ((amplifier+1)*10), 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 20 * ((amplifier+1)*10), 3));
            }
        }
    }

    // EFFECTS END

    // FOODS
    public static final Item SEASONED_BREAD = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 10, 2), 1.0f).build())),
            "seasoned_bread"
    );

    public static final Item COOKED_BREAD = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().snack().hunger(7).build())),
            "cooked_bread"
    );

    public static final Item BREAD_SLICE = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().snack().hunger(4).build())),
            "bread_slice"
    );

    // food version of Berry Wine. Registered, but not obtainable via survival methods.
    public static final Item BERRY_WINE_MUNCH = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().alwaysEdible()
                    .statusEffect(new StatusEffectInstance(DRUNK_EFFECT, 20 * 60, 1), 1.0f).build())),
            "berry_wine_munchable"
    );

    // FOODS END

    // Item Group
    public static final RegistryKey<ItemGroup> FG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FoodsGalore.MOD_ID, "item_group"));
    public static final ItemGroup FG_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SEASONED_BREAD))
            .displayName(Text.translatable("itemGroup.foods_galore"))
            .build();


    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, FG_ITEM_GROUP_KEY, FG_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(FG_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.SEASONED_BREAD);
//            itemGroup.add(ModItems.BERRY_WINE_MUNCH);
            itemGroup.add(ModItems.COOKED_BREAD);
            itemGroup.add(ModItems.BREAD_SLICE);
            itemGroup.add(PotionUtil.setPotion(new ItemStack(Items.POTION), FoodsGalore.BERRY_WINE));
        });
    }
}
