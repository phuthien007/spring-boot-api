package springboot.ApiController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entity.EventEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.EventConverter;
import springboot.Model.DTO.EventDTO;
import springboot.Service.EventService;

@RestController
@RequestMapping(path="/api/")
public class EventController {

	@Autowired
	private EventService eventSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/event")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllevents(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "keyword" ,required = false) String keyword) {
		Page<EventEntity> events =eventSer.getAll(PageRequest.of(page, 20));
		if (keyword != null) {
//			System.out.println("thuc hien 1");
			events = eventSer.getAll(PageRequest.of(page, 20), keyword);
//			System.out.println("thuc hien 1");
		}
//			.stream().map(event -> EventConverter.toDTO(event)).collect(Collectors.toList());
//				.stream().map(event -> EventConverter.toDTO(event)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(events.getTotalElements()));
		headers.add("page", String.valueOf(events.getNumber()));
		headers.add("elementOfPages", String.valueOf(events.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(events.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",events.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
				events.toList().stream().map(event -> EventConverter.toDTO(event)).collect(Collectors.toList()) );
	}


	
	// lấy bản ghi theo id
	@GetMapping("public/event/{id}")
	public EventDTO geteventById(@PathVariable(value = "id") Long eventId) {
		return EventConverter.toDTO(eventSer.findById(eventId));
	}

	// thêm mới bản ghi
	@PostMapping("public/event")
	@ResponseStatus(value = HttpStatus.CREATED)
	public EventDTO addevent(@RequestBody @Validated EventDTO event) {
		try {
			if (event.getName() == null || event.getClasses().getId() == null || event.getStatus() == null)
				throw new BadRequestException("Value is missing");
			EventEntity t = EventConverter.toEntity(event);
			t.setId(null);
			return EventConverter.toDTO(eventSer.addevent(t));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Something went wrong!");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/event")
	public EventDTO updateeventById(@RequestBody @Validated EventDTO event) {
		if (event.getId() == null)
			throw new BadRequestException("Value id is missing");
		return EventConverter.toDTO(eventSer.updateevent(EventConverter.toEntity(event)));
	}

	// xóa bản ghi
	@DeleteMapping("event/{id}")
	public Map<String, Boolean> deleteeventById(@PathVariable(value = "id") Long eventId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", eventSer.deleteById(eventId));
		return response;
	}
	
}