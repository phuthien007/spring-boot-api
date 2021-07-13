package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.CourseEntity;
import springboot.Entity.ExamEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.OperationQuery;
import springboot.FilterSpecification.Specification.ExamSpecification;
import springboot.Repository.CourseRepository;
import springboot.Repository.ExamRepository;

import java.util.Map;

@Service
public class ExamService {

	@Autowired
	private ExamRepository examRep;
	@Autowired
	private CourseRepository courseRep;
	
	
	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "exams")
	public Page<ExamEntity> getAll(PageRequest pageable) {
		for(ExamEntity e : examRep.findAll(pageable)) {
			System.out.println(e.toString());
		}
		return examRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "exams")
	public Page<ExamEntity> getAll(Pageable pageable , Map<String, String> keyword) {
//		try {
//			Long id = Long.parseLong(keyword);
//			CourseEntity course = courseRep.findById(id).get();
//			return examRep.findByNameContainingOrCourse(keyword, course, pageable);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return examRep.findByNameContaining(keyword, pageable);
		ExamSpecification examSpec = new ExamSpecification();
		for( String key : keyword.keySet()){
			examSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}
		return  examRep.findAll(examSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "exams")
	public ExamEntity findById(Long ID) {
		return examRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("exam Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public ExamEntity addexam(ExamEntity exam) {
		CourseEntity course = courseRep.findById(exam.getCourse().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Course Not Found By ID = " + exam.getCourse().getId() + " Cant add this plan "));
		exam.setCourse(course);
		return examRep.save(exam);
	}

	
	// cập nhật dữ liệu
	@CachePut(value = "exams")
	public ExamEntity updateexam(ExamEntity exam) {
		ExamEntity t = examRep.findById(exam.getId())
				.orElseThrow(() -> new ResourceNotFoundException("exam Not Found By ID = " + exam.getId()));
		
		try {
			if(exam.getName() != null)
				t.setName(exam.getName());
			if (exam.getCourse() != null && exam.getCourse().getId() != null ) {
				CourseEntity course = courseRep.findById(exam.getCourse().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Course Not Found By ID = " + exam.getCourse().getId() + " Cant update this plan "));
				t.setCourse(course);
			}
			return examRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "exams", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			ExamEntity t = examRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("exam Not Found By ID = " + ID));
			examRep.delete(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
	
	
}
