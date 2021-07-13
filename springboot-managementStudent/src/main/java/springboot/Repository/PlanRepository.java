package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.CourseEntity;
import springboot.Entity.PlanEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long>,
		JpaSpecificationExecutor<PlanEntity> {

	public Page<PlanEntity> findByNameContainingOrCourse(
			String keyword1,CourseEntity course ,Pageable pageable);
	
	public Page<PlanEntity> findByNameContaining(String keyword1 ,Pageable pageable);
	
}
