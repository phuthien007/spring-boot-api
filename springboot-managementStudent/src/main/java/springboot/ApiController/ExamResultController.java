package springboot.ApiController;

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
import org.springframework.web.bind.annotation.*;
import springboot.Entity.ExamResultEntity;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.ExamResultDTO;
import springboot.Model.Mapper.ExamResultMapper;
import springboot.Model.MetaModel.ExamResultMeta;
import springboot.Service.ExamResultService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/")
public class ExamResultController {

    @Autowired
    private ExamResultService examResultSer;

    @Autowired
    private ExamResultMapper ExamResultConverter;

    private Map<String, Map<String, List<String>>> validateInput(String[] params) {
        Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
        try {
            inputTransform = UtilController.transformInput(params);
            for (String index : inputTransform.keySet()) {
                for (String field : inputTransform.get(index).keySet()) {
                    if (!ExamResultMeta.hasAttribute(field))
                        throw new BadRequestException("input invalid, no find any field");
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return inputTransform;
    }

    private static final Logger log = LogManager.getLogger(ExamResultController.class);

    // lấy tất cả các bản ghi
    @GetMapping("public/examResult")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllexamResults(
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

        Page<ExamResultEntity> examResults = null;
		if (!keyword.isEmpty()){
			examResults =  examResultSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
		}
		else
			examResults =  examResultSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));

		HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(examResults.getTotalElements()));
        headers.add("page", String.valueOf(examResults.getNumber()));
        headers.add("elementOfPages", String.valueOf(examResults.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(examResults.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(
                examResults.toList().stream().map(examResult -> ExamResultConverter.toDTO(examResult)).collect(Collectors.toList()));
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
