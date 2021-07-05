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

import springboot.Entity.PlanEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.PlanConverter;
import springboot.Model.DTO.PlanDTO;
import springboot.Service.PlanService;

@RestController
@RequestMapping(path="/api/")
public class PlanController {

	@Autowired
	private PlanService planSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/plan")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllplans(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Page<PlanEntity> plans =planSer.getAll(PageRequest.of(page, 20));
		if (keyword != null)
			plans = planSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList());
//				.stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(plans.getTotalElements()));
		headers.add("page", String.valueOf(plans.getNumber()));
		headers.add("elementOfPages", String.valueOf(plans.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(plans.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",plans.getTotalElements()  );
		return ResponseEntity.ok().headers(headers).body(
				plans.toList().stream().map(plan -> PlanConverter.toDTO(plan)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/plan/{id}")
	public PlanDTO getplanById(@PathVariable(value = "id") Long planId) {
		return PlanConverter.toDTO(planSer.findById(planId));
	}

	// thêm mới bản ghi
	@PostMapping("public/plan")
	@ResponseStatus(value = HttpStatus.CREATED)
	public PlanDTO addplan(@RequestBody @Validated PlanDTO plan) {
		try {
			if (plan.getName() == null || plan.getCourse().getId() == null)
				throw new BadRequestException("Value is missing");
			PlanEntity t = PlanConverter.toEntity(plan);
			t.setId(null);
			return PlanConverter.toDTO(planSer.addplan(t));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Something went wrong");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/plan")
	public PlanDTO updateplanById(@RequestBody @Validated PlanDTO plan) {
		try {
			if (plan.getId() == null)
				throw new BadRequestException("Value id is missing");
			return PlanConverter.toDTO(planSer.updateplan(PlanConverter.toEntity(plan)));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
		
	}

	// xóa bản ghi
	@DeleteMapping("plan/{id}")
	public Map<String, Boolean> deleteplanById(@PathVariable(value = "id") Long planId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", planSer.deleteById(planId));
		return response;
	}
	
}
