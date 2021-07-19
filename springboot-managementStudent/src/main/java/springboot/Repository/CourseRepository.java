package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>,
		JpaSpecificationExecutor<CourseEntity> {


	public Page<CourseEntity> findByNameContainingOrTypeContaining(
			String keyword1, String keyword2, Pageable pageable);
	
}
