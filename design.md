APC
==

2017/5/10
--

今天主要查看了关于api参数校验的相关方案,大致有如下方案:
* 注解方式
    * 基于spring aop的参数校验,将参数转化成了java bean的形式从而进行校验
    * 基于spring mvc的参数校验,同样是将表单参数和java bean绑定从而校验
* json解释
    * 好像没有,一般都是 基于json的api描述信息 从而生成api文档,并没有 基于json的api描述信息 的参数校验工具.

思考:
目前基于注解的api叙述方式有一些侵入性,所以是否可以使用基于json的api定义方式,同时也可以解析json从而根据json来生成api文档.这也是一个思路.
此时的api json文件是作为类似于配置文件放于项目目录下.

当前主要功能模块
* api信息获取
* api参数校验
    * 参数是否确实
    * 参数是否符合正则
* 错误处理模块


2017/5/11
--

又思考了下关于api 参数校验方面的细节:(可以再ApcCinfig中配置变量进行控制)
1. 对于配置文件中没有列出的 apiParam 如果在request中有了,应该禁止其访问
2. 一般对于http参数值 就是 用正则进行控制

apc运行流程
1. filter截取请求并且并且将以下信息交给apc-core
    * httpServletRequest
2. apc-core拿到请求后,开始针对存储好的api配置进行检验,主要有以下几种检验方式:
    * 检验requestPath是否存在
    * 检验各个参数
        * 是否required
        * 参数值是否符合'正则表达式'
3. 将检查结果传回给filter
    * 通过则继续调用相关chain
    * 未通过则使用handlerError方法进行处理.


2017/6/28
--

依照5大workflow的顺序,一步一步,最近几天完成对apc的需求,分析,设计,实现,demo测试编写

**requirement workflow**

本工具主要是为了在同一层面上对api的参数进行校验,并且对一系列错误统一采用一系列相应方法进行处理.

**analyse workflow**

* api解析
    * 基于注解的方式对包内的所有class进行遍历,提取方法上的注解信息,并且将api对象化进行存储
    * 确定定义api的方式,api的各个属性,从而创建Api类
* api存储
    * 存储解析好的api实例,简单的key-value键值对
* api验证
    * 此处需要一个专门的validator,来进行request和相应api之间的验证


2017/6/29
---

开发实现的时候发现了一个问题,注解只能被使用一次,所以原先设想的多ApcHttpParam注解实现多参数检验行不通了
于是现阶段准备在注解内设置json式Param说明用来解析api的参数.
