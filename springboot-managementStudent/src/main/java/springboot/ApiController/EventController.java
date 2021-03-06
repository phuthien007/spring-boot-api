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
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.EventDTO;
import springboot.Model.Mapper.EventMapper;
import springboot.Model.MetaModel.EventMeta;
import springboot.Model.MetaModel.TeacherMeta;
import springboot.Service.EventService;

@RestController
@RequestMapping(path="/api/")
public class EventController {

	@Autowired
	private EventService eventSer;

	@Autowired
	private EventMapper EventConverter;

	private Map<String, Map<String, List<String>>> validateInput(String[] params) {
		Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
		try {
			inputTransform = UtilController.transformInput(params);
			for (String index : inputTransform.keySet()) {
				for (String field : inputTransform.get(index).keySet()) {
					if (!EventMeta.hasAttribute(field))
						throw new BadRequestException("input invalid, no find any field");
				}
			}
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		return inputTransform;
	}

	private static final Logger log = LogManager.getLogger(EventController.class);


	// l???y t???t c??? c??c b???n ghi
	@GetMapping("public/event")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllevents(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			// filter Params
			@RequestParam(name = "equal", required = false) String[] equalParams,
			@RequestParam(name = "greater_than", required = false) String[] greaterThanParams,
			@RequestParam(name = "less_than", required = false) String[] lessThanParams,
			@RequestParam(name = "like", required = false) String[] likeParams,
			// sorting
			@RequestParam(name = "sort", required = false, defaultValue = "id|asc") List<String> sorting

	) {
		Page<EventEntity> events = null;
		Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
		Map<OperationQuery, Map<String, Map<String, List<String>>>> keyword = new HashMap<>();

		if (equalParams != null) {
			inputTransform = validateInput(equalParams);
			keyword.put(OperationQuery.EQUALS, inputTransform);
		}
		if (greaterThanParams != null) {
			inputTransform = validateInput(greaterThanParams);
			keyword.put(OperationQuery.GREATER_THAN, inputTransform);
		}
		if (lessThanParams != null) {
			inputTransform = validateInput(lessThanParams);
			keyword.put(OperationQuery.LESS_THAN, inputTransform);
		}
		if (likeParams != null) {
			inputTransform = validateInput(likeParams);
			keyword.put(OperationQuery.LIKE, inputTransform);
		}
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


	
	// l???y b???n ghi theo id
	@GetMapping("public/event/{id}")
	public EventDTO geteventById(@PathVariable(value = "id") Long eventId) {
		return EventConverter.toDTO(eventSer.findById(eventId));
	}

	// th??m m???i b???n ghi
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

	// s???a b???n ghi
	@PutMapping("public/event")
	public EventDTO updateeventById(@RequestBody @Validated EventDTO event) {
		if (event.getId() == null)
			throw new BadRequestException("Value id is missing");
		return EventConverter.toDTO(eventSer.updateevent(EventConverter.toEntity(event)));
	}

	// x??a b???n ghi
	@DeleteMapping("event/{id}")
	public Map<String, Boolean> deleteeventById(@PathVariable(value = "id") Long eventId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", eventSer.deleteById(eventId));
		return response;
	}
	
}
