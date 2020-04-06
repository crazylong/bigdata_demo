模拟登录流程
客户端                                             服务端
构建登录对象-->编码成Bytebuf-->writeAndflush()----------->收到Bytebuf-->解码成登录请求-->登录验证
                                                                                       |
                                                                                       |
                                                                                       v
处理响应结果<--解码成登录响应<--收到Bytebuf<--------- writeAndflush<--编码成Bytebuf<--构建登录响应对象