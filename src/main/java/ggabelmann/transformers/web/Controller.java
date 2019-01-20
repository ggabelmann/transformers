package ggabelmann.transformers.web;

import ggabelmann.transformers.core.TransformerService;
import ggabelmann.transformers.core.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {
	
	private final TransformerService service;
	
	@Autowired
	public Controller(final TransformerService service) {
		this.service = service;
	}
	
	@PostMapping("/transformers")
	public int add(@RequestBody final TransformerDto dto) {
		return service.add(convertFromDto(dto));
	}
	
	@RequestMapping("/transformers")
	public List<TransformerDto> list() {
		return service.getTransformers().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	public void battle() {
	}
	
	@DeleteMapping("/transformers/{id}")
	public boolean remove(@PathVariable("id") final int id) {
		return service.remove(id);
	}
	
	@PutMapping("/transformers/{id}")
	public void update(@PathVariable("id") final int id, @RequestBody final TransformerDto dto) {
		final Transformer transformer = convertFromDto(dto);
		service.update(id, transformer);
	}
	
	private Transformer convertFromDto(final TransformerDto dto) {
		final Transformer.Type type;
		if (dto.getType().equals("A")) {
			type = Transformer.Type.AUTOBOT;
		}
		else if (dto.getType().equals("D")) {
			type = Transformer.Type.DECEPTICON;
		}
		else {
			throw new IllegalArgumentException("Unknown Transformer type.");
		}
		return new Transformer(
				dto.getName(),
				type,
				dto.getStrength(),
				dto.getIntelligence(),
				dto.getSpeed(),
				dto.getEndurance(),
				dto.getRank(),
				dto.getCourage(),
				dto.getFirepower(),
				dto.getSkill());
	}
	
	private TransformerDto convertToDto(final Transformer transformer) {
		final TransformerDto dto = new TransformerDto();
		dto.setId(transformer.getId().get());
		dto.setName(transformer.getName());
		dto.setType(transformer.getType().getFriendly());
		dto.setCourage(transformer.getCourage());
		dto.setEndurance(transformer.getEndurance());
		dto.setFirepower(transformer.getFirepower());
		dto.setIntelligence(transformer.getIntelligence());
		dto.setRank(transformer.getRank());
		dto.setSkill(transformer.getSkill());
		dto.setSpeed(transformer.getSpeed());
		dto.setStrength(transformer.getStrength());
		return dto;
	}
	
}
