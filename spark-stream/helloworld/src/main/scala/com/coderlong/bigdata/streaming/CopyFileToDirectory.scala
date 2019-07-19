package com.coderlong.bigdata.streaming

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.UUID


object CopyFileToDirectory {
  def main(args: Array[String]): Unit = {
    while (true){
      Thread.sleep(5000)
      val uuid = UUID.randomUUID().toString;
      copyFile(new File("./data/copyFileWord"), new File("./data/streamingCopyFile" + uuid + "------words.txt"))
    }
  }

  def copyFile(from:File, to: File)={
    val ins = new FileInputStream(from)
    val out = new FileOutputStream(to)

    val buffer = new Array[Byte](1024*1024)
    var size = 0
    while(size != -1){
      out.write(buffer, 0, buffer.length)
      size = ins.read(buffer)
    }
    ins.close()
    out.close()
  }
}
