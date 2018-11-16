package com.coderlong.bigdata.springboot.scala.controller

import com.alibaba.fastjson.JSONObject
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  *
  * @author Long Qiong 
  * @create 2018/11/8
  */
@RestController
class IndexController {
  @RequestMapping(value=Array("/index"))
  def index(): JSONObject = {
    val json = new JSONObject
    json.put("code", 0)
    json.put("data", "success")
    json
  }
}
