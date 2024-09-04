package zeropointnothing.foods_galore;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(FoodsGalore.MOD_ID, id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    // FOODS
    public static final Item SEASONED_BREAD = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 10, 2), 1.0f).build())),
            "seasoned_bread"
    );

    public static final Item BERRY_WINE_MUNCH = register(
            new Item(new Item.Settings().food(new FoodComponent.Builder().alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 60, 1), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 120, 3), 1.0f).build())),
            "berry_wine_munchable"
    );

    public static final RegistryKey<ItemGroup> FG_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FoodsGalore.MOD_ID, "item_group"));
    public static final ItemGroup FG_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SEASONED_BREAD))
            .displayName(Text.translatable("itemGroup.foods_galore"))
            .build();


    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, FG_ITEM_GROUP_KEY, FG_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(FG_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.SEASONED_BREAD);
            itemGroup.add(ModItems.BERRY_WINE_MUNCH);
            itemGroup.add(PotionUtil.setPotion(new ItemStack(Items.POTION), FoodsGalore.BERRY_WINE));
        });
    }
}
