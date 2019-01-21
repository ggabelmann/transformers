package ggabelmann.transformers;

import ggabelmann.transformers.core.BattleResult;
import ggabelmann.transformers.core.TransformerService;
import ggabelmann.transformers.core.TransformerServiceImpl;
import ggabelmann.transformers.core.TotalDestructionException;
import ggabelmann.transformers.core.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransformerServiceImplTest {
	
	private final Transformer auto1 = new Transformer("Auto 1", Transformer.Type.AUTOBOT, 1, 2, 3, 4, 5, 6, 7, 8);
	private final Transformer autoLowSkill = new Transformer("Auto Low Skill", Transformer.Type.AUTOBOT, 1, 2, 3, 4, 5, 6, 7, 5);
	private final Transformer autoHighCourageAndStrength = new Transformer("Auto High Courage and Strength", Transformer.Type.AUTOBOT, 4, 1, 1, 1, 1, 5, 1, 1);
	private final Transformer bluestreak = new Transformer("Bluestreak", Transformer.Type.AUTOBOT, 6, 6, 7, 9, 5, 2, 9, 7);
	private final Transformer hubcap = new Transformer("Hubcap", Transformer.Type.AUTOBOT, 4, 4, 4, 4, 4, 4, 4, 4);
	private final Transformer optimus = new Transformer(Transformer.OPTIMUS_PRIME, Transformer.Type.AUTOBOT, 1, 2, 3, 4, 5, 6, 7, 8);
	
	private final Transformer decep1 = new Transformer("Decep 1", Transformer.Type.DECEPTICON, 1, 2, 3, 4, 5, 6, 7, 8);
	private final Transformer decepLow = new Transformer("Decep Low Rating", Transformer.Type.DECEPTICON, 1, 2, 2, 2, 2, 1, 2, 2);
	private final Transformer predaking = new Transformer(Transformer.PREDAKING, Transformer.Type.DECEPTICON, 1, 2, 3, 4, 5, 6, 7, 8);
	private final Transformer soundwave = new Transformer("Soundwave", Transformer.Type.DECEPTICON, 8, 9, 2, 6, 7, 5, 6, 10);
	
	@Test
	public void testAdd() {
		final TransformerService service = new TransformerServiceImpl();
		service.add(auto1);
		service.add(decep1);
		
		assertEquals(2, service.getTransformers().size());
		assertEquals(Optional.of(0), service.getTransformers().get(0).getId());
		assertEquals(Optional.of(1), service.getTransformers().get(1).getId());
	}
	
	@Test
	public void testRemoveNonExistent() {
		final TransformerService service = new TransformerServiceImpl();
		
		assertFalse(service.remove(123));
	}
	
	@Test
	public void testRemoveExistent() {
		final TransformerService service = new TransformerServiceImpl();
		service.add(auto1);
		
		assertTrue(service.remove(0));
		assertEquals(0, service.getTransformers().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateWithId() {
		final TransformerService service = new TransformerServiceImpl();
		final int id = service.add(auto1);
		service.update(id, service.findTransformerById(id).get());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNonExistent() {
		final TransformerService service = new TransformerServiceImpl();
		service.update(0, auto1);
	}
	
	@Test
	public void testUpdateExistent() {
		final TransformerService service = new TransformerServiceImpl();
		final int id = service.add(auto1);
		service.update(id, decep1);
		
		assertEquals(1, service.getTransformers().size());
		assertEquals(Transformer.Type.DECEPTICON, service.findTransformerById(0).get().getType());
	}
	
	@Test(expected = TotalDestructionException.class)
	public void testTotalDestruction() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		service.add(optimus);
		service.add(predaking);
		
		service.battle(new HashSet<>(Arrays.asList(0, 1)));
	}
	
	@Test
	public void testOptimusWins() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId = service.add(optimus);
		final int decepId = service.add(decep1);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId, decepId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.AUTOBOT, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(decepId).isPresent());
	}
	
	@Test
	public void testPredakingWins() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int decepId = service.add(predaking);
		final int autoId = service.add(auto1);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(decepId, autoId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.DECEPTICON, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(autoId).isPresent());
	}
	
	@Test
	public void testTwoAutobots() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId1 = service.add(auto1);
		final int autoId2 = service.add(autoLowSkill);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId1, autoId2)));
		assertEquals(0, battleResult.getNumBattles());
		assertEquals(Transformer.Type.AUTOBOT, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertTrue(service.findTransformerById(autoId1).isPresent());
		assertTrue(service.findTransformerById(autoId2).isPresent());
	}
	
	/**
	 * If both bots have the same rating then both are destroyed.
	 * If both sides lose the same number of bots then consider the Autobots as the winners, since they're the good guys.
	 */
	@Test
	public void testBothHaveEqualRating() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId = service.add(auto1);
		final int decepId = service.add(decep1);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId, decepId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.AUTOBOT, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(autoId).isPresent());
		assertFalse(service.findTransformerById(decepId).isPresent());
	}
	
	@Test
	public void testHighRatingWins() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId = service.add(auto1);
		final int decepId = service.add(decepLow);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId, decepId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.AUTOBOT, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(decepId).isPresent());
	}
	
	@Test
	public void testHighCourageAndStrengthWins() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId = service.add(autoHighCourageAndStrength);
		final int decepId = service.add(decepLow);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId, decepId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.AUTOBOT, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(decepId).isPresent());
	}
	
	@Test
	public void testHighSkillWins() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int autoId = service.add(autoLowSkill);
		final int decepId = service.add(decep1);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(autoId, decepId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.DECEPTICON, battleResult.getWinningTeam());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
		assertFalse(service.findTransformerById(autoId).isPresent());
	}
	
	/**
	 Battle:
	Soundwave
	Bluestreak
	Hubcap
	
	The output should be:
	1 battle
	Winning team (Decepticons): Soundwave
	Survivors from the losing team (Autobots): Hubcap
	 */
	@Test
	public void testSampleBattle() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int blueId = service.add(bluestreak);
		final int hubId = service.add(hubcap);
		final int soundId = service.add(soundwave);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(blueId, hubId, soundId)));
		assertEquals(1, battleResult.getNumBattles());
		assertEquals(Transformer.Type.DECEPTICON, battleResult.getWinningTeam());
		assertEquals(Optional.of(soundId), battleResult.getWinningTeamSurvivors().get(0).getId());
		assertEquals(Optional.of(hubId), battleResult.getLosingTeamSurvivors().get(0).getId());
		assertTrue(service.findTransformerById(hubId).isPresent());
		assertFalse(service.findTransformerById(blueId).isPresent());
	}
	
	@Test
	public void testTwoBattles() throws TotalDestructionException {
		final TransformerService service = new TransformerServiceImpl();
		final int blueId = service.add(bluestreak);
		final int hubId = service.add(hubcap);
		final int decepId = service.add(decep1);
		final int soundId = service.add(soundwave);
		
		final BattleResult battleResult = service.battle(new HashSet<>(Arrays.asList(blueId, hubId, decepId, soundId)));
		assertEquals(2, battleResult.getNumBattles());
		assertEquals(Transformer.Type.DECEPTICON, battleResult.getWinningTeam());
		assertEquals(2, battleResult.getWinningTeamSurvivors().size());
		assertEquals(0, battleResult.getLosingTeamSurvivors().size());
	}
	
}

