package springboot.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springboot.Entity.ClassEntity;
import springboot.Entity.EventEntity;
import springboot.Entity.ExamEntity;
import springboot.Entity.TeacherEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>,
        JpaSpecificationExecutor<EventEntity> {


    public Page<EventEntity> findByNameContainingOrStatusContainingOrC(
            String keyword1, String keyword2, ClassEntity cl, Pageable pageable);

    public List<EventEntity> findByC(ClassEntity c);

    public Page<EventEntity> findByNameContainingOrStatusContaining(
            String keyword1, String keyword2, Pageable pageable);


    @Query(value = "SELECT t FROM EventEntity  t",
        countQuery = "SELECT COUNT(t) FROM  EventEntity t")
    @EntityGraph(attributePaths = {"c"})
    public Page<EventEntity> findAll(Pageable pageable);

//    @Query(value = "SELECT t FROM EventEntity  t")
    @EntityGraph(attributePaths = {"c"})
    public Page<EventEntity> findAll(Specification <EventEntity> spec, Pageable pageable);

}
