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

import springboot.Entity.PlanEntity;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.PlanDTO;
import springboot.Model.Mapper.PlanMapper;
import springboot.Model.MetaModel.PlanMeta;
import springboot.Model.MetaModel.TeacherMeta;
import springboot.Service.PlanService;

@RestController
@RequestMapping(path="/api/")
public class PlanController {

	@Autowired
	private PlanService planSer;

	@Autowired
	private PlanMapper PlanConverter;

	private Map<String, Map<String, List<String>>> validateInput(String[] params) {
		Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
		try {
			inputTransform = UtilController.transformInput(params);
			for (String index : inputTransform.keySet()) {
				for (String field : inputTransform.get(index).keySet()) {
					if (!PlanMeta.hasAttribute(field))
						throw new BadRequestException("input invalid, no find any field");
				}
			}
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		return inputTransform;
	}

	private static final Logger log = LogManager.getLogger(PlanController.class);

	// l???y t???t c??? c??c b???n ghi

	@GetMapping("public/plan/test")
	public List<PlanDTO> getAllPlans(){
		return planSer.getAll().stream().map( plan -> PlanConverter.toDTO(plan) ).collect(Collectors.toList());
	}

	@GetMapping("public/plan")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllplans(
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
		Page<PlanEntity> plans = null;
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
		if (!keyword.isEmpty())
			plans = planSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
//
//			.stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList());
//				.stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList());
		else {
			plans = planSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
//
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(plans.getTotalElements()));
		headers.add("page", String.valueOf(plans.getNumber()));
		headers.add("elementOfPages", String.valueOf(plans.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(plans.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",plans.getTotalElements()  );
		return ResponseEntity.ok().headers(headers).body(
				plans.toList().stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList()) );
	}

	// l???y b???n ghi theo id
	@GetMapping("public/plan/{id}")
	public PlanDTO getplanById(@PathVariable(value = "id") Long planId) {
		return PlanConverter.toDTO(planSer.findById(planId));
	}

	// th??m m???i b???n ghi
	@PostMapping("public/plan")
	@ResponseStatus(value = HttpStatus.CREATED)
	public PlanDTO addplan(@RequestBody @Validated PlanDTO plan) {
		try {
			if (plan.getName() == null || plan.getCourseId() == null)
				throw new BadRequestException("Value is missing");
			PlanEntity t = PlanConverter.toEntity(plan);
			t.setId(null);
			return PlanConverter.toDTO(planSer.addplan(t));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN ADD A NEW PLAN] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException("Something went wrong");
		}
		
	}

	// s???a b???n ghi
	@PutMapping("public/plan")
	public PlanDTO updateplanById(@RequestBody @Validated PlanDTO plan) {
		try {
			if (plan.getId() == null)
				throw new BadRequestException("Value id is missing");
			return PlanConverter.toDTO(planSer.updateplan(PlanConverter.toEntity(plan)));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN UPDATE A PLAN] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException(e.getMessage());
		}
		
	}

	// x??a b???n ghi
	@DeleteMapping("plan/{id}")
	public Map<String, Boolean> deleteplanById(@PathVariable(value = "id") Long planId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", planSer.deleteById(planId));
		return response;
	}
	
}
