package ggabelmann.transformers.web;

import java.util.List;
import java.util.Objects;

/**
 * A DTO for the results of a battle.
 * Immutable.
 */
public class BattleResultDto {
	
	private final int numBattles;
	private final String winningTeam;
	private final List<String> winningTeamSurvivors;
	private final List<String> losingTeamSurvivors;
	
	BattleResultDto(final int numBattles, final String winningTeam, final List<String> winningTeamSurvivors, final List<String> losingTeamSurvivors) {
		this.numBattles = numBattles;
		this.winningTeam = Objects.requireNonNull(winningTeam);
		this.winningTeamSurvivors = Objects.requireNonNull(winningTeamSurvivors);
		this.losingTeamSurvivors = Objects.requireNonNull(losingTeamSurvivors);
	}
	
	public int getNumBattles() {
		return numBattles;
	}
	
	public String getWinningTeam() {
		return winningTeam;
	}
	
	public List<String> getWinningTeamSurvivors() {
		return winningTeamSurvivors;
	}
	
	public List<String> getLosingTeamSurvivors() {
		return losingTeamSurvivors;
	}
	
}
