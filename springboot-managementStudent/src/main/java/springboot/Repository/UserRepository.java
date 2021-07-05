package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
 
	public Page<UserEntity> findByUsernameOrEmailContainingOrFullnameContaining(
		String keyword1, String keyword2 , String keyword3, Pageable pageable	);
	
	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
	public UserEntity findByUsername(String username);
	public UserEntity findByForgotPassWordToken(String token);
	public UserEntity findByEmail(String email);
}
