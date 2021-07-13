package springboot.ApiController;

import java.util.Date;
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
			@RequestParam(name = "name" ,required = false) String name,
			@RequestParam(name = "startDate" ,required = false) Date startDate,
			@RequestParam(name = "endDate" ,required = false) Date endDate,
			@RequestParam(name = "status" ,required = false) String status) {
		Page<ClassEntity> clasess =clSer.getAll(PageRequest.of(page, 20));
		Map<String, String> keyword = new HashMap<>();
		if(name != null) keyword.put("name", name);
		if(startDate != null) keyword.put("startDate", startDate.toString());
		if(endDate != null ) keyword.put("endDate", endDate.toString());
		if(status != null) keyword.put("status", status);
		if (!keyword.isEmpty())
			clasess = clSer.getAll(PageRequest.of(page, 20), keyword);
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
