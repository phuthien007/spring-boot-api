package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.PlanEntity;
import springboot.Entity.UserEntity;
//import springboot.FilterSpecification.Specification.PlanSpecification;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT user FROM UserEntity user ",
			countQuery = "SELECT COUNT(user) FROM UserEntity user"	)
	@EntityGraph(attributePaths = "role")
	public Page<UserEntity> findAll(Pageable pageable);

//	@Query(value = "SELECT plan FROM PlanEntity plan LEFT JOIN  plan.course  course")
//	@Query(value = "SELECT user FROM UserEntity user ")
	@EntityGraph(attributePaths = "role", type = EntityGraph.EntityGraphType.FETCH)
	public Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);

	public Page<UserEntity> findByUsernameOrEmailContainingOrFullnameContaining(
		String keyword1, String keyword2 , String keyword3, Pageable pageable	);
	
	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
	public UserEntity findByUsername(String username);
	public UserEntity findByForgotPassWordToken(String token);
	public UserEntity findByEmail(String email);
}
