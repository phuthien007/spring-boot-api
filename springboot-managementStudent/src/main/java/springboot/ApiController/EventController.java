package springboot.ApiController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import springboot.Model.DTO.EventDTO;
import springboot.Model.Mapper.EventMapper;
import springboot.Service.EventService;

@RestController
@RequestMapping(path="/api/")
public class EventController {

	@Autowired
	private EventService eventSer;

	@Autowired
	private EventMapper EventConverter;

	private static final Logger log = LogManager.getLogger(TeacherController.class);


	// lấy tất cả các bản ghi
	@GetMapping("public/event")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllevents(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			// filter params
			@RequestParam(name = "name" ,required = false) String name,
			@RequestParam(name = "createDate" ,required = false) Date createDate,
			@RequestParam(name = "status" ,required = false) String status,
			@RequestParam(name = "happenDate" ,required = false) Date happenDate,
			@RequestParam(name="sort", required = false, defaultValue = "id|asc") List<String> sorting) {
		Page<EventEntity> events = null;
		Map<String, String> keyword = new HashMap<>();
		if(name != null) keyword.put("name", name);
		if(createDate != null) keyword.put("createDate", createDate.toString());
		if(status != null) keyword.put("status", status);
		if(happenDate != null) keyword.put("happenDate", happenDate.toString());
		if (!keyword.isEmpty()) {
//			System.out.println("thuc hien 1");
			events = eventSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
//			System.out.println("thuc hien 1");
		}
		else events =eventSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
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
			if (event.getName() == null || event.getClassId() == null || event.getStatus() == null)
				throw new BadRequestException("Value is missing");
			EventEntity t = EventConverter.toEntity(event);
			t.setId(null);
			return EventConverter.toDTO(eventSer.addevent(t));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN ADD A NEW EVENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

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
