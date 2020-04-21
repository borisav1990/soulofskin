package soulOfSkin.service;

import java.util.List;

import soulOfSkin.models.Image;

public interface ImageService {

	List<Image> getImageByBlog(Long blogId);

	List<Image> getAllImages();

}
