package soulOfSkin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import soulOfSkin.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	@Query(value = "SELECT * FROM users e JOIN user_role r ON e.id=r.user_id WHERE r.role_id=2", nativeQuery = true)
	List<User> fetchAllTeacher();

	@Query(value = "SELECT * FROM users e JOIN user_role r ON e.id=r.user_id WHERE r.role_id=4", nativeQuery = true)
	List<User> fetchAllParent();

}
