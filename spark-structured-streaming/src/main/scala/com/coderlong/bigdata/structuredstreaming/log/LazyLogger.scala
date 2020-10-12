package com.coderlong.bigdata.structuredstreaming.log

import org.apache.log4j.LogManager

trait LazyLogger {
  @transient lazy val log = LogManager.getLogger(getClass)
}
