package soulOfSkin.service;

import java.util.List;

import soulOfSkin.models.Blog;

public interface BlogService {

	List<Blog> getAllBlogs();

	String saveBlog(Blog blog);

	Blog getBlog(Long blogId);

	boolean deleteBlogById(Long blogId);

	List<Blog> getAllBlogsForSkin();

	List<Blog> getAllBlogsForLife();

}
