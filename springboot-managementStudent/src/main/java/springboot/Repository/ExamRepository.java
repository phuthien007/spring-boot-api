package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.CourseEntity;
import springboot.Entity.ExamEntity;
import springboot.Entity.ExamResultEntity;
import springboot.Entity.TeacherEntity;
//import springboot.FilterSpecification.Specification.ExamResultSpecification;
//import springboot.FilterSpecification.Specification.ExamSpecification;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> ,
		JpaSpecificationExecutor<ExamEntity> {
	public Page<ExamEntity> findByNameContainingOrCourse(
			String keyword1, CourseEntity course, Pageable pageable);
	
	public Page<ExamEntity> findByNameContaining(String keyword1, Pageable pageable);

	@Query(value = "SELECT t FROM ExamEntity  t",
		countQuery = "SELECT COUNT(t) FROM ExamEntity t")
	@EntityGraph(attributePaths = {"course"})
	public Page<ExamEntity> findAll(Pageable pageable);

//	@Query(value = "SELECT t FROM ExamEntity  t")
	@EntityGraph(attributePaths = {"course"})
	public Page<ExamEntity> findAll(Specification<ExamEntity> spec, Pageable pageable);


}
