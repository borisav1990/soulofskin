package soulOfSkin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import soulOfSkin.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select f from Comment as f where f.blog.id = ?1 and f.allowed=1 order by id desc")
	List<Comment> getAllCommentByBlogId(Long blogId);

	@Query("select f from Comment as f order by id desc")
	List<Comment> getAllCommentOrderById();

	@Modifying
	@Query("delete from Comment as f where f.blog.id = ?1")
	void deleteAllCommentWithBlog(Long blogId);

}
