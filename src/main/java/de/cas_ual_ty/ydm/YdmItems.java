package de.cas_ual_ty.ydm;

import java.util.function.Supplier;

import de.cas_ual_ty.ydm.card.CardItem;
import de.cas_ual_ty.ydm.card.CardSleevesItem;
import de.cas_ual_ty.ydm.card.CardSleevesType;
import de.cas_ual_ty.ydm.cardbinder.CardBinderItem;
import de.cas_ual_ty.ydm.deckbox.DeckBoxItem;
import de.cas_ual_ty.ydm.deckbox.PatreonDeckBoxItem;
import de.cas_ual_ty.ydm.duel.dueldisk.DuelDiskItem;
import de.cas_ual_ty.ydm.set.CardSetItem;
import de.cas_ual_ty.ydm.set.OpenedCardSetItem;
import de.cas_ual_ty.ydm.simplebinder.SimpleBinderItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YdmItems
{
    private static final DeferredRegister<Item> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, YDM.MOD_ID);
    static public final RegistryObject<Item> BLANC_CARD = DEFERRED_REGISTER.register("blanc_card", null);
    static public final RegistryObject<Item> CARD_BACK = DEFERRED_REGISTER.register("card_back", null);
    static public final RegistryObject<Item> BLANC_SET = DEFERRED_REGISTER.register("blanc_set", null);
    static public final RegistryObject<CardItem> CARD = DEFERRED_REGISTER.register("card", null);
    static public final RegistryObject<CardSetItem> SET = DEFERRED_REGISTER.register("set", null);
    static public final RegistryObject<OpenedCardSetItem> OPENED_SET = DEFERRED_REGISTER.register("opened_set", null);
    static public final RegistryObject<CardBinderItem> CARD_BINDER = DEFERRED_REGISTER.register("card_binder", null);
    static public final RegistryObject<Item> DUEL_PLAYMAT = DEFERRED_REGISTER.register("duel_playmat", null);
    static public final RegistryObject<Item> DUEL_TABLE = DEFERRED_REGISTER.register("duel_table", null);
    static public final RegistryObject<Item> CARD_SUPPLY = DEFERRED_REGISTER.register("card_supply", null);
    static public final RegistryObject<SimpleBinderItem> SIMPLE_BINDER_3 = DEFERRED_REGISTER.register("simple_binder_" + 3, () -> SimpleBinderItem.makeItem(YDM.MOD_ID, YDM.ydmItemGroup, 3));
    static public final RegistryObject<SimpleBinderItem> SIMPLE_BINDER_9 = DEFERRED_REGISTER.register("simple_binder_" + 9, () -> SimpleBinderItem.makeItem(YDM.MOD_ID, YDM.ydmItemGroup, 9));
    static public final RegistryObject<SimpleBinderItem> SIMPLE_BINDER_27 = DEFERRED_REGISTER.register("simple_binder_" + 27, () -> SimpleBinderItem.makeItem(YDM.MOD_ID, YDM.ydmItemGroup, 27));
    static public final RegistryObject<Item> MILLENIUM_EYE = DEFERRED_REGISTER.register("millennium_eye", null);
    static public final RegistryObject<Item> MILLENIUM_KEY = DEFERRED_REGISTER.register("millennium_key", null);
    static public final RegistryObject<Item> MILLENIUM_NECKLACE = DEFERRED_REGISTER.register("millennium_necklace", null);
    static public final RegistryObject<Item> MILLENIUM_PUZZLE = DEFERRED_REGISTER.register("millennium_puzzle", null);
    static public final RegistryObject<Item> MILLENIUM_RING = DEFERRED_REGISTER.register("millennium_ring", null);
    static public final RegistryObject<Item> MILLENIUM_ROD = DEFERRED_REGISTER.register("millennium_rod", null);
    static public final RegistryObject<Item> MILLENIUM_SCALE = DEFERRED_REGISTER.register("millennium_scale", null);
    static public final RegistryObject<Item> DUEL_DISK = DEFERRED_REGISTER.register("duel_disk", null);
    static public final RegistryObject<Item> CHAOS_DISK = DEFERRED_REGISTER.register("chaos_disk", null);
    static public final RegistryObject<Item> ACADEMIA_DISK = DEFERRED_REGISTER.register("academia_disk", null);
    static public final RegistryObject<Item> ACADEMIA_DISK_RED = DEFERRED_REGISTER.register("academia_disk_red", null);
    static public final RegistryObject<Item> ACADEMIA_DISK_BLUE = DEFERRED_REGISTER.register("academia_disk_blue", null);
    static public final RegistryObject<Item> ACADEMIA_DISK_YELLOW = DEFERRED_REGISTER.register("academia_disk_yellow", null);
    static public final RegistryObject<Item> ROCK_SPIRIT_DISK = DEFERRED_REGISTER.register("rock_spirit_disk", null);
    static public final RegistryObject<Item> TRUEMAN_DISK = DEFERRED_REGISTER.register("trueman_disk", null);
    static public final RegistryObject<Item> JEWEL_DISK = DEFERRED_REGISTER.register("jewel_disk", null);
    static public final RegistryObject<Item> KAIBAMAN_DISK = DEFERRED_REGISTER.register("kaibaman_disk", null);
    static public final RegistryObject<Item> CYBER_DESIGN_INTERFACE = DEFERRED_REGISTER.register("cyber_design_interface", null);
    static public final RegistryObject<DeckBoxItem> BLACK_DECK_BOX = DEFERRED_REGISTER.register("black_deck_box", null);
    static public final RegistryObject<DeckBoxItem> RED_DECK_BOX = DEFERRED_REGISTER.register("red_deck_box", null);
    static public final RegistryObject<DeckBoxItem> GREEN_DECK_BOX = DEFERRED_REGISTER.register("green_deck_box", null);
    static public final RegistryObject<DeckBoxItem> BROWN_DECK_BOX = DEFERRED_REGISTER.register("brown_deck_box", null);
    static public final RegistryObject<DeckBoxItem> BLUE_DECK_BOX = DEFERRED_REGISTER.register("blue_deck_box", null);
    static public final RegistryObject<DeckBoxItem> PURPLE_DECK_BOX = DEFERRED_REGISTER.register("purple_deck_box", null);
    static public final RegistryObject<DeckBoxItem> CYAN_DECK_BOX = DEFERRED_REGISTER.register("cyan_deck_box", null);
    static public final RegistryObject<DeckBoxItem> LIGHT_GRAY_DECK_BOX = DEFERRED_REGISTER.register("light_gray_deck_box", null);
    static public final RegistryObject<DeckBoxItem> GRAY_DECK_BOX = DEFERRED_REGISTER.register("gray_deck_box", null);
    static public final RegistryObject<DeckBoxItem> PINK_DECK_BOX = DEFERRED_REGISTER.register("pink_deck_box", null);
    static public final RegistryObject<DeckBoxItem> LIME_DECK_BOX = DEFERRED_REGISTER.register("lime_deck_box", null);
    static public final RegistryObject<DeckBoxItem> YELLOW_DECK_BOX = DEFERRED_REGISTER.register("yellow_deck_box", null);
    static public final RegistryObject<DeckBoxItem> LIGHT_BLUE_DECK_BOX = DEFERRED_REGISTER.register("light_blue_deck_box", null);
    static public final RegistryObject<DeckBoxItem> MAGENTA_DECK_BOX = DEFERRED_REGISTER.register("magenta_deck_box", null);
    static public final RegistryObject<DeckBoxItem> ORANGE_DECK_BOX = DEFERRED_REGISTER.register("orange_deck_box", null);
    static public final RegistryObject<DeckBoxItem> WHITE_DECK_BOX = DEFERRED_REGISTER.register("white_deck_box", null);
    static public final RegistryObject<DeckBoxItem> IRON_DECK_BOX = DEFERRED_REGISTER.register("iron_deck_box", null);
    static public final RegistryObject<DeckBoxItem> GOLD_DECK_BOX = DEFERRED_REGISTER.register("gold_deck_box", null);
    static public final RegistryObject<DeckBoxItem> DIAMOND_DECK_BOX = DEFERRED_REGISTER.register("diamond_deck_box", null);
    static public final RegistryObject<DeckBoxItem> EMERALD_DECK_BOX = DEFERRED_REGISTER.register("emerald_deck_box", null);
    static public final RegistryObject<DeckBoxItem> PATREON_DECK_BOX = DEFERRED_REGISTER.register("patreon_deck_box", null);
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BLANC_CARD);
            event.accept(CARD_BACK);
            event.accept(BLANC_SET);
            event.accept(CARD);
            event.accept(SET);
            event.accept(OPENED_SET);
            event.accept(CARD_BINDER);
            event.accept(DUEL_PLAYMAT);
            event.accept(DUEL_TABLE);
            event.accept(CARD_SUPPLY);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SIMPLE_BINDER_3);
            event.accept(SIMPLE_BINDER_9);
            event.accept(SIMPLE_BINDER_27);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(MILLENIUM_KEY);
            event.accept(MILLENIUM_NECKLACE);
            event.accept(MILLENIUM_PUZZLE);
            event.accept(MILLENIUM_RING);
            event.accept(MILLENIUM_ROD);
            event.accept(MILLENIUM_SCALE);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(DUEL_DISK);
            event.accept(CHAOS_DISK);
            event.accept(ACADEMIA_DISK);
            event.accept(ACADEMIA_DISK_RED);
            event.accept(ACADEMIA_DISK_BLUE);
            event.accept(ACADEMIA_DISK_YELLOW);
            event.accept(ROCK_SPIRIT_DISK);
            event.accept(TRUEMAN_DISK);
            event.accept(JEWEL_DISK);
            event.accept(KAIBAMAN_DISK);
            event.accept(CYBER_DESIGN_INTERFACE);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BLACK_DECK_BOX);
            event.accept(RED_DECK_BOX);
            event.accept(GREEN_DECK_BOX);
            event.accept(BROWN_DECK_BOX);
            event.accept(BLUE_DECK_BOX);
            event.accept(PURPLE_DECK_BOX);
            event.accept(CYAN_DECK_BOX);
            event.accept(LIGHT_GRAY_DECK_BOX);
            event.accept(GRAY_DECK_BOX);
            event.accept(PINK_DECK_BOX);
            event.accept(LIME_DECK_BOX);
            event.accept(YELLOW_DECK_BOX);
            event.accept(LIGHT_BLUE_DECK_BOX);
            event.accept(MAGENTA_DECK_BOX);
            event.accept(ORANGE_DECK_BOX);
            event.accept(WHITE_DECK_BOX);
            event.accept(IRON_DECK_BOX);
            event.accept(GOLD_DECK_BOX);
            event.accept(DIAMOND_DECK_BOX);
            event.accept(EMERALD_DECK_BOX);
            event.accept(PATREON_DECK_BOX);
            for(CardSleevesType sleeve : CardSleevesType.VALUES)
            {
                if(!sleeve.isCardBack())
                    {
                        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                            DEFERRED_REGISTER.register(((CardSleevesType) sleeve).getResourceName(), null);
                            event.accept(sleeve);
                        }
                    }
                }
            }
        }
    public static void register(IEventBus bus)
    {
        DEFERRED_REGISTER.register(bus);
    }
}