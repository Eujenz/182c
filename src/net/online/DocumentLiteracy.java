package net.online;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DocumentLiteracy {
  private final Logger log = LoggerFactory.getLogger(DocumentLiteracy.class);
  
  public abstract String getContentWrite();
  
  public abstract String getUrlWrite();
  
  public void Write() {
    File file = null;
    FileWriter fw = null;
    BufferedWriter bw = null;
    try {
      file = new File(getUrlWrite());
      if (!file.exists())
        file.createNewFile(); 
      fw = new FileWriter(file.getAbsoluteFile());
      bw = new BufferedWriter(fw);
      bw.write(getContentWrite());
    } catch (IOException e) {
      this.log.error(e.getLocalizedMessage(), e);
    } catch (Exception e) {
      this.log.error(e.getLocalizedMessage(), e);
    } finally {
      try {
        bw.close();
        fw.close();
      } catch (IOException e) {
        this.log.error(e.getLocalizedMessage(), e);
      } 
    } 
  }
}
