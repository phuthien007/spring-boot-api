package springboot.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import springboot.Entity.ClassEntity;
import springboot.Entity.ExamEntity;
import springboot.Entity.ExamResultEntity;
import springboot.Entity.StudentEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification2;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.ClassRepository;
import springboot.Repository.ExamRepository;
import springboot.Repository.ExamResultRepository;
import springboot.Repository.StudentRepository;

@Service
public class ExamResultService {

	@Autowired
	private ExamResultRepository examResultRep;
	@Autowired
	private StudentRepository studentRep;
	@Autowired
	private ExamRepository examRep;
	@Autowired
	private ClassRepository classRep;

	private static final Logger log = LogManager.getLogger(TeacherController.class);


	// tìm tất cả bản ghi có phân trang
	@Cacheable("ExamResults")
	public Page<ExamResultEntity> getAll(PageRequest pageable) {
		return examResultRep.findAll(pageable);
	}

//	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	public Page<ExamResultEntity> getAll(Pageable pageable, Map<String, String> keyword, List<String> sort) {
//		try {
//			Long id = Long.parseLong(keyword);
//			StudentEntity student = studentRep.findById(id).get();
//			ExamEntity exam = examRep.findById(id).get();
//			ClassEntity c = classRep.findById(id).get();
//			return examResultRep.findByScoreOrStudentOrExamOrClass(id, student, exam, c,pageable);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new BadRequestException(e.getLocalizedMessage());
//		}
		GenericSpecification2<ExamResultEntity> examResultSpec = new GenericSpecification2<>();
		for(String key : keyword.keySet()){
			examResultSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}
		for(String item : sort) {
			System.out.println(item);
			examResultSpec.add(item);
		}
		return examResultRep.findAll(examResultSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "ExamResults")
	public ExamResultEntity findById(Long ID) {
		return examResultRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("examResult Not Found By ID = " + ID));
	}

//	// thêm mới 1 bản ghi

	public ExamResultEntity addexamResult(ExamResultEntity examResult) {
		StudentEntity student = studentRep.findById(examResult.getStudent().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Course Not Found By ID = " + examResult.getStudent().getId() + " Cant add this examResult "));
		ExamEntity exam = examRep.findById(examResult.getExam().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Course Not Found By ID = " + examResult.getExam().getId() + " Cant add this examResult "));
		ClassEntity c = classRep.findById(examResult.getC().getId()).orElseThrow(() -> new ResourceNotFoundException(
				"Course Not Found By ID = " + examResult.getC().getId() + " Cant add this examResult "));
		examResult.setC(c);
		examResult.setStudent(student);
		examResult.setExam(exam);
		examResult.setResultDate(new Date(System.currentTimeMillis()));
		return examResultRep.save(examResult);
	}

	// cập nhật dữ liệu
	@CachePut("ExamResults")
	public ExamResultEntity updateexamResult(ExamResultEntity examResult) {
		ExamResultEntity t = examResultRep.findById(examResult.getId())
				.orElseThrow(() -> new ResourceNotFoundException("examResult Not Found By ID = " + examResult.getId()));

		if (examResult.getStudent() != null && examResult.getStudent().getId() != null) {
			StudentEntity student = studentRep.findById(examResult.getStudent().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Course Not Found By ID = "
							+ examResult.getStudent().getId() + " Cant update this examResult "));
			t.setStudent(student);
		}
		if (examResult.getExam() != null && examResult.getExam().getId() != null) {
			ExamEntity exam = examRep.findById(examResult.getExam().getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Course Not Found By ID = " + examResult.getExam().getId() + " Cant update this examResult "));
			t.setExam(exam);
		}
		if (examResult.getC() != null && examResult.getC().getId() != null) {
			ClassEntity c = classRep.findById(examResult.getC().getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Course Not Found By ID = " + examResult.getC().getId() + " Cant update this examResult "));
			t.setC(c);
		}
		
		if( examResult.getNote() != null)
			t.setNote(examResult.getNote());
		
		if(examResult.getScore() != null)
			t.setScore(examResult.getScore());
		
		return examResultRep.save(t);
	}

	// xóa bản ghi
	@CacheEvict(value = "ExamResults", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			ExamResultEntity t = examResultRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("examResult Not Found By ID = " + ID));
			examResultRep.delete(t);
			return true;
		} catch (Exception e) {
			log.error("[ IN SERVICE DELETE A EXAM RESULT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
