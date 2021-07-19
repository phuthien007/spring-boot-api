package springboot.Service;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.ApiController.TeacherController;
import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification2;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.TeacherRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    private static final Logger log = LogManager.getLogger(TeacherController.class);

    @Autowired
    private TeacherRepository teacherRep;


    // tìm tất cả bản ghi có phân trang
    @Cacheable(value = "teachers")
    public Page<TeacherEntity> getAll(PageRequest pageable) {
        return teacherRep.findAll(pageable);
    }

    // tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
    public Page<TeacherEntity> getAll(Pageable pageable, Map<String, String> keyword)
    {
        GenericSpecification2<TeacherEntity> teacherSpec = new GenericSpecification2<>();
        for (String input : keyword.keySet()) {
            teacherSpec.add(new FilterInput(input, keyword.get(input), OperationQuery.LIKE));
        }
        System.out.println("before sort");
//		teacherSpec.add(new FilterInput("fullname", keyword.get("fullname"), OperationQuery.EQUALS));
        return teacherRep.findAll(teacherSpec, pageable);
    }

    // tìm kiếm theo id
    @CachePut(value = "teachers")
    public TeacherEntity findById(Long ID) {
        return teacherRep.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + ID));
    }

    // thêm mới 1 bản ghi
    public TeacherEntity addTeacher(TeacherEntity teacher) {
        if (teacherRep.existsByEmail(teacher.getEmail()) == true)
            throw new BadRequestException("Value email is exist. Please choose another email!");
//		if(teacher.getEmail() != null) {
//			TeacherEntity t = teacherRep.findByEmail(teacher.getEmail());
//			if ( t != null)
//				throw new BadRequestException("Value email is exist. Please choose another email!");
//		}	
        return teacherRep.save(teacher);
    }

    // cập nhật dữ liệu
    @CachePut(value = "teachers")
    public TeacherEntity updateTeacher(TeacherEntity teacher) {
        TeacherEntity t = teacherRep.findById(teacher.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + teacher.getId()));
        try {
            if (teacher.getFullname() != null)
                t.setFullname(teacher.getFullname());
            if (teacher.getAddress() != null)
                t.setAddress(teacher.getAddress());
            if (teacher.getEmail() != null )
                t.setEmail(teacher.getEmail());
            if (teacher.getGrade() != null )
                t.setGrade(teacher.getGrade());
            if (teacher.getPhone() != null )
                t.setPhone(teacher.getPhone());
            return teacherRep.save(t);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN SERVICE UPDATE A TEACHER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

            throw new BadRequestException(e.getMessage());
        }

    }

    // xóa bản ghi
    @CacheEvict(value = "teachers", allEntries = true)
    public Boolean deleteById(Long ID) {
        try {
            TeacherEntity t = teacherRep.findById(ID)
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + ID));
            teacherRep.delete(t);
            return true;
        } catch (Exception e) {
            log.error("[ IN SERVICE DELETE A TEACHER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

            // TODO: handle exception
            throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
        }
    }

}
