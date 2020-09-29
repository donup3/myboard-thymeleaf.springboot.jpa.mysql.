package com.dong.board.controller;

import com.dong.board.dto.AttachFileDto;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.coyote.Response;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
public class UploadController {
    @PostMapping("/uploadAjaxAction")
    @ResponseBody
    public ResponseEntity<List<AttachFileDto>> uploadAjaxPost(MultipartFile[] uploadFile) {
        String uploadFolder = "C:\\upload";
        String uploadFolderPath = getFolder();
        File uploadPath = new File(uploadFolder, uploadFolderPath);
        List<AttachFileDto> list = new ArrayList<>();
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        for (MultipartFile multipartFile : uploadFile) {
            AttachFileDto attachFileDto = new AttachFileDto();
            log.info("Upload File Name: " + multipartFile.getOriginalFilename());
            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);//IE 일때
            attachFileDto.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();
            uploadFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);
                attachFileDto.setUuid(uuid.toString());
                attachFileDto.setUploadPath(uploadFolderPath);
                if (checkImageType(saveFile)) {
                    attachFileDto.setImage(true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                    Thumbnailator.createThumbnail(new FileInputStream(new File(uploadPath, uploadFileName)), thumbnail, 100, 100);
                    thumbnail.close();
                }
                list.add(attachFileDto);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        File file = new File("C:\\upload\\" + fileName);
        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
        Resource resource = new FileSystemResource("C:\\upload\\" + fileName);
        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String resourceName = resource.getFilename();
        String resourceOriginalName = resourceName.substring(resourceName.indexOf("-") + 1);
        HttpHeaders headers = new HttpHeaders();

        try {
            String downloadName = null;
            if (userAgent.contains("Trident")) {
                downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
            } else if (userAgent.contains("Edge")) {
                downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
            } else {
                downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
            }
            headers.add("Content-Disposition", "attachment; filename=" + downloadName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName,String type){
        log.info("deleteFile: "+fileName);
        File file;
        file=new File("C:\\upload\\"+ URLDecoder.decode(fileName, StandardCharsets.UTF_8));
        file.delete();
        if(type.equals("image")){
            String largeFileName=file.getAbsolutePath().replace("s_","");
            log.info("largeFileName: "+largeFileName);
            file=new File(largeFileName);
            file.delete();
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
