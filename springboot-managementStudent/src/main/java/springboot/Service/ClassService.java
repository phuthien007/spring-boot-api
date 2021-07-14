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
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.OperationQuery;
import springboot.FilterSpecification.Specification.ClassSpecification;
import springboot.Repository.ClassRepository;
import springboot.Repository.CourseRepository;
import springboot.Repository.TeacherRepository;

import java.util.List;
import java.util.Map;

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
	public Page<ClassEntity> getAll(Pageable pageable, Map<String, Map<String, Object>> keyword, List<String> sort) {
//		try {
//			Long id = Long.parseLong(keyword);
//			CourseEntity course = courseRep.findById(id).get();
//			TeacherEntity teacher = teacherRep.findById(id).get();
//			return classRep.findByNameContainingOrStatusContainingOrTeacherOrCourse(keyword, keyword, teacher, course,
//					pageable);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return classRep.findByNameContainingOrStatusContaining(keyword, keyword, pageable);
//
//		}
		ClassSpecification classSpec = new ClassSpecification();
		// using filter generic 2
		for(String operation : keyword.keySet()){
			System.out.println("querying" + operation);
			for(String key : keyword.get(operation).keySet()){
//				System.out.println("operation " + operation + " " + keyword.get(operation).get(key));
//				System.out.println(key + " query " +keyword.get(key) );
				classSpec.add( new FilterInput(key, keyword.get(operation).get(key) , operation.trim()));
//				/System.out.println("done " + key);
			}
		}
		for(String item : sort){
			classSpec.add(item);
		}
		return classRep.findAll(classSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "classes")
	public ClassEntity findById(Long ID) {
		return classRep.findById(ID).orElseThrow(() -> new ResourceNotFoundException("class Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public ClassEntity addclass(ClassEntity cl) {
//		CourseEntity course = courseRep.findById(cl.getCourse().getId())
//				.orElseThrow(() -> new ResourceNotFoundException(
//						"Course Not Found By ID = " + cl.getCourse().getId() + " Cant add this class "));
//		TeacherEntity teacher = teacherRep.findById(cl.getTeacher().getId())
//				.orElseThrow(() -> new ResourceNotFoundException(
//						"Teacher Not Found By ID = " + cl.getTeacher().getId() + " Cant add this class "));
		CourseEntity course = new CourseEntity();
		course.setId(cl.getCourse().getId());
		TeacherEntity teacher = new TeacherEntity();
		teacher.setId(cl.getTeacher().getId());
		cl.setCourse(course);
		cl.setTeacher(teacher);
		return classRep.save(cl);
	}

	// cập nhật dữ liệu
	@CachePut(value = "classes")
	public ClassEntity updateclass(ClassEntity Class) {
		ClassEntity t = classRep.findById(Class.getId())
				.orElseThrow(() -> new ResourceNotFoundException("class Not Found By ID = " + Class.getId()));
		try {
			if (Class.getName() != null )
				t.setName(Class.getName());
			if (Class.getCourse() != null && Class.getCourse().getId() != null) {
				System.out.println("Run course");
				CourseEntity course = courseRep.findById(Class.getCourse().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Course Not Found By ID = " + Class.getCourse().getId() + " Cant update this class "));
				t.setCourse(course);
			}
			if (Class.getTeacher() != null && Class.getTeacher().getId() != null ) {
				System.out.println("Run teacher");
				TeacherEntity teacher = teacherRep.findById(Class.getTeacher().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Teacher Not Found By ID = " + Class.getCourse().getId() + " Cant update this class "));
				t.setTeacher(teacher);
			}
			if (Class.getStartDate() != null )
				t.setStartDate(Class.getStartDate());
			if (Class.getEndDate() != null )
				t.setEndDate(Class.getEndDate());
			if ( Class.getStatus()!= null )
				t.setStatus(Class.getStatus());
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
