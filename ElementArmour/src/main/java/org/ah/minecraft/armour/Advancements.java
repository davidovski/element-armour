package org.ah.minecraft.armour;

import org.bukkit.NamespacedKey;

import com.google.gson.JsonObject;

import io.chazza.advancementapi.AdvancementAPI;
import io.chazza.advancementapi.Condition;
import io.chazza.advancementapi.FrameType;
import io.chazza.advancementapi.Trigger;
import io.chazza.advancementapi.Trigger.TriggerBuilder;

public class Advancements {
    private Plugin plugin;

    public Advancements(Plugin p) {
        this.plugin = p;

        TriggerBuilder t = Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "cant").condition(Condition.builder("do", new JsonObject()));
        AdvancementAPI ignore = makeAdvancement("Nothing...", "...", "minecraft:air", Trigger.builder(Trigger.TriggerType.TICK, "eat"), FrameType.GOAL, null);

        AdvancementAPI parent = AdvancementAPI.builder(new NamespacedKey(plugin, "elementArmour/start"))
                .title("Element Armour")
                .description("Wow")
                .icon("minecraft:leather_helmet")
                .trigger(
                        Trigger.builder(
                                Trigger.TriggerType.TICK, "start"))
                .hidden(false)
                .toast(false)
                .background("minecraft:textures/gui/advancements/backgrounds/stone.png")
                .frame(FrameType.TASK)
                .parent(ignore.getId().toString())
                .build();

        parent.add();
        AdvancementAPI second = makeAdvancement("Eat Food", "Simple", "minecraft:apple", Trigger.builder(Trigger.TriggerType.CONSUME_ITEM, "eat"), FrameType.GOAL, parent);
        AdvancementAPI third = makeAdvancement("must rewawrd", "k", "minecraft:bedrock", t,
                FrameType.GOAL, second);

    }

    public AdvancementAPI makeAdvancement(String title, String description, String icon, TriggerBuilder trigger, FrameType type, AdvancementAPI parent) {
        String key = title.replace(" ", "_").toLowerCase();
        AdvancementAPI.AdvancementAPIBuilder adb = null;
        if (parent != null) {
            adb = AdvancementAPI.builder(new NamespacedKey(this.plugin, "elementArmour/g")).title(title).description(description).icon(icon).trigger(trigger)

                    .hidden(false).toast(true).background("minecraft:textures/blocks/planks_jungle.png").frame(type).parent(parent.getId().toString());
        } else {
            adb = AdvancementAPI.builder(new NamespacedKey(this.plugin, "elementArmour/g")).title(title).description(description).icon(icon).trigger(trigger)

                    .hidden(false).toast(true).background("minecraft:textures/blocks/planks_jungle.png").frame(type);
        }

        AdvancementAPI ad = adb.build();

        ad.add();
        return ad;

    }
}
