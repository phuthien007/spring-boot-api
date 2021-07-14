package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.OperationQuery;
import springboot.FilterSpecification.Specification.TeacherSpecification;
import springboot.Repository.TeacherRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRep;


    // tìm tất cả bản ghi có phân trang
    @Cacheable(value = "teachers")
    public Page<TeacherEntity> getAll(PageRequest pageable) {
        return teacherRep.findAll(pageable);
    }

    // tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
    public Page<TeacherEntity> getAll(Pageable pageable, Map<String, String> keyword, List<String> sort)
    {
        TeacherSpecification teacherSpec = new TeacherSpecification();
        for (String input : keyword.keySet()) {
            teacherSpec.add(new FilterInput(input, keyword.get(input), OperationQuery.LIKE));
        }
        System.out.println("before sort");

        for (String input : sort) {
            System.out.println("Middle sort");
            teacherSpec.add(input);
        }
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
            // TODO: handle exception
            throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
        }
    }

}
