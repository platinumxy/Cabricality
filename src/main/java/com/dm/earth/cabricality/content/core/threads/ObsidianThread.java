package com.dm.earth.cabricality.content.core.threads;

import static com.dm.earth.cabricality.ModEntry.CABF;
import static com.dm.earth.cabricality.ModEntry.CR;
import static com.dm.earth.cabricality.ModEntry.MC;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.RecipeLoadingEvents.AddRecipesCallback.RecipeHandler;

import com.dm.earth.cabricality.content.core.TechThread;
import com.dm.earth.cabricality.tweak.core.MechAndSmithCraft;
import com.dm.earth.cabricality.util.RecipeBuilderUtil;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;

import net.minecraft.util.Identifier;

public class ObsidianThread implements TechThread {

    @Override
    public void addRecipes(RecipeHandler handler) {
        handler.register(recipeId("sequenced_assembly", ""),
                id -> (new SequencedAssemblyRecipeBuilder(id)).require(CR.asItem("precision_mechanism"))
                        .transitionTo(CABF.asItem("incomplete_sturdy_mechanism"))
                        .addOutput(CABF.asItem("sturdy_mechanism"), 1.0F).loops(1)
                        .addStep(DeployerApplicationRecipe::new, r -> r.require(CR.asItem("sturdy_sheet")))
                        .addStep(DeployerApplicationRecipe::new, r -> r.require(CR.asItem("sturdy_sheet")))
                        .addStep(PressingRecipe::new, r -> r)
                        .build());

        handler.register(recipeId("crafting", "obsidian_machine"), id -> RecipeBuilderUtil.donutRecipe(id,
                CR.asItem("railway_casing"), CABF.asItem("sturdy_mechanism"), CABF.asItem("obsidian_machine"), 1));
    }

    @Override
    public void load() {
        MechAndSmithCraft.addEntry(entry(CR.id("track_station"), 1, null));
        MechAndSmithCraft.addEntry(entry(CR.id("track_signal"), 1, CR.id("electron_tube")));
        MechAndSmithCraft.addEntry(entry(CR.id("track_observer"), 1, MC.id("observer")));
        MechAndSmithCraft.addEntry(entry(CR.id("controls"), 1, MC.id("lever")));
    }

    @Override
    public String getLevel() {
        return "obsidian";
    }

    @Contract("_, _, _ -> new")
    private MechAndSmithCraft.@NotNull Entry entry(Identifier output, int count, @Nullable Identifier other) {
        return MechAndSmithCraft.entry(this.getLevel(), CABF.id("obsidian_machine"), output, count, other);
    }

}