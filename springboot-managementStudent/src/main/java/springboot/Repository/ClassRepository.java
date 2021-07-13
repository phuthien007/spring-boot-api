package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long>,
		JpaSpecificationExecutor<ClassEntity> {

	public Page<ClassEntity> findByNameContainingOrStatusContainingOrTeacherOrCourse(
			String keyword1, String keyword2, TeacherEntity teacher, CourseEntity course, Pageable pageable);
	public Page<ClassEntity> findByNameContainingOrStatusContaining(
			String keyword1, String keyword2, Pageable pageable);
	
}
