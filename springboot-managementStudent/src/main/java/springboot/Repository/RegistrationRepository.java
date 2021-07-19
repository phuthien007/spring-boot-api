package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.*;
import springboot.Entity.CompositeKey.ClassStudentIdKey;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, ClassStudentIdKey >,
		JpaSpecificationExecutor<RegistrationEntity> {

//	@Query(value = "SELECT d FROM RegistrationEntity d ",
//	countQuery = "SELECT  COUNT(d) FROM RegistrationEntity d")
	@EntityGraph(attributePaths = {"c","s", "c.course","c.teacher"})
	public Page<RegistrationEntity> findAll(Pageable pageable);

//	@Query(value = "SELECT d FROM RegistrationEntity d ")
//	@EntityGraph(attributePaths = {"c","s"})
//	public Page<RegistrationEntity> findAll(Specification<RegistrationEntity> spec, Pageable pageable);

	public Page<RegistrationEntity> findByStatusContainingOrCOrS(
			String keyword1, ClassEntity c, StudentEntity s, Pageable pageable);
	
	public Page<RegistrationEntity> findByStatusContaining(
			String keyword1, Pageable pageable);

	
}
