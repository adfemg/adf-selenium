package com.redheap.selenium.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;

public class FileOutputType implements OutputType<File> {

    private File file;

    public FileOutputType(File file) {
        this.file = file;
    }

    @Override
    public File convertFromBase64Png(String base64Png) {
        try {
            IOUtils.copy(new Base64InputStream(IOUtils.toInputStream(base64Png, "UTF-8" )), new FileOutputStream(file));
        } catch (IOException e) {
            throw new WebDriverException(e);
        }
        return file;
    }

    @Override
    public File convertFromPngBytes(byte[] png) {
        try {
            FileUtils.writeByteArrayToFile(file, png);
        } catch (IOException e) {
            throw new WebDriverException(e);
        }
        return file;
    }


    @Override
    public String toString() {
        try {
            return FileOutputType.class.getSimpleName() + "[" + file.getCanonicalPath() + "]";
        } catch (IOException e) {
            return super.toString();
        }
    }
}
