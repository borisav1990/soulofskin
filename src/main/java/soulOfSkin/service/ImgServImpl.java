package soulOfSkin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulOfSkin.models.Image;
import soulOfSkin.repository.ImageRepository;

@Service
@Transactional
public class ImgServImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Image> getImageByBlog(Long blogId) {
		return imageRepository.findAllById(blogId);

	}

	@Override
	public List<Image> getAllImages() {
		return (List<Image>) imageRepository.findAll();
	}

}
