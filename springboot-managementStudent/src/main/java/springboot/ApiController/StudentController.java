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

import springboot.Entity.StudentEntity;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.StudentDTO;
import springboot.Model.Mapper.StudentMapper;
import springboot.Model.MetaModel.StudentMeta;
import springboot.Model.MetaModel.TeacherMeta;
import springboot.Service.StudentService;

@RestController
@RequestMapping(path = "/api/")
public class StudentController {

    @Autowired
    private StudentService studentSer;

    @Autowired
    private StudentMapper StudentConverter;

    private static final Logger log = LogManager.getLogger(StudentController.class);
    private Map<String, Map<String, List<String>>> validateInput(String[] params) {
        Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
        try {
            inputTransform = UtilController.transformInput(params);
            for (String index : inputTransform.keySet()) {
                for (String field : inputTransform.get(index).keySet()) {
                    if (!StudentMeta.hasAttribute(field))
                        throw new BadRequestException("input invalid, no find any field");
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return inputTransform;
    }

    // lấy tất cả các bản ghi
    @GetMapping("public/student")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllstudents(
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
        Page<StudentEntity> students = null;
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
        // sort

        if (!keyword.isEmpty())
            students = studentSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
//			.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
//				.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
        else students = studentSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(students.getTotalElements()));
        headers.add("page", String.valueOf(students.getNumber()));
        headers.add("elementOfPages", String.valueOf(students.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(students.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",students.getTotalElements()  );	
        return ResponseEntity.ok().headers(headers).body(
                students.toList().stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList()));
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
            log.error("[ IN ADD A NEW STUDENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

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
            log.error("[ IN UPDATE A  STUDENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

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
