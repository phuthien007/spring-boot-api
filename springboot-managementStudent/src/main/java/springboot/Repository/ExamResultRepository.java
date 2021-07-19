package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.ExamResultEntity;
import springboot.Entity.TeacherEntity;
//import springboot.FilterSpecification.Specification.ExamResultSpecification;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResultEntity, Long> ,
        JpaSpecificationExecutor<ExamResultEntity> {

//    @Query(value = "SELECT t FROM ExamResultEntity  t",
//    countQuery = "SELECT  COUNT(t) FROM ExamResultEntity  t")
    @EntityGraph(attributePaths = {"student","exam","c", "c.course","c.teacher","exam.course"})
    public Page<ExamResultEntity> findAll(Pageable pageable);

//    @Query(value = "SELECT t FROM ExamResultEntity  t")
//    @EntityGraph(attributePaths = {"student","exam","c"})
//    public Page<ExamResultEntity> findAll(Specification<ExamResultEntity> spec, Pageable pageable);

//	public Page<ExamResultEntity> findByScoreOrStudentOrExamOrClass(Long keyword1, StudentEntity student,
//			ExamEntity exam, ClassEntity C, Pageable page);

}
