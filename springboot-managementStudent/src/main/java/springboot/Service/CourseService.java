package springboot.Service;

import java.util.Date;
import java.util.Map;

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
import springboot.Entity.CourseEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.CourseRepository;

@Service
public class CourseService {

	private static final Logger log = LogManager.getLogger(TeacherController.class);

	@Autowired
	private CourseRepository courseRep;

	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "courses")
	public Page<CourseEntity> getAll(PageRequest pageable) {
		return courseRep.findAll(pageable);
	}

	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "courses")
	public Page<CourseEntity> getAll(Pageable pageable, Map<String, String> keyword) {

		GenericSpecification<CourseEntity> courseSpec = new GenericSpecification<>();
		for( String key : keyword.keySet() ){
			courseSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}
		return  courseRep.findAll(courseSpec, pageable);
//		return courseRep.findByNameContainingOrTypeContaining(keyword, keyword, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "courses")
	public CourseEntity findById(Long ID) {
		return courseRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("Course Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public CourseEntity addcourse(CourseEntity course) {
		course.setCreateDate(new Date(System.currentTimeMillis()));
		return courseRep.save(course);
	}

	// cập nhật dữ liệu
	@CachePut(value = "courses")
	public CourseEntity updatecourse(CourseEntity course) {
		CourseEntity t = courseRep.findById(course.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Course Not Found By ID = " + course.getId()));
		try {
			if (course.getName() != null) {
//				System.out.println("old: " + t.getName() + " new: " + course.getName());

				t.setName(course.getName());
			}
			if (course.getType() != null) {
//				System.out.println("old: " + t.getType() + " new: " + course.getType());

				t.setType(course.getType());
			}
			return courseRep.save(t);

		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN SERVICE UPDATE A COURSE] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "courses", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			CourseEntity t = courseRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("Course Not Found By ID = " + ID));
			courseRep.delete(t);
			return true;
		} catch (Exception e) {
			log.error("[ IN SERVICE DELETE A COURSE] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
