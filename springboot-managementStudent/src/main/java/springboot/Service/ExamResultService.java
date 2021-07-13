package springboot.Service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.ClassEntity;
import springboot.Entity.ExamEntity;
import springboot.Entity.ExamResultEntity;
import springboot.Entity.StudentEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.OperationQuery;
import springboot.FilterSpecification.Specification.ExamResultSpecification;
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

	// tìm tất cả bản ghi có phân trang
	@Cacheable("ExamResults")
	public Page<ExamResultEntity> getAll(PageRequest pageable) {
		return examResultRep.findAll(pageable);
	}

//	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	public Page<ExamResultEntity> getAll(Pageable pageable, Map<String, String> keyword) {
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
		ExamResultSpecification examResultSpec = new ExamResultSpecification();
		for(String key : keyword.keySet()){
			examResultSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
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
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
