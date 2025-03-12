package com.defectscan.tools;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileTool {

    public void writeLine(String url, String msg){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(url));
            writer.write(msg+"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLines(String url, List<String> msg){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(url));
            for(String i:msg){
                writer.write(i+"\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readLines(String url){
        try {
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(url));
            String line = reader.readLine();
            while(line!=null)
            {
//                readLine() 方法从输入流中读取一行文本，并且会在遇到换行符（在UNIX和Linux系统中是\n，在Windows系统中可能是\r\n）时停止读取，并将换行符从返回的字符串中移除。
                if(!line.isEmpty())
                    lines.add(line);
                line = reader.readLine();
            }
            reader.close();
            return lines;
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在！");
            return null;
        }catch (IOException e)
        {
            System.out.println("读取错误");
            return null;
        }
    }
}
