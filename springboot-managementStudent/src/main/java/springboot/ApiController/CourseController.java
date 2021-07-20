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
import springboot.Entity.CourseEntity;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.CourseDTO;
import springboot.Model.Mapper.CourseMapper;
import springboot.Model.MetaModel.CourseMeta;
import springboot.Service.CourseService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/")
public class CourseController {

    @Autowired
    private CourseService courseSer;

    @Autowired
    private CourseMapper CourseConverter;

    private Map<String, Map<String, List<String>>> validateInput(String[] params) {
        Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
        try {
            inputTransform = UtilController.transformInput(params);
            for (String index : inputTransform.keySet()) {
                for (String field : inputTransform.get(index).keySet()) {
                    if (!CourseMeta.hasAttribute(field))
                        throw new BadRequestException("input invalid, no find any field");
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return inputTransform;
    }

    private static final Logger log = LogManager.getLogger(CourseController.class);


    // lấy tất cả các bản ghi
    @GetMapping("public/course")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllcourses(
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
        Page<CourseEntity> courses = null;
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
        if (!keyword.isEmpty())
            courses = courseSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
//			.stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
//				.stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList());
        else courses = courseSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(courses.getTotalElements()));
        headers.add("page", String.valueOf(courses.getNumber()));
        headers.add("elementOfPages", String.valueOf(courses.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(courses.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",courses.getTotalElements()  );	
        return ResponseEntity.ok().headers(headers).body(
                courses.toList().stream().map(course -> CourseConverter.toDTO(course)).collect(Collectors.toList()));
    }

    // lấy bản ghi theo id
    @GetMapping("public/course/{id}")
    public CourseDTO getcourseById(@PathVariable(value = "id") Long courseId) {
        return CourseConverter.toDTO(courseSer.findById(courseId));
    }

    // thêm mới bản ghi
    @PostMapping("public/course")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDTO addcourse(@RequestBody @Validated CourseDTO course) {
        if (course.getName() == null)
            throw new BadRequestException("Name is missing");
        CourseEntity t = CourseConverter.toEntity(course);
        t.setId(null);
        return CourseConverter.toDTO(courseSer.addcourse(t));
    }

    // sửa bản ghi
    @PutMapping("public/course")
    public CourseDTO updatecourseById(@RequestBody @Validated CourseDTO course) {
        try {
            if (course.getId() == null)
                throw new BadRequestException("Value id is missing");
            return CourseConverter.toDTO(courseSer.updatecourse(CourseConverter.toEntity(course)));
        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN UPDATE COURSE] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
            throw new BadRequestException("Value id is missing");
        }

    }

    // xóa bản ghi
    @DeleteMapping("course/{id}")
    public Map<String, Boolean> deletecourseById(@PathVariable(value = "id") Long courseId) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("status", courseSer.deleteById(courseId));
        return response;
    }


}
