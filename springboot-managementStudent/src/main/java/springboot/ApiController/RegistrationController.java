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

import springboot.Entity.RegistrationEntity;
import springboot.Entity.CompositeKey.ClassStudentIdKey;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.RegistrationConverter;
import springboot.Model.DTO.RegistrationDTO;
import springboot.Service.RegistrationService;

@RestController
@RequestMapping(path = "/api/")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/registration")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllregistrations(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Page<RegistrationEntity> registrations = registrationSer.getAll(PageRequest.of(page, 20));
		if (keyword != null) {
//			System.out.println("thuc hien 1");
			registrations = registrationSer.getAll(PageRequest.of(page, 20), keyword);
//			System.out.println("thuc hien 1");
		}
//			.stream().map(registration -> RegistrationConverter.toDTO(registration)).collect(Collectors.toList());
//				.stream().map(registration -> RegistrationConverter.toDTO(registration)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(registrations.getTotalElements()));
		headers.add("page", String.valueOf(registrations.getNumber()));
		headers.add("elementOfPages", String.valueOf(registrations.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(registrations.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",registrations.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(registrations.toList().stream()
				.map(registration -> RegistrationConverter.toDTO(registration)).collect(Collectors.toList()));
	}

	// lấy bản ghi theo id
	@GetMapping("public/registration/{class}/{student}")
	public RegistrationDTO getregistrationById(@PathVariable(value = "class") Long classId,
			@PathVariable(value = "student") Long studentId) {
		ClassStudentIdKey registrationId = new ClassStudentIdKey(classId, studentId);
		return RegistrationConverter.toDTO(registrationSer.findById(registrationId));
	}

	// thêm mới bản ghi
	@PostMapping("public/registration")
	@ResponseStatus(value = HttpStatus.CREATED)
	public RegistrationDTO addregistration(@RequestBody RegistrationDTO registration) {
		try {
			if (registration.getC().getId() == null || registration.getStatus() == null
					|| registration.getS().getId() == null)
				throw new BadRequestException("Value is missing");
			RegistrationEntity t = RegistrationConverter.toEntity(registration);
			t.setId(new ClassStudentIdKey(0L, 0L));
			return RegistrationConverter.toDTO(registrationSer.addregistration(t));
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println(e.getLocalizedMessage());
			throw new BadRequestException("Something went wrong!");
		}

	}

	// sửa bản ghi
	@PutMapping("public/registration")
	public RegistrationDTO updateregistrationById(@RequestBody @Validated RegistrationDTO registration) {
		try {
			if (registration.getC() == null || registration.getS() == null)
				throw new BadRequestException("Value id is missing");
			return RegistrationConverter
					.toDTO(registrationSer.updateregistration(RegistrationConverter.toEntity(registration)));

		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@DeleteMapping("registration/{class}/{student}")
	public Map<String, Boolean> deleteregistrationById(@PathVariable(value = "class") Long classId,
			@PathVariable(value = "student") Long studentId) {
		ClassStudentIdKey registrationId = new ClassStudentIdKey(classId, studentId);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", registrationSer.deleteById(registrationId));
		return response;
	}

}