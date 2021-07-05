package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.CourseEntity;
import springboot.Entity.ExamEntity;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
	public Page<ExamEntity> findByNameContainingOrCourse(
			String keyword1, CourseEntity course, Pageable pageable);
	
	public Page<ExamEntity> findByNameContaining(String keyword1, Pageable pageable);
	
}
