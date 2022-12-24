package com.dm.earth.cabricality.content.core.threads;

import static com.dm.earth.cabricality.ModEntry.AE2;
import static com.dm.earth.cabricality.ModEntry.CABF;
import static com.dm.earth.cabricality.ModEntry.CR;
import static com.dm.earth.cabricality.ModEntry.IV;
import static com.dm.earth.cabricality.ModEntry.KB;
import static com.dm.earth.cabricality.ModEntry.MC;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.RecipeLoadingEvents.AddRecipesCallback;
import org.quiltmc.qsl.recipe.api.builder.VanillaRecipeBuilders;

import com.dm.earth.cabricality.content.core.TechThread;
import com.dm.earth.cabricality.tweak.core.MechAndSmithCraft;

import net.minecraft.util.Identifier;

public class EnderiumThread implements TechThread {

	@Override
	public void load() {
		MechAndSmithCraft.addEntry(entry(KB.id("entangled_chest"), 1, MC.id("chest")));
		MechAndSmithCraft.addEntry(entry(KB.id("entangled_tank"), 1, CR.id("fluid_tank")));
		MechAndSmithCraft.addEntry(entry(IV.id("tier_upgrade_mk4"), 1, MC.id("redstone")));
		MechAndSmithCraft.addEntry(entry(AE2.id("quantum_ring"), 1, AE2.id("energy_cell")));
		MechAndSmithCraft.addEntry(entry(AE2.id("quantum_link"), 1, AE2.id("fluix_pearl")));
	}

	@Override
	public void addRecipes(AddRecipesCallback.RecipeHandler handler) {
		handler.register(recipeId("crafting", "enderium_machine"),
				id -> VanillaRecipeBuilders.shapedRecipe("SSS", "SCS", "SSS")
						.ingredient('S', CABF.asIngredient("abstruse_mechanism"))
						.ingredient('C', CABF.asIngredient("enderium_casing")).output(CABF.asStack("enderium_machine"))
						.build(id, ""));
	}

	@Override
	public String getLevel() {
		return "enderium";
	}

	@Contract("_, _, _ -> new")
	private MechAndSmithCraft.@NotNull Entry entry(Identifier output, int count, @Nullable Identifier other) {
		return MechAndSmithCraft.entry(this.getLevel(), CABF.id("enderium_machine"), output, count, other);
	}

}