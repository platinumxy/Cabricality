package com.dm.earth.cabricality.content.core.threads;

import static com.dm.earth.cabricality.ModEntry.AE2;
import static com.dm.earth.cabricality.ModEntry.CABF;
import static com.dm.earth.cabricality.ModEntry.CR;
import static com.dm.earth.cabricality.ModEntry.IV;
import static com.dm.earth.cabricality.ModEntry.MC;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.RecipeLoadingEvents;
import org.quiltmc.qsl.recipe.api.builder.VanillaRecipeBuilders;

import com.dm.earth.cabricality.content.core.TechThread;
import com.dm.earth.cabricality.resource.data.core.FreePRP;
import com.dm.earth.cabricality.tweak.RecipeTweaks;
import com.dm.earth.cabricality.tweak.core.MechAndSmithCraft;
import com.dm.earth.cabricality.util.RecipeBuilderUtil;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class Brass implements TechThread {
	@Override
	public void addRecipes(RecipeLoadingEvents.AddRecipesCallback.RecipeHandler handler) {
		handler.register(recipeId("milling", "sky_stone_block"),
				id -> new MillingRecipe(new FreePRP(id).setIngredient(Ingredient.ofItems(AE2.asItem("sky_stone_block")))
						.setResult(new ProcessingOutput(AE2.asItem("sky_stone_block").getDefaultStack(), 1),
								new ProcessingOutput(AE2.asItem("sky_dust").getDefaultStack(), 1))
						.setProcessingTime(350)));

		Item redstone = Items.REDSTONE;
		handler.register(recipeId("crafting", "rose_quartz"),
				id -> VanillaRecipeBuilders
						.shapelessRecipe(CR.asItem("rose_quartz").getDefaultStack()).ingredient(Items.QUARTZ,
								AE2.asItem("certus_quartz_crystal"), AE2.asItem("charged_certus_quartz_crystal"))
						.ingredient(redstone).ingredient(redstone).ingredient(redstone)
						.build(id, ""));

		handler.register(recipeId("milling", "certus_quartz"),
				id -> new MillingRecipe(new FreePRP(id)
						.setIngredient(Ingredient.ofItems(AE2.asItem("certus_quartz_crystal"),
								AE2.asItem("charged_certus_quartz_crystal")))
						.setResult(new ProcessingOutput(AE2.asItem("certus_quartz_dust").getDefaultStack(), 1))
						.setProcessingTime(200)));

		handler.register(recipeId("milling", "fluix_quartz"),
				id -> new MillingRecipe(new FreePRP(id)
						.setIngredient(Ingredient.ofItems(AE2.asItem("fluix_crystal")))
						.setResult(new ProcessingOutput(AE2.asItem("fluix_dust").getDefaultStack(), 1))
						.setProcessingTime(200)));

		handler.register(recipeId("mechanical_crafting", "certus_quartz_seed"),
				id -> RecipeBuilderUtil.mechanicalFromShaped(
						VanillaRecipeBuilders.shapedRecipe("x").ingredient('x', AE2.asItem("certus_quartz_crystal"))
								.output(AE2.asItem("certus_crystal_seed").getDefaultStack()).build(id, ""),
						false));

		handler.register(recipeId("mechanical_crafting", "fluix_crystal_seed"),
				id -> RecipeBuilderUtil.mechanicalFromShaped(
						VanillaRecipeBuilders.shapedRecipe("x").ingredient('x', AE2.asItem("fluix_crystal"))
								.output(AE2.asItem("fluix_crystal_seed").getDefaultStack()).build(id, ""),
						false));

		handler.register(recipeId("crafting", "brass_machine"), id -> RecipeBuilderUtil.donutRecipe(id,
				CR.asItem("brass_casing"), CR.asItem("precision_mechanism"), CABF.asItem("brass_machine"), 1));

		handler.register(recipeId("sequenced_assembly", "precision_mechanism"),
				id -> new SequencedAssemblyRecipeBuilder(id).require(CABF.asItem("kinetic_mechanism"))
						.transitionTo(CR.asItem("incomplete_precision_mechanism"))
						.addOutput(CR.asItem("precision_mechanism"), 1)
						.addStep(DeployerApplicationRecipe::new, r -> r.require(CR.id("electron_tube")))
						.addStep(DeployerApplicationRecipe::new, r -> r.require(CR.id("electron_tube")))
						.addStep(DeployerApplicationRecipe::new,
								r -> r.require(IV.asItem("screwdriver")).toolNotConsumed())
						.build());
	}

	@Override
	public void removeRecipes(RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler) {
		handler.removeIf(p -> RecipeTweaks.notCabf(p) && p.getOutput().isOf(AE2.asItem("sky_dust")));
		handler.remove(CR.id("crafting/materials/electron_tube"));
		handler.remove(CR.id("crafting/materials/rose_quartz"));
		handler.remove(CR.id("sequenced_assembly/precision_mechanism"));
	}

	@Override
	public void load() {
		MechAndSmithCraft.addEntry(entry(CR.id("mechanical_crafter"), 3, MC.id("crafting_table")));
		MechAndSmithCraft.addEntry(entry(CR.id("sequenced_gearshift"), 2, null));
		MechAndSmithCraft.addEntry(entry(CR.id("rotation_speed_controller"), 1, null));
		MechAndSmithCraft.addEntry(entry(CR.id("mechanical_arm"), 1, null));
		MechAndSmithCraft.addEntry(entry(CR.id("stockpile_switch"), 2, null));
		MechAndSmithCraft.addEntry(entry(CR.id("content_observer"), 2, null));
		MechAndSmithCraft.addEntry(entry(IV.id("solid_infuser_mk1"), 1, MC.id("dropper")));
		MechAndSmithCraft.addEntry(entry(IV.id("biomass_generator_mk3"), 1, IV.id("heat_coil")));
		MechAndSmithCraft.addEntry(entry(CR.id("brass_funnel"), 4, null));
		MechAndSmithCraft.addEntry(entry(CR.id("brass_tunnel"), 4, null));
	}

	@Override
	public String getLevel() {
		return "brass";
	}

	@Contract("_, _, _ -> new")
	private @NotNull MechAndSmithCraft.Entry entry(Identifier output, int count, @Nullable Identifier other) {
		return MechAndSmithCraft.entry(this.getLevel(), CABF.id("brass_machine"), output, count, other);
	}
}