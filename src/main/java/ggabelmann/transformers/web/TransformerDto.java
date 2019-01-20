package ggabelmann.transformers.web;

/**
 * A DTO for a Transformer.
 * No business logic whatsoever.
 */
public class TransformerDto {
	
	private Integer id;
	private String name;
	private String type;
	private int strength;
	private int intelligence;
	private int speed;
	private int endurance;
	private int rank;
	private int courage;
	private int firepower;
	private int skill;
	
	public TransformerDto() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
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
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setCourage(int courage) {
		this.courage = courage;
	}
	
	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}
	
	public void setSkill(int skill) {
		this.skill = skill;
	}
	
}
