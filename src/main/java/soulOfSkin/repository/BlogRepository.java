package soulOfSkin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulOfSkin.models.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

	@Query("select f from Blog as f order by id desc")
	List<Blog> findAllBlogs();

	@Query(value = "SELECT * FROM blog e WHERE e.share='skin' ORDER BY id DESC", nativeQuery = true)
	List<Blog> getAllBlogsForSkin();

	@Query(value = "SELECT * FROM blog e  WHERE e.share='life' ORDER BY id DESC", nativeQuery = true)
	List<Blog> getAllBlogsForLife();

}
