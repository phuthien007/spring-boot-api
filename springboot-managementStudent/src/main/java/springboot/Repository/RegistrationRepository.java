package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.ClassEntity;
import springboot.Entity.RegistrationEntity;
import springboot.Entity.StudentEntity;
import springboot.Entity.CompositeKey.ClassStudentIdKey;
import springboot.Entity.TeacherEntity;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, ClassStudentIdKey >,
		JpaSpecificationExecutor<RegistrationEntity> {

	public Page<RegistrationEntity> findByStatusContainingOrCOrS(
			String keyword1, ClassEntity c, StudentEntity s, Pageable pageable);
	
	public Page<RegistrationEntity> findByStatusContaining(
			String keyword1, Pageable pageable);

	
}
