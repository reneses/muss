package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.domain.*;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GalleryController {

	@Autowired
	ChObjectService objectService;
	
	@RequestMapping(value="/gallery", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("chObjects", objectService.findAll());

		return "gallery";
	}

	@RequestMapping(value="/gallery/{objectID}", method = RequestMethod.GET)
	public String object(Model model, @PathVariable int objectID) {

		/*ChObject object = new ChObject();
		object.setTitle("TITLE");
		object.setCreditLine("LINE");
		object.setDate("DATE");
		object.setDescription("DESCRIPTION");
		object.setGalleryText("GALLERY");
		object.setMedium("MEDIUM");
		object.setOriginalId(1001);

		ImageSize size = new ImageSize();
		size.setHeight(100);
		size.setWidth(200);
		size.setUrl("http://www.jpl.nasa.gov/spaceimages/images/mediumsize/PIA17011_ip.jpg");
		size.setLabel("s");
		List<ImageSize> sizes = new ArrayList<>();
		sizes.add(size);

		Image image = new Image();
		image.setOriginalId(3001);
		image.setPrimary(true);
		image.setSizes(sizes);

		ImageSize size2 = new ImageSize();
		size2.setHeight(100);
		size2.setWidth(200);
		size2.setUrl("http://www.nycgovparks.org/photo_gallery/full_size/14413.jpg");
		size2.setLabel("s");
		List<ImageSize> sizes2 = new ArrayList<>();
		sizes2.add(size2);

		Image image2 = new Image();
		image2.setOriginalId(3001);
		image2.setPrimary(true);
		image2.setSizes(sizes2);


		List<Image> images = new ArrayList<>();
		images.add(image);
		images.add(image2);
		object.setImages(images);

		List<Tag> tags = new ArrayList<>();
		Tag t = new Tag();
		t.setName("PICTURE");
		tags.add(t);
		t = new Tag();
		t.setName("NEO IMPRESSIONISM");
		tags.add(t);
		object.setTags(tags);*/

		ChObject object = objectService.find(objectID);

		model.addAttribute("object", object);
		return "object";
	}
	
}
