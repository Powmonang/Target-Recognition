package com.defectscan.tools;

import com.defectscan.entity.CustomMultipartFile;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
public class FileTool {

    // 支持的压缩文件类型
    private static final String[] COMPRESSED_EXTENSIONS = {"zip", "rar", "7z"};
    // 支持的图片类型
    private static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg", "png"};

    public boolean isCompressedFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        for (String ext : COMPRESSED_EXTENSIONS) {
            if (lowerCaseFileName.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImageFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        for (String ext : IMAGE_EXTENSIONS) {
            if (lowerCaseFileName.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    public List<MultipartFile> processCompressedFile(MultipartFile file) {
        List<MultipartFile> images = new ArrayList<>();
        String fileExtension = getFileExtension(file.getOriginalFilename());
        switch (fileExtension) {
            case "zip":
                try {
                    images.addAll(extractImagesFromZip(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "rar":
                try {
                    images.addAll(extractImagesFromRar(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "7z":
                try {
                    images.addAll(extractImagesFrom7z(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return images;
    }

    private List<MultipartFile> extractImagesFromZip(MultipartFile zipFile) throws IOException {
        List<MultipartFile> images = new ArrayList<>();
        File tempZip = File.createTempFile("temp", ".zip");
        try (FileOutputStream fos = new FileOutputStream(tempZip)) {
            fos.write(zipFile.getBytes());
        }

        try (ZipFile zip = new ZipFile(tempZip)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && isImageFile(entry.getName())) {
                    try (InputStream inputStream = zip.getInputStream(entry)) {
                        String fileName = getFileNameWithoutPath(entry.getName());
                        MultipartFile multipartFile = new CustomMultipartFile(fileName, inputStream);
                        images.add(multipartFile);
                    }
                }
            }
        } finally {
            tempZip.delete();
        }
        return images;
    }

    private List<MultipartFile> extractImagesFromRar(MultipartFile rarFile) throws IOException {
        List<MultipartFile> images = new ArrayList<>();
        File tempRar = File.createTempFile("temp", ".rar");
        try (FileOutputStream fos = new FileOutputStream(tempRar)) {
            fos.write(rarFile.getBytes());
        }

        try (Archive archive = new Archive(tempRar)) {
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                if (!fileHeader.isDirectory() && isImageFile(fileHeader.getFileNameString())) {
                    try (InputStream inputStream = archive.getInputStream(fileHeader)) {
                        String fileName = getFileNameWithoutPath(fileHeader.getFileNameString());
                        MultipartFile multipartFile = new CustomMultipartFile(fileName, inputStream);
                        images.add(multipartFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempRar.delete();
        }
        return images;
    }

    private List<MultipartFile> extractImagesFrom7z(MultipartFile sevenZFile) throws IOException {
        List<MultipartFile> images = new ArrayList<>();
        File temp7z = File.createTempFile("temp", ".7z");
        try (FileOutputStream fos = new FileOutputStream(temp7z)) {
            fos.write(sevenZFile.getBytes());
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(temp7z, "r");
             IInArchive inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile))) {
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                if (!item.isFolder() && isImageFile(item.getPath())) {
                    final ExtractOperationResult result;
                    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                        result = item.extractSlow(data -> {
                            try {
                                bos.write(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return data.length;
                        });
                        if (result == ExtractOperationResult.OK) {
                            String fileName = getFileNameWithoutPath(item.getPath());
                            MultipartFile multipartFile = new CustomMultipartFile(fileName, new ByteArrayInputStream(bos.toByteArray()));
                            images.add(multipartFile);
                        }
                    }
                }
            }
        } catch (SevenZipException e) {
            e.printStackTrace();
        } finally {
            temp7z.delete();
        }
        return images;
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1) {
            return fileName.substring(lastIndex + 1).toLowerCase();
        }
        return "";
    }

    private String getFileNameWithoutPath(String fullPath) {
        int lastIndex = Math.max(fullPath.lastIndexOf('/'), fullPath.lastIndexOf('\\'));
        return lastIndex == -1 ? fullPath : fullPath.substring(lastIndex + 1);
    }
}