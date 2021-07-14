package springboot.ApiController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import springboot.Entity.ClassEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.ClassConverter;
import springboot.Model.DTO.ClassDTO;
import springboot.Service.ClassService;

@RestController
@RequestMapping(path = "/api/")
public class ClassController {

	@Autowired
	private ClassService clSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/class")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getAllClass(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			// filter Params
			@RequestParam(name = "equal name" ,required = false) String equalName,
			@RequestParam(name = "like name" ,required = false) String likeName,
			@RequestParam(name = "not equal name" ,required = false) String notEqualName,

			@RequestParam(name = "equal startDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSX") Date equalStartDate,
			@RequestParam(name = "greater than startDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date greaterThanStartDate,
			@RequestParam(name = "less than startDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lessThanStartDate,

			@RequestParam(name = "equal endDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSX") Date equalEndDate,
			@RequestParam(name = "greater than endDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date greaterThanEndDate,
			@RequestParam(name = "less than endDate" ,required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lessThanEndDate,

			// sorting
			@RequestParam(name = "sorting", required = false, defaultValue = "id|asc")List<String> sort
			) {
		Page<ClassEntity> clasess =clSer.getAll(PageRequest.of(page, 20));
		Map<String, Object> greaterThan = new HashMap<>();
		if(greaterThanStartDate != null) greaterThan.put("startDate", greaterThanStartDate);
		if(greaterThanEndDate != null) greaterThan.put("endDate", greaterThanEndDate);

		Map<String, Object> lessThan = new HashMap<>();
		if(lessThanStartDate != null) lessThan.put("startDate", lessThanStartDate);
		if(lessThanEndDate != null) lessThan.put("endDate", lessThanEndDate);

		Map<String, Object> equal = new HashMap<>();
		System.out.println("equal date: " + equalStartDate);
		if(equalStartDate != null) equal.put("startDate", equalStartDate);
		System.out.println(" addded	 equal date: " + equalStartDate);
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

		if (!keyword.isEmpty() || sort !=null)
			clasess = clSer.getAll(PageRequest.of(page, 20), keyword, sort);
//			.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
//				.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
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
			if (CLass.getName() == null || CLass.getCourse().getId() == null
					|| CLass.getTeacher().getId() == null)
				throw new BadRequestException("Value is missing");
			ClassEntity t = ClassConverter.toEntity(CLass);
			t.setId(null);
			return ClassConverter.toDTO(clSer.addclass(t));
		} catch (Exception e) {
			// TODO: handle exception
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
