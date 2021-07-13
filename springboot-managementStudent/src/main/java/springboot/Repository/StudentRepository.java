package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.StudentEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> ,
		JpaSpecificationExecutor<StudentEntity> {
	
	public boolean existsByEmail(String email);
	public Page<StudentEntity>
	findByAddressContainingOrFullnameContainingOrEmailContainingOrPhoneContainingOrNoteContaining(
		String keyword1, String keyword2 , String keyword3
		, String keyword4, String keyword5, Pageable pageable);
		
	
}
