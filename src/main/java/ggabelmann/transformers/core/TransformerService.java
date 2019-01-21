package ggabelmann.transformers.core;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An interface for services that can manage Transformers and fight battles with them.
 */
public interface TransformerService {
	
	/**
	 * Add the given transformer.
	 *
	 * @param transformer This ID, if it has one, will be ignored.
	 * @return The ID of the newly added transformer.
	 */
	int add(Transformer transformer);
	
	/**
	 * Fight a battle between the given transformers.
	 *
	 * @param ids The set of IDs.
	 * @return The result of the battle.
	 * @throws TotalDestructionException
	 */
	BattleResult battle(Set<Integer> ids) throws TotalDestructionException;
	
	/**
	 * Try to find a transformer with the given ID.
	 *
	 * @param id The ID.
	 * @return An Optional that may have the transformer.
	 */
	Optional<Transformer> findTransformerById(int id);
	
	/**
	 * Gets all the transformers.
	 *
	 * @return The list.
	 */
	List<Transformer> getTransformers();
	
	/**
	 * Remove a transformer.
	 *
	 * @param id The transformer's id.
	 * @return true if the transformer existed and was removed, false otherwise.
	 */
	boolean remove(int id);
	
	/**
	 * Update the values of a transformer.
	 *
	 * @param id The transformer to update.
	 * @param transformer The values to use.
	 */
	void update(int id, Transformer transformer);
	
}
