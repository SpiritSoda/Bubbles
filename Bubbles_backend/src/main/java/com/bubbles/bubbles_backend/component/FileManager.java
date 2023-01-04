package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.config.ResourceConfig;
import com.bubbles.bubbles_backend.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

@Component
@Slf4j
public class FileManager {
    public void storeFile(String folder, MultipartFile file, long timestamp) throws Exception{
        String fileRoot = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "static/" + folder;
        File f = new File(fileRoot);
        if (!f.exists()) {
            f.mkdirs();
        }
        String filename = timestamp + "." + file.getOriginalFilename();
        file.transferTo(new File(f, filename));
    }
    public void storeImage(MultipartFile file, long timestamp) throws Exception{
        String imageRoot = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "static/images";
//        String imageRoot = System.getProperty("user.dir") + "/";
//        log.info(imageRoot);
        File f = new File(imageRoot);
        if (!f.exists()) {
            f.mkdirs();
        }
        String filename = timestamp + "." + file.getOriginalFilename();
        file.transferTo(new File(f, filename));
    }

}
