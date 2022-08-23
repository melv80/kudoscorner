package com.kudos.server.components;

import com.kudos.server.config.AppConfig;
import com.kudos.server.model.jpa.Image;
import com.kudos.server.model.jpa.KudosType;
import com.kudos.server.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ImageServiceImpl implements ImageService{

  private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);


  private final AppConfig appConfig;
  private final ImageRepository imageRepository;


  public ImageServiceImpl(@Autowired AppConfig config, @Autowired ImageRepository imageRepository) {
    this.appConfig = config;
    this.imageRepository = imageRepository;
  }

  @PostConstruct
  public void insertIntoDatabase() {
    List<Image> currentImages = imageRepository.findAll();
    Map<String, Image> toInsert = scanDirectories();
    for (Image currentImage : currentImages) {
      toInsert.remove(currentImage.pathOnDisk);
    }

    if (toInsert.isEmpty()) {
      logger.info("no *new* images found");
    }
    else {
      logger.info("inserting new images into database: " + toInsert.size());
    }
    imageRepository.saveAll(toInsert.values());
    imageRepository.flush();
  }


  // TODO: 25.07.2020 to slow
  @Nullable
  public Image pickRandomImage(KudosType type) {
    final List<Image> all = imageRepository.findAll().stream().filter(image -> image.type == type).collect(Collectors.toList());
    if (all.isEmpty()) {
      if (type == KudosType.THANK_YOU)
        return null;
      else
       return pickRandomImage(KudosType.THANK_YOU);
    }
    return all.get(new Random().nextInt(all.size()));
  }

  public Map<String, Image> scanDirectories() {
    long start = System.currentTimeMillis();
    logger.info("searching for new images in " + appConfig.getBaseDir() + " ...");
    if (!Files.isReadable(appConfig.getBaseDir())) {
      logger.error("can not read :" + appConfig.getBaseDir() + " ...");

      return Collections.emptyMap();
    }

    Map<String, Image> images = new HashMap<>();
    try {
      Files.walkFileTree(appConfig.getBaseDir(), new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          final Image imageFromPath = createImageFromPath(file.normalize());
          if (imageFromPath != null) {
            logger.trace("discovered image: " + imageFromPath);
            images.put(imageFromPath.pathOnDisk, imageFromPath);
          }
          else
            logger.warn("could not access file: "+file);

          return super.visitFile(file, attrs);
        }
      });
      logger.info(String.format("base directory scanned, %s new images, took: %s ms", images.size(), System.currentTimeMillis()-start));
    } catch (IOException e) {
      logger.warn("exception during directory scan: ", e);
    }
    return images;
  }

  public Image createImageFromPath(Path file) {
    final Path relativePath = appConfig.getBaseDir().relativize(file);
    Image image = new Image();
    image.pathOnDisk = relativePath.toString();
    try {
      image.size = Files.size(file);
      image.name = file.getFileName().toString();
      BufferedImage bufferedImage = ImageIO.read(file.toFile());
      image.width  = bufferedImage.getWidth();
      image.height = bufferedImage.getHeight();
      image.type   = KudosType.getTypeFrom(relativePath.getName(0).toString());
    } catch (IOException e) {
      return null;
    }
    return image;

  }

}
