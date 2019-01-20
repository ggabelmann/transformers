package ggabelmann.transformers.core;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of Service.
 * All methods are synchronized to simulate an ACID datastore.
 */
@Service
public class TransformerServiceImpl implements TransformerService {
	
	private int nextId;
	private final List<Transformer> transformers;
	
	public TransformerServiceImpl() {
		this.nextId = 0;
		this.transformers = new ArrayList<>();
	}
	
	@Override
	public synchronized int add(final Transformer transformer) {
		Objects.requireNonNull(transformer);
		if (transformer.getId().isPresent()) {
			throw new IllegalArgumentException("The transformer must not have an ID.");
		}
		
		final int id = nextId;
		nextId++;
		transformers.add(new Transformer(Optional.of(id), transformer));
		return id;
	}
	
	@Override
	public synchronized BattleResult battle(final Set<Integer> ids) throws TotalDestructionException {
		final List<Transformer> autobots = new ArrayList<>();
		final List<Transformer> decepticons = new ArrayList<>();
		
		for (final int id : ids) {
			if (transformers.get(id).getType() == Transformer.Type.AUTOBOT) {
				autobots.add(transformers.get(id));
			}
			else {
				decepticons.add(transformers.get(id));
			}
		}

		Collections.sort(autobots);
		Collections.sort(decepticons);
		
		final List<Transformer> destroyedAutobots = new ArrayList<>();
		final List<Transformer> destroyedDecepticons = new ArrayList<>();
		int numBattles = 0;
		for (; numBattles < Math.min(autobots.size(), decepticons.size()); numBattles++) {
			final List<Transformer> losers = losers(autobots.get(numBattles), decepticons.get(numBattles));
			for (final Transformer loser : losers) {
				if (loser.getType() == Transformer.Type.AUTOBOT) {
					destroyedAutobots.add(loser);
				}
				else {
					destroyedDecepticons.add(loser);
				}
			}
		}
		
		for (final Transformer destroyed : destroyedAutobots) {
			remove(destroyed.getId().get());
			for (final ListIterator<Transformer> li = autobots.listIterator(); li.hasNext(); ) {
				if (li.next().getId().get() == destroyed.getId().get()) {
					li.remove();
				}
			}
		}
		for (final Transformer destroyed : destroyedDecepticons) {
			remove(destroyed.getId().get());
			for (final ListIterator<Transformer> li = decepticons.listIterator(); li.hasNext(); ) {
				if (li.next().getId().get() == destroyed.getId().get()) {
					li.remove();
				}
			}
		}
		
		if (destroyedAutobots.size() <= destroyedDecepticons.size()) {
			return new BattleResult(numBattles, Transformer.Type.AUTOBOT, autobots, decepticons);
		}
		else {
			return new BattleResult(numBattles, Transformer.Type.DECEPTICON, decepticons, autobots);
		}
	}
	
	private List<Transformer> losers(final Transformer autobot, final Transformer decepticon) throws TotalDestructionException {
		if (autobot.getName().equals(Transformer.OPTIMUS_PRIME) && decepticon.getName().equals(Transformer.PREDAKING)) {
			throw new TotalDestructionException();
		}
		
		if (autobot.getName().equals(Transformer.OPTIMUS_PRIME)) {
			return Collections.singletonList(decepticon);
		}
		
		if (decepticon.getName().equals(Transformer.PREDAKING)) {
			return Collections.singletonList(autobot);
		}
		
		if (autobot.getCourage() - decepticon.getCourage() <= -4 && autobot.getStrength() - decepticon.getStrength() <= -3) {
			return Collections.singletonList(autobot);
		}
		else if (decepticon.getCourage() - autobot.getCourage() <= -4 && decepticon.getStrength() - autobot.getStrength() <= -3) {
			return Collections.singletonList(decepticon);
		}
		
		if (autobot.getSkill() - decepticon.getSkill() <= -3) {
			return Collections.singletonList(autobot);
		}
		else if (decepticon.getSkill() - autobot.getSkill() <= -3) {
			return Collections.singletonList(decepticon);
		}
		
		if (autobot.getOverallRating() == decepticon.getOverallRating()) {
			return Arrays.asList(autobot, decepticon);
		}
		else if (autobot.getOverallRating() < decepticon.getOverallRating()) {
			return Collections.singletonList(autobot);
		}
		else {
			return Collections.singletonList(decepticon);
		}
	}
	
	@Override
	public Optional<Transformer> findTransformerById(final int id) {
		for (final ListIterator<Transformer> li = transformers.listIterator(); li.hasNext(); ) {
			final Transformer potential = li.next();
			if (potential.getId().get().equals(id)) {
				return Optional.of(potential);
			}
		}
		
		return Optional.empty();
	}
	
	@Override
	public synchronized List<Transformer> getTransformers() {
		return new ArrayList<>(transformers);
	}
	
	@Override
	public synchronized boolean remove(final int id) {
		for (final ListIterator<Transformer> li = transformers.listIterator(); li.hasNext(); ) {
			final Transformer potential = li.next();
			if (potential.getId().get().equals(id)) {
				li.remove();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public synchronized void update(final int id, final Transformer transformer) {
		Objects.requireNonNull(transformer);
		if (transformer.getId().isPresent()) {
			throw new IllegalArgumentException("The transformer must not have an ID.");
		}
		
		if (remove(id)) {
			transformers.add(new Transformer(Optional.of(id), transformer));
		}
		else {
			throw new IllegalArgumentException("The transformer was not found.");
		}
	}
	
}
