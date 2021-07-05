package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.AuthorizationEachRoleEntity;
import springboot.Entity.CompositeKey.PermistionRoleIdKey;

@Repository
public interface AuthorizationEachRoleRepository
		extends JpaRepository<AuthorizationEachRoleEntity, PermistionRoleIdKey> {

}
