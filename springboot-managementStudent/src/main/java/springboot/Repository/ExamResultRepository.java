package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.ExamResultEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> ,
        JpaSpecificationExecutor<ExamResultEntity> {

//	public Page<ExamResultEntity> findByScoreOrStudentOrExamOrClass(Long keyword1, StudentEntity student,
//			ExamEntity exam, ClassEntity C, Pageable page);

}
