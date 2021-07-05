package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.TeacherEntity;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

	public TeacherEntity findByEmail(String email);

	public boolean existsByEmail(String email);
	
	public Page<TeacherEntity> findByAddressContainingOrEmailContainingOrFullnameContainingOrGradeContainingOrPhoneContaining(
			String keyword, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable);

}
