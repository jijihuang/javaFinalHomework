package replay;

import annotation.AuthorAnno;

import java.io.FileWriter;

@AuthorAnno
public class ReplayWriter {

    private static FileWriter fileWriter;

    public static void setFileWriter(FileWriter fileWriter) {
        ReplayWriter.fileWriter = fileWriter;
    }

    public void writeInToFile(String data) throws Exception{
        fileWriter.write(data);
    }

    public void closeFile()throws Exception{
        fileWriter.close();
    }

}
