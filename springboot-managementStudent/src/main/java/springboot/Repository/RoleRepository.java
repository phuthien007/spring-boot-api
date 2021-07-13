package springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import springboot.Entity.RoleEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>,
        JpaSpecificationExecutor<RoleEntity> {

}
