package com.forestj.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtil {
    public static File MultipartFileToFile(MultipartFile file) throws Exception{
        File f = null;
        try {
            InputStream is = file.getInputStream();
            f = new File(file.getOriginalFilename());

            OutputStream os = new FileOutputStream(f);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }
}
