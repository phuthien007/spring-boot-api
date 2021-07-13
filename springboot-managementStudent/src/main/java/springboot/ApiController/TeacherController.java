package springboot.ApiController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.Converter.TeacherConverter;
import springboot.Model.DTO.TeacherDTO;
import springboot.Service.TeacherService;

@RestController
@RequestMapping(path = "/api/")
public class TeacherController {

    @Autowired
    private TeacherService teacherSer;

    // lấy tất cả các bản ghi
    @GetMapping("public/teacher")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    public ResponseEntity<?> getAllTeachers(
            // pageable
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            // sorting
//			@RequestParam(name="parameter sort", required = false) List<String> sort,
            // query paramerter all criteria
            @RequestParam(name = "fullname", required = false) String fullname,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "grade", required = false) String grade) {
        Map<String, String> keyword = new HashMap<>();
        if (fullname != null) keyword.put("fullname", fullname);
        if (email != null) keyword.put("email", email);
        if (phone != null) keyword.put("phone", phone);
        if (address != null) keyword.put("address", address);
        if (grade != null) keyword.put("grade", grade);
        Page<TeacherEntity> teachers = teacherSer.getAll(PageRequest.of(page, 20));
        teachers = teacherSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
//				.stream().map(student -> StudentConverter.toDTO(student)).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(teachers.getTotalElements()));
        headers.add("page", String.valueOf(teachers.getNumber()));
        headers.add("elementOfPages", String.valueOf(teachers.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(teachers.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",teachers.getTotalElements()  );	
        return ResponseEntity.ok().headers(headers).body(
                teachers.toList().stream().map(teacher -> TeacherConverter.toDTO(teacher)).collect(Collectors.toList()));
    }

    // lấy bản ghi theo id
    @GetMapping("public/teacher/{id}")
    public TeacherDTO getTeacherById(@PathVariable(value = "id") Long teacherId) {
        return TeacherConverter.toDTO(teacherSer.findById(teacherId));
    }

    // thêm mới bản ghi
    @PostMapping("public/teacher")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeacherDTO addTeacher(@RequestBody @Validated TeacherDTO teacher) {
        if (teacher.getFullname() == null)
            throw new BadRequestException("Value fullname is missing");
        TeacherEntity t = TeacherConverter.toEntity(teacher);
        return TeacherConverter.toDTO(teacherSer.addTeacher(t));
    }

    // sửa bản ghi
    @PutMapping("public/teacher")
    public TeacherDTO updateTeacherById(@RequestBody @Validated TeacherDTO teacher) {
        if (teacher.getId() == null)
            throw new BadRequestException("Value id is missing");
        return TeacherConverter.toDTO(teacherSer.updateTeacher(TeacherConverter.toEntity(teacher)));
    }

    // xóa bản ghi
    @DeleteMapping("teacher/{id}")
    public Map<String, Boolean> deleteTeacherById(@PathVariable(value = "id") Long teacherId) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("status", teacherSer.deleteById(teacherId));
        return response;
    }

}
