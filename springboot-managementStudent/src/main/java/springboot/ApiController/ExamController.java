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

import springboot.Entity.ExamEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.ExamConverter;
import springboot.Model.DTO.ExamDTO;
import springboot.Service.ExamService;

@RestController
@RequestMapping(path="/api/")
public class ExamController {

	@Autowired
	private ExamService examSer;

	// lấy tất cả các bản ghi
	@GetMapping("public/exam")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	public ResponseEntity<?> getAllexams(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			// filter parmas
			@RequestParam(name = "name", defaultValue = "" ,required = false) String name
	) {
		Page<ExamEntity> exams =examSer.getAll(PageRequest.of(page, 20));
		Map<String, String> keyword = new HashMap<>();
		if(name != null) keyword.put("name", name);
		if (!keyword.isEmpty())
			exams = examSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(exam -> ExamConverter.toDTO(exam)).collect(Collectors.toList());
//				.stream().map(exam -> ExamConverter.toDTO(exam)).collect(Collectors.toList());
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(exams.getTotalElements()));
		headers.add("page", String.valueOf(exams.getNumber()));
		headers.add("elementOfPages", String.valueOf(exams.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(exams.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",exams.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
				exams.toList().stream().map(exam -> ExamConverter.toDTO(exam)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/exam/{id}")
	public ExamDTO getexamById(@PathVariable(value = "id") Long examId) {
		return ExamConverter.toDTO(examSer.findById(examId));
	}

	// thêm mới bản ghi
	@PostMapping("public/exam")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ExamDTO addexam(@RequestBody @Validated ExamDTO exam) {
		try {
			if (exam.getName() == null || exam.getCourse().getId() == null)
				throw new BadRequestException("Value is missing");
			ExamEntity t = ExamConverter.toEntity(exam);
			t.setId(null);
			return ExamConverter.toDTO(examSer.addexam(t));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Something went wrong!");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/exam")
	public ExamDTO updateexamById(@RequestBody @Validated ExamDTO exam) {
		try {
			if (exam.getId() == null)
				throw new BadRequestException("Value id is missing");
			return ExamConverter.toDTO(examSer.updateexam(ExamConverter.toEntity(exam)));
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Value id is missing");
		}
		
	}

	// xóa bản ghi
	@DeleteMapping("exam/{id}")
	public Map<String, Boolean> deleteexamById(@PathVariable(value = "id") Long examId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", examSer.deleteById(examId));
		return response;
	}
	
}
