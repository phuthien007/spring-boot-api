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

import springboot.Entity.StudentEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.StudentConverter;
import springboot.Model.DTO.StudentDTO;
import springboot.Service.StudentService;

@RestController
@RequestMapping(path="/api/")
public class StudentController {

	@Autowired
	private StudentService studentSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/student")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllstudents(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Page<StudentEntity> students =studentSer.getAll(PageRequest.of(page, 20));
		if (keyword != null)
			students = studentSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
//				.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(students.getTotalElements()));
		headers.add("page", String.valueOf(students.getNumber()));
		headers.add("elementOfPages", String.valueOf(students.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(students.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",students.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
				students.toList().stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/student/{id}")
	public StudentDTO getstudentById(@PathVariable(value = "id") Long studentId) {
		return StudentConverter.toDTO(studentSer.findById(studentId));
	}

	// thêm mới bản ghi
	@PostMapping("public/student")
	@ResponseStatus(value = HttpStatus.CREATED)
	public StudentDTO addstudent(@RequestBody @Validated StudentDTO student) {
		try {
			if (student.getFullname() == null || student.getAddress() == null || student.getEmail() == null)
				throw new BadRequestException("Some keyword is missing");
			StudentEntity t = StudentConverter.toEntity(student);
			t.setId(null);
			return StudentConverter.toDTO(studentSer.addStudent(t));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Something went wrong");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/student")
	public StudentDTO updatestudentById(@RequestBody @Validated StudentDTO student) {
		try {
			if (student.getId() == null)
				throw new BadRequestException("Value id is missing");
			return StudentConverter.toDTO(studentSer.updateStudent(StudentConverter.toEntity(student)));
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
		}

	// xóa bản ghi
	@DeleteMapping("student/{id}")
	public Map<String, Boolean> deletestudentById(@PathVariable(value = "id") Long studentId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", studentSer.deleteById(studentId));
		return response;
	}
	
}
