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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springboot.Entity.ClassEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.DTO.ClassDTO;
import springboot.Model.Mapper.ClassMapper;
import springboot.Service.ClassService;

@RestController
@RequestMapping(path = "/api/")
public class ClassController {

	@Autowired
	private ClassService clSer;

	@Autowired
	private ClassMapper ClassConverter;

	private static final Logger log = LogManager.getLogger(TeacherController.class);

	// lấy tất cả các bản ghi
	@GetMapping("public/class")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllClass(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			// filter Params
			@RequestParam(name = "equalName" ,required = false) String equalName,
			@RequestParam(name = "likeName" ,required = false) String likeName,
			@RequestParam(name = "notEqualName" ,required = false) String notEqualName,

			@RequestParam(name = "equalStartDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSX") Date equalStartDate,
			@RequestParam(name = "greaterThanStartDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date greaterThanStartDate,
			@RequestParam(name = "lessThanStartDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lessThanStartDate,

			@RequestParam(name = "equalEndDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSX") Date equalEndDate,
			@RequestParam(name = "greaterThanEndDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date greaterThanEndDate,
			@RequestParam(name = "lessThanEndDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lessThanEndDate,

			// sorting
			@RequestParam(name = "sort", required = false, defaultValue = "id|asc")List<String> sorting

	) {

		System.out.println("equal name " + equalName);

		Map<String, Object> greaterThan = new HashMap<>();
		if(greaterThanStartDate != null) greaterThan.put("startDate", greaterThanStartDate);
		if(greaterThanEndDate != null) greaterThan.put("endDate", greaterThanEndDate);

		Map<String, Object> lessThan = new HashMap<>();
		if(lessThanStartDate != null) lessThan.put("startDate", lessThanStartDate);
		if(lessThanEndDate != null) lessThan.put("endDate", lessThanEndDate);

		Map<String, Object> equal = new HashMap<>();
		if(equalStartDate != null) equal.put("startDate", equalStartDate);
		if(equalEndDate != null) equal.put("endDate", equalEndDate);
		if(equalName != null) equal.put("name", equalName);

		Map<String, Object> notEqual = new HashMap<>();
		if(notEqualName != null) notEqual.put("name", notEqualName);

		Map<String, Object> like = new HashMap<>();
		if(likeName != null) like.put("name", likeName);

		Map<String, Map<String, Object>> keyword = new HashMap<>();
		if(!equal.isEmpty()) keyword.put("EQUALS", equal);
		if(!notEqual.isEmpty()) keyword.put("NOT_EQUALS", notEqual);
		if(!greaterThan.isEmpty()) keyword.put("GREATER_THAN", greaterThan);
		if(!lessThan.isEmpty()) keyword.put("LESS_THAN", lessThan);
		if(!like.isEmpty()) keyword.put("LIKE", like);

//		if(startDate != null) keyword.put("startDate", startDate.toString());
//		if(endDate != null ) keyword.put("endDate", endDate.toString());

		Page<ClassEntity> clasess = null;

		if (!keyword.isEmpty() ){
			System.out.println("length keyword: "+ keyword.size());
			clasess = clSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
		}

//			.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
//				.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
		else {
			clasess =clSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(clasess.getTotalElements()));
		headers.add("page", String.valueOf(clasess.getNumber()));
		headers.add("elementOfPages", String.valueOf(clasess.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(clasess.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",cls.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
				clasess.toList().stream().map(Class -> ClassConverter.toDTO(Class)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/class/{id}")
	public ClassDTO getClassById(@PathVariable(value = "id") Long classId) {
		return ClassConverter.toDTO(clSer.findById(classId));
	}

	// thêm mới bản ghi
	@PostMapping("public/class")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClassDTO addClass(@RequestBody @Validated ClassDTO CLass) {
		try {
			if (CLass.getName() == null || CLass.getCourseId() == null
					|| CLass.getTeacherId() == null)
				throw new BadRequestException("Value is missing");
			ClassEntity t = ClassConverter.toEntity(CLass);
			t.setId(null);
			return ClassConverter.toDTO(clSer.addclass(t));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN ADD A NEW CLASS] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
			throw new BadRequestException("Something went wrong!");
		}

	}

	// sửa bản ghi
	@PutMapping("public/class")
	public ClassDTO updateClassById(@RequestBody ClassDTO Class) {
		try {
			System.out.println("Update Class");
			if (Class.getId() == null){
				System.out.println("error ID");
				throw new BadRequestException("Value id is missing");
			}
			System.out.println("error out");
			return ClassConverter.toDTO(clSer.updateclass(ClassConverter.toEntity(Class)));
		} catch (Exception e) {
			System.out.println("Exception value");
			log.error("[ IN UPDATE CLASS] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}

	}

	// xóa bản ghi
	@DeleteMapping("class/{id}")
	public Map<String, Boolean> deleteClassById(@PathVariable(value = "id") Long classId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", clSer.deleteById(classId));
		return response;
	}


}
