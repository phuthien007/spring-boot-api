package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.Entity.PermistionEntity;

@Repository
public interface PermistionRepository extends JpaRepository<PermistionEntity, Long>{

}
