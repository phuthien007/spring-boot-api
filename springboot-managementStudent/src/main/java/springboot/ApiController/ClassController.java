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
	@GetMapping("public/cl")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllcls(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "keyword", defaultValue = "" ,required = false) String keyword) {
		Page<ClassEntity> cls =clSer.getAll(PageRequest.of(page, 20));
		if (keyword != "")
			cls = clSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
//				.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(cls.getTotalElements()));
		headers.add("page", String.valueOf(cls.getNumber()));
		headers.add("elementOfPages", String.valueOf(cls.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(cls.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",cls.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
				cls.toList().stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/cl/{id}")
	public ClassDTO getclById(@PathVariable(value = "id") Long clId) {
		return ClassConverter.toDTO(clSer.findById(clId));
	}

	// thêm mới bản ghi
	@PostMapping("public/cl")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ClassDTO addcl(@RequestBody @Validated ClassDTO cl) {
		try {
			if (cl.getName() == null || cl.getCourse().getId() == null || cl.getTeacher().getId() == null)
				throw new BadRequestException("Value is missing");
			ClassEntity t = ClassConverter.toEntity(cl);
			t.setId(null);
			return ClassConverter.toDTO(clSer.addclass(t));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Something went wrong!");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/cl")
	public ClassDTO updateclById(@RequestBody @Validated ClassDTO cl) {
		try {
			if (cl.getId() == null)
				throw new BadRequestException("Value id is missing");
			return ClassConverter.toDTO(clSer.updateclass(ClassConverter.toEntity(cl)));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Value id is missing");
		}
		
	}

	// xóa bản ghi
	@DeleteMapping("cl/{id}")
	public Map<String, Boolean> deleteclById(@PathVariable(value = "id") Long clId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", clSer.deleteById(clId));
		return response;
	}
	
	
}
