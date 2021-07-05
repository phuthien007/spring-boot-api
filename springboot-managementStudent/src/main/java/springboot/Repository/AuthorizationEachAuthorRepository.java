package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.AuthorizationEachAuthorEntity;
import springboot.Entity.CompositeKey.PermistionUserIdKey;

@Repository
public interface AuthorizationEachAuthorRepository
		extends JpaRepository<AuthorizationEachAuthorEntity, PermistionUserIdKey> {

}
