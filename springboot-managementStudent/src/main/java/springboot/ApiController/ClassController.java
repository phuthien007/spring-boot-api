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
import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.OperationQuery;
import springboot.Model.DTO.ClassDTO;
import springboot.Model.Mapper.ClassMapper;
import springboot.Model.MetaModel.ClassMeta;
import springboot.Repository.CourseRepository;
import springboot.Service.ClassService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/")
public class ClassController {

    @Autowired
    private ClassService clSer;


    @Autowired
    private ClassMapper ClassConverter;

    private static final Logger log = LogManager.getLogger(ClassController.class);

    private Map<String, Map<String, List<String>>> validateInput(String [] params) {
        Map<String, Map<String, List<String>>> inputTransform = new HashMap<>();
        try {
            inputTransform = UtilController.transformInput(params);
            for (String index : inputTransform.keySet()) {
                for (String field : inputTransform.get(index).keySet()) {
                    if (!ClassMeta.hasAttribute(field))
                        throw new BadRequestException("input invalid, no find any field");
                }
            }
        }
        catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        return inputTransform;
    }

    // lấy tất cả các bản ghi
    @GetMapping("public/class")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllClass(
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

//		System.out.println("equal name " + equalName);
//
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
        System.out.println(keyword);
//		if(startDate != null) keyword.put("startDate", startDate.toString());
//		if(endDate != null ) keyword.put("endDate", endDate.toString());

        Page<ClassEntity> clasess = null;

        if (!keyword.isEmpty()) {
            System.out.println("length keyword: " + keyword.size());
            clasess = clSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))), keyword);
        }

//			.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
//				.stream().map(cl -> ClassConverter.toDTO(cl)).collect(Collectors.toList());
        else {
        clasess = clSer.getAll(PageRequest.of(page, 20, Sort.by(UtilController.listSort(sorting))));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(clasess.getTotalElements()));
        headers.add("page", String.valueOf(clasess.getNumber()));
        headers.add("elementOfPages", String.valueOf(clasess.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(clasess.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",cls.getTotalElements()  );	
        return ResponseEntity.ok().headers(headers).body(
                clasess.toList().stream().map(Class -> ClassConverter.toDTO(Class)).collect(Collectors.toList()));
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
            if (CLass.getName() == null || CLass.getCourseId() == null
                    || CLass.getTeacherId() == null)
                throw new BadRequestException("Value is missing");
            ClassEntity t = ClassConverter.toEntity(CLass);
            t.setId(null);
            return ClassConverter.toDTO(clSer.addclass(t));
        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN ADD A NEW CLASS] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));
            throw new BadRequestException("Something went wrong!");
        }

    }

    // sửa bản ghi
    @PutMapping("public/class")
    public ClassDTO updateClassById(@RequestBody ClassDTO Class) {
        try {
            System.out.println("Update Class");
            if (Class.getId() == null) {
                System.out.println("error ID");
                throw new BadRequestException("Value id is missing");
            }
            System.out.println("error out");
            return ClassConverter.toDTO(clSer.updateclass(ClassConverter.toEntity(Class)));
        } catch (Exception e) {
            System.out.println("Exception value");
            log.error("[ IN UPDATE CLASS] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

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
