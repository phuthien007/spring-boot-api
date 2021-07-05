package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.ExamResultEntity;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> {

//	public Page<ExamResultEntity> findByScoreOrStudentOrExamOrClass(Long keyword1, StudentEntity student,
//			ExamEntity exam, ClassEntity C, Pageable page);

}
