package ggabelmann.transformers.core;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Service {
	
	int add(Transformer transformer);
	
	BattleResult battle(Set<Integer> ids) throws TotalDestructionException;
	
	Optional<Transformer> findTransformerById(int id);
	
	List<Transformer> getTransformers();
	
	/**
	 * Remove a transformer.
	 *
	 * @param id The transformer's id.
	 * @return true if the transformer existed and was removed, false otherwise.
	 */
	boolean remove(int id);
	
	void update(int id, Transformer transformer);
	
}
