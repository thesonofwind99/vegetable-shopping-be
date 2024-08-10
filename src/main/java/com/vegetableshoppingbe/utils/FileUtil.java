package com.vegetableshoppingbe.utils;

import com.vegetableshoppingbe.exception.SystemErrorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class FileUtil {

    public File convertMultiPartToFile(final MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new SystemErrorException("Multipart file is null");
        }
        String currentDir = System.getProperty("user.dir");

        String fileName = multipartFile.getOriginalFilename();
        Path path = Paths.get(currentDir, fileName);
        File file = path.toFile();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new SystemErrorException("Error converting multipart file to file");
        }
        return file;
    }
}
