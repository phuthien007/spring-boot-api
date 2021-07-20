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
import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.TeacherDTO;
import springboot.Model.Mapper.TeacherMapper;
import springboot.Model.MetaModel.TeacherMeta;
import springboot.Service.TeacherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/")
public class TeacherController {

    @Autowired
    private TeacherService teacherSer;

    @Autowired
    private TeacherMapper TeacherConverter;

    private Map<String, Map<String, List<String>>> validateInput(String[] params) {
        Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
        try {
            inputTransform = UtilController.transformInput(params);
            for (String index : inputTransform.keySet()) {
                for (String field : inputTransform.get(index).keySet()) {
                    if (!TeacherMeta.hasAttribute(field))
                        throw new BadRequestException("input invalid, no find any field");
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return inputTransform;
    }

    private static final Logger log = LogManager.getLogger(TeacherController.class);

    // lấy tất cả các bản ghi
    @GetMapping("public/teacher")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllTeachers(
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
        System.out.println("step1");
        Page<TeacherEntity> teachers = null;
        System.out.println("gia tri sort co bang null: " + sorting.isEmpty());

        if (!keyword.isEmpty())
            teachers = teacherSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
//			.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
//				.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
        else teachers = teacherSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(teachers.getTotalElements()));
        headers.add("page", String.valueOf(teachers.getNumber()));
        headers.add("elementOfPages", String.valueOf(teachers.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(teachers.getTotalPages()));
        log.info("IN getAllusers : size : {}", teachers.getTotalElements());
        System.out.println("done");
        return ResponseEntity.ok().headers(headers).body(
                teachers.toList().stream().map(teacher -> TeacherConverter.toDTO(teacher)).collect(Collectors.toList()));
    }

    // lấy bản ghi theo id
    @GetMapping("public/teacher/{id}")
    public TeacherDTO getTeacherById(@PathVariable(value = "id") Long teacherId) {
        log.info("IN get teacher by id : {}", () -> teacherId);
        return TeacherConverter.toDTO(teacherSer.findById(teacherId));
    }

    // thêm mới bản ghi
    @PostMapping("public/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDTO addTeacher(@RequestBody @Validated TeacherDTO teacher) {
        if (teacher.getFullname() == null)
            throw new BadRequestException("Value fullname is missing");
        TeacherEntity t = TeacherConverter.toEntity(teacher);
        log.info("IN add a new teacher : {}", () -> teacher);
        return TeacherConverter.toDTO(teacherSer.addTeacher(t));
    }

    // sửa bản ghi
    @PutMapping("public/teacher")
    public TeacherDTO updateTeacherById(@RequestBody @Validated TeacherDTO teacher) {
        if (teacher.getId() == null)
            throw new BadRequestException("Value id is missing");
        log.info("IN update teacher by id : {}", () -> teacher);
        return TeacherConverter.toDTO(teacherSer.updateTeacher(TeacherConverter.toEntity(teacher)));
    }

    // xóa bản ghi
    @DeleteMapping("teacher/{id}")
    public Map<String, Boolean> deleteTeacherById(@PathVariable(value = "id") Long teacherId) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("status", teacherSer.deleteById(teacherId));
        log.warn("IN delete a teacher by id : {}", () -> teacherId);
        return response;
    }

}
