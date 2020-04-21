package soulOfSkin.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import soulOfSkin.models.Blog;
import soulOfSkin.models.Image;
import soulOfSkin.repository.BlogRepository;
import soulOfSkin.repository.ImageRepository;

@Service
@Transactional
public class BlogServiceImp implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ImageRepository ImgRep;

	@Autowired
	private ServletContext context;

	@Autowired
	private UploadPathService uploadService;

	@Override
	public String saveBlog(Blog blog) {
		if (blog != null && blog.getImageList() != null && blog.getImageList().size() > 0) {
			blog.setSaved(new Date());
			blogRepository.save(blog);
			for (MultipartFile file : blog.getImageList()) {
				if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
					String fileOrgName = file.getOriginalFilename();
					String modifiedFileName = FilenameUtils.getBaseName(fileOrgName) + "," + System.currentTimeMillis()
							+ "," + FilenameUtils.getExtension(fileOrgName);
					File storeFile = uploadService.getFilePath(modifiedFileName, "images");
					if (storeFile != null) {
						try {
							FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					Image image = new Image();
					image.setFileExtension(FilenameUtils.getExtension(fileOrgName));
					image.setFileName(fileOrgName);
					image.setModifiedFileName(modifiedFileName);
					image.setBlog(blog);
					ImgRep.save(image);
				}
			}
		}
		ImgRep.deleteImagesFromDB(blog.getId(), deleteImagesByte(blog));
		return "success";

	}

	@Override
	public List<Blog> getAllBlogs() {

		List<Blog> listOfBlog = new ArrayList<>();
		List<Blog> allBlogs = blogRepository.findAllBlogs();
		for (Blog blog : allBlogs) {
			if (blog.getPublished() != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date date = blog.getPublished();
				String viewDate = dateFormat.format(date);
				blog.setViewDate(viewDate);

			} else {
				blog.setViewDate("00.00.0000");
				;
			}

			listOfBlog.add(blog);
		}

		return listOfBlog;

	}

	@Override
	public Blog getBlog(Long blogId) {
		return blogRepository.findById(blogId).get();

	}

	@Override
	public boolean deleteBlogById(Long blogId) {
		List<Image> images = ImgRep.findAllById(blogId);
		List<String> delList = new ArrayList<>();
		for (Image image : images) {
			String name = image.getModifiedFileName();
			delList.add(name);
		}
		Blog blog = new Blog();
		blog.setRemoveImages(delList);

		deleteAllImagesByte(blog);
		ImgRep.deleteAllImagesByBlog(blogId);
		blogRepository.deleteById(blogId);

		return true;
	}

	// --------------METHOD FOR DELETE IMAGES FROM SERVER---------

	public String[] deleteImagesByte(Blog blog) {
		String[] arrayForDelete = new String[10];
		if (blog != null && blog.getRemoveImages() != null && blog.getRemoveImages().size() > 0) {
			// Read array string from JavaScript array and parse...
			String nameFromImage = "";
			int posImgIn = 0;
			for (String nameImgFromJS : blog.getRemoveImages()) {
				if (nameImgFromJS.equals("jpg") || nameImgFromJS.equals("JPG") || nameImgFromJS.equals("jpeg")
						|| nameImgFromJS.equals("JPEG") || nameImgFromJS.equals("png") || nameImgFromJS.equals("PNG")
						|| nameImgFromJS.equals("jfif") || nameImgFromJS.equals("JFIF")) {
					nameFromImage = nameFromImage + nameImgFromJS;
					arrayForDelete[posImgIn] = nameFromImage;
					posImgIn++;
					nameFromImage = "";
				} else {
					nameFromImage = nameFromImage + nameImgFromJS + ",";
				}
			}
			// checking in file and delete file
			for (String fileForDelete : arrayForDelete) {
				File dbFile = new File(context.getRealPath("/images/" + fileForDelete));
				if (dbFile.exists()) {
					dbFile.delete();
				}
			}
		}
		return arrayForDelete;
	}

	public void deleteAllImagesByte(Blog blog) {
		if (blog != null && blog.getRemoveImages() != null && blog.getRemoveImages().size() > 0) {
			for (String delete : blog.getRemoveImages()) {
				File dbFile = new File(context.getRealPath("/images/" + delete));
				if (dbFile.exists()) {
					dbFile.delete();
				}
			}
		}
	}
	// ----------------END DELETE METHOD---------------

	@Override
	public List<Blog> getAllBlogsForSkin() {
		List<Blog> listBlogForSkin = new ArrayList<>();
		List<Blog> listBlog = blogRepository.getAllBlogsForSkin();
		int i = 3;
		for (Blog blog : listBlog) {
			if ((i % 2) != 0) {
				blog.setSide("rechts");
				i++;
			} else {
				blog.setSide("links");
				i++;
			}
			listBlogForSkin.add(blog);
		}
		return listBlogForSkin;
	}

	@Override
	public List<Blog> getAllBlogsForLife() {
		List<Blog> listBlogForLife = new ArrayList<>();
		List<Blog> listBlog = blogRepository.getAllBlogsForLife();
		int i = 3;
		for (Blog blog : listBlog) {
			if ((i % 2) != 0) {
				blog.setSide("rechts");
				i++;
			} else {
				blog.setSide("links");
				i++;
			}
			listBlogForLife.add(blog);
		}
		return listBlogForLife;
	}

}
