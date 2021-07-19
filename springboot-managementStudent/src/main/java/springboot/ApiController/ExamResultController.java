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

import springboot.Entity.ExamResultEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.DTO.ExamResultDTO;
import springboot.Model.Mapper.ExamResultMapper;
import springboot.Service.ExamResultService;

@RestController
@RequestMapping(path="/api/")
public class ExamResultController {
	
	@Autowired
	private ExamResultService examResultSer;

	@Autowired
	private ExamResultMapper ExamResultConverter;

	private static final Logger log = LogManager.getLogger(TeacherController.class);

	// lấy tất cả các bản ghi
	@GetMapping("public/examResult")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllexamResults(
			// pageable
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name="sort", required = false, defaultValue = "id|asc")List<String> sorting
			) {
/*
// filter params
			@RequestParam(name = "Equal Score",  required = false) Long scoreEqual,
			@RequestParam(name = "Greater Than Score",  required = false) Long scoreGreater,
			@RequestParam(name = "Less Than Score",  required = false) Long scoreLess,

			@RequestParam(name = "Equal resultDate", required = false) Date resultDateEqual,
			@RequestParam(name = "Greater Than resultDate", required = false) Date resultDateGreater,
			@RequestParam(name = "Less Than resultDate", required = false) Date resultDateLess,

			@RequestParam(name = "Equal Note ",required = false) String noteEqual,
			@RequestParam(name = "Not Equal Note",required = false) String noteNotEqual,
			@RequestParam(name = "Like Note",required = false) String noteLike,
			// sorting
			@RequestParam(name = "sort", required = false) List<String> sort
* */
		Page<ExamResultEntity> examResults =examResultSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
//		Map<String, String > keyword = new HashMap<>();
//		if(score != null) keyword.put("score", String.valueOf(score));
//		if(resultDate != null) keyword.put("resultDate", resultDate.toString());
//		if(note != null ) keyword.put("note", note);
//		if( !keyword.isEmpty() || sort != null)
//			examResultSer.getAll(PageRequest.of(page, 20), keyword, sort);
//		if (keyword != null) {
//////			System.out.println("thuc hien 1");
//			examResults = examResultSer.getAll(PageRequest.of(page, 20), keyword);
//////			System.out.println("thuc hien 1");
//		}
//			.stream().map(examResult -> ExamResultConverter.toDTO(examResult)).collect(Collectors.toList());
//				.stream().map(examResult -> ExamResultConverter.toDTO(examResult)).collect(Collectors.toList());

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(examResults.getTotalElements()));
		headers.add("page", String.valueOf(examResults.getNumber()));
		headers.add("elementOfPages", String.valueOf(examResults.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(examResults.getTotalPages()));
		
			return ResponseEntity.ok().headers(headers).body(
				examResults.toList().stream().map(examResult -> ExamResultConverter.toDTO(examResult)).collect(Collectors.toList()) );
	}


	
	// lấy bản ghi theo id
	@GetMapping("public/examResult/{id}")
	public ExamResultDTO getexamResultById(@PathVariable(value = "id") Long examResultId) {
//		for( ExamResultEntity e: examResults.toList() ) {
//			System.out.println("asd " +ExamResultConverter.toDTO(examResultSer.findById(examResultId)) );
//		}
		return ExamResultConverter.toDTO(examResultSer.findById(examResultId));
	}

	// thêm mới bản ghi
	@PostMapping("public/examResult")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ExamResultDTO addexamResult(@RequestBody @Validated ExamResultDTO examResult) {
		try {
			if (examResult.getScore() == null || examResult.getClassId() == null
				|| examResult.getExamId() == null || examResult.getStudentId() == null)
				throw new BadRequestException("Value is missing");
			ExamResultEntity t = ExamResultConverter.toEntity(examResult);
			t.setId(null);
			return ExamResultConverter.toDTO(examResultSer.addexamResult(t));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN ADD A NEW EXAM RESULT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
			System.out.println("[ IN UPDATE A EXAM RESULT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
			throw new BadRequestException("Something went wrong!");
		}
		
	}

	// sửa bản ghi
	@PutMapping("public/examResult")
	public ExamResultDTO updateExamResultById(@RequestBody @Validated ExamResultDTO examResult) {
		try {
			if (examResult.getId() == null)
				throw new BadRequestException("Value id is missing");
			return ExamResultConverter.toDTO(examResultSer.updateexamResult(ExamResultConverter.toEntity(examResult)));
		
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN UPDATE A EXAM RESULT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
			System.out.println("[ IN UPDATE A EXAM RESULT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
			throw new BadRequestException("Value id is missing");
		}
	}

	// xóa bản ghi
	@DeleteMapping("examResult/{id}")
	public Map<String, Boolean> deleteexamResultById(@PathVariable(value = "id") Long examResultId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("status", examResultSer.deleteById(examResultId));
		return response;
	}
	
}
