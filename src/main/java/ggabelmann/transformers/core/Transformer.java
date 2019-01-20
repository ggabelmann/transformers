package ggabelmann.transformers.core;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable.
 */
public class Transformer implements Comparable<Transformer> {
	
	public static final String PREDAKING = "Predaking";
	public static final String OPTIMUS_PRIME = "Optimus Prime";
	
	public enum Type {
		AUTOBOT("A"),
		DECEPTICON("D");
		
		private final String friendly;
		
		Type(final String friendly) {
			this.friendly = friendly;
		}
		
		public String getFriendly() {
			return friendly;
		}
	}
	
	private final Optional<Integer> id;
	private final String name;
	private final Type type;
	private final int strength;
	private final int intelligence;
	private final int speed;
	private final int endurance;
	private final int rank;
	private final int courage;
	private final int firepower;
	private final int skill;
	
	/**
	 * Construct a Transformer without an ID.
	 *
	 * @param name
	 * @param type
	 * @param strength
	 * @param intelligence
	 * @param speed
	 * @param endurance
	 * @param rank
	 * @param courage
	 * @param firepower
	 * @param skill
	 */
	public Transformer(
			final String name,
			final Type type,
			final int strength,
			final int intelligence,
			final int speed,
			final int endurance,
			final int rank,
			final int courage,
			final int firepower,
			final int skill) {
		this.id = Optional.empty();
		this.name = Objects.requireNonNull(name);
		this.type = Objects.requireNonNull(type);
		this.strength = validate(strength);
		this.intelligence = validate(intelligence);
		this.speed = validate(speed);
		this.endurance = validate(endurance);
		this.rank = validate(rank);
		this.courage = validate(courage);
		this.firepower = validate(firepower);
		this.skill = validate(skill);
	}
	
	/**
	 * Construct a Transformer with an ID.
	 *
	 * @param id
	 * @param transformer
	 */
	Transformer(final Optional<Integer> id, final Transformer transformer) {
			this.id = Objects.requireNonNull(id);
			Objects.requireNonNull(transformer);
			
			this.name = transformer.name;
			this.type = transformer.type;
			this.strength = transformer.strength;
			this.intelligence = transformer.intelligence;
			this.speed = transformer.speed;
			this.endurance = transformer.endurance;
			this.rank = transformer.rank;
			this.courage = transformer.courage;
			this.firepower = transformer.firepower;
			this.skill = transformer.skill;
	}

	private static int validate(final int rating) {
		if (rating < 1) {
			throw new IllegalArgumentException("Rating cannot be below 1.");
		}
		if (rating > 10) {
			throw new IllegalArgumentException("Rating cannot be above 10.");
		}
		return rating;
	}
	
	public Optional<Integer> getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getIntelligence() {
		return intelligence;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getEndurance() {
		return endurance;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getCourage() {
		return courage;
	}
	
	public int getFirepower() {
		return firepower;
	}
	
	public int getSkill() {
		return skill;
	}

	public int getOverallRating() {
		return strength + intelligence + speed + endurance + firepower;
	}
	
	/**
	 * Sorts by rank, from high to low.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(final Transformer o) {
		return -1 * Integer.compare(this.rank, o.rank);
	}
	
}
