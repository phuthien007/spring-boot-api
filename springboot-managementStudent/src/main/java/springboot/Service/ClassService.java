package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.Repository.ClassRepository;
import springboot.Repository.CourseRepository;
import springboot.Repository.TeacherRepository;

@Service
public class ClassService {

	@Autowired
	private ClassRepository classRep;
	@Autowired
	private TeacherRepository teacherRep;
	@Autowired
	private CourseRepository courseRep;

	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "classes")
	public Page<ClassEntity> getAll(PageRequest pageable) {
//		for (ClassEntity e : classRep.findAll(pageable)) {
//			System.out.println(e.toString());
//		}
		return classRep.findAll(pageable);
	}

	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "classes")
	public Page<ClassEntity> getAll(Pageable pageable, String keyword) {
		try {
			Long id = Long.parseLong(keyword);
			CourseEntity course = courseRep.findById(id).get();
			TeacherEntity teacher = teacherRep.findById(id).get();
			return classRep.findByNameContainingOrStatusContainingOrTeacherOrCourse(keyword, keyword, teacher, course,
					pageable);

		} catch (Exception e) {
			// TODO: handle exception
			return classRep.findByNameContainingOrStatusContaining(keyword, keyword, pageable);

		}
	}

	// tìm kiếm theo id
	@CachePut(value = "classes")
	public ClassEntity findById(Long ID) {
		return classRep.findById(ID).orElseThrow(() -> new ResourceNotFoundException("class Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public ClassEntity addclass(ClassEntity cl) {
		CourseEntity course = courseRep.findById(cl.getCourse().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Course Not Found By ID = " + cl.getCourse().getId() + " Cant add this class "));
		TeacherEntity teacher = teacherRep.findById(cl.getTeacher().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Teacher Not Found By ID = " + cl.getTeacher().getId() + " Cant add this class "));

		cl.setCourse(course);
		cl.setTeacher(teacher);
		return classRep.save(cl);
	}

	// cập nhật dữ liệu
	@CachePut(value = "classes")
	public ClassEntity updateclass(ClassEntity cl) {
		ClassEntity t = classRep.findById(cl.getId())
				.orElseThrow(() -> new ResourceNotFoundException("class Not Found By ID = " + cl.getId()));
		try {
			if (t.getName() != null && !cl.getName().equals(t.getName()))
				t.setName(cl.getName());
			if (cl.getCourse().getId() != t.getCourse().getId()) {
				CourseEntity course = courseRep.findById(cl.getCourse().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Course Not Found By ID = " + cl.getCourse().getId() + " Cant update this class "));
				t.setCourse(course);
			}
			if (cl.getTeacher().getId() != t.getTeacher().getId()) {
				TeacherEntity teacher = teacherRep.findById(cl.getTeacher().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Teacher Not Found By ID = " + cl.getCourse().getId() + " Cant update this class "));
				t.setTeacher(teacher);
			}
			if (cl.getStartDate() != t.getStartDate())
				t.setStartDate(cl.getStartDate());
			if (t.getEndDate() != null && cl.getEndDate() != t.getEndDate())
				t.setEndDate(cl.getEndDate());
			if ( t.getStatus()!= null && !cl.getStatus().equals(t.getStatus()))
				t.setStatus(cl.getStatus());
			return classRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "classes", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			ClassEntity t = classRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("class Not Found By ID = " + ID));
			classRep.delete(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
