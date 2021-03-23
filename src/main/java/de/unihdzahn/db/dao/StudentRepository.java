package de.unihdzahn.db.dao;

import de.unihdzahn.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query(value = "SELECT * FROM student s WHERE :name IN (s.prename, s.surname)", nativeQuery = true)
    Optional<Student> findStudentByName(@Param("name") String name);

    @Query(value = "SELECT * FROM student s WHERE s.hs_id = :id", nativeQuery = true)
    Optional<Student> findStudentById(@Param("id") String hs_id);

}
