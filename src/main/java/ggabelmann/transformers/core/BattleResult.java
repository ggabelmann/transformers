package ggabelmann.transformers.core;

import java.util.List;
import java.util.Objects;

/**
 * Holds the results of a battle between Transformers.
 */
public class BattleResult {
	
	private final int numBattles;
	private final Transformer.Type winningTeam;
	private final List<Transformer> winningTeamSurvivors;
	private final List<Transformer> losingTeamSurvivors;
	
	BattleResult(final int numBattles, final Transformer.Type winningTeam, final List<Transformer> winningTeamSurvivors, final List<Transformer> losingTeamSurvivors) {
		this.numBattles = numBattles;
		this.winningTeam = Objects.requireNonNull(winningTeam);
		this.winningTeamSurvivors = Objects.requireNonNull(winningTeamSurvivors);
		this.losingTeamSurvivors = Objects.requireNonNull(losingTeamSurvivors);
	}
	
	public int getNumBattles() {
		return numBattles;
	}
	
	public Transformer.Type getWinningTeam() {
		return winningTeam;
	}
	
	public List<Transformer> getWinningTeamSurvivors() {
		return winningTeamSurvivors;
	}
	
	public List<Transformer> getLosingTeamSurvivors() {
		return losingTeamSurvivors;
	}
	
}
