# 概要设计

| 小组成员：康京旸，张烨，麦麦提艾力·艾力

## 总体方案设计

系统设计为网页应用程序，用户端使用 JavaScript 的 React.js 框架搭建网页界面，服务器端使用 Java 的 SpringBoot 框架构建 API，其中业务逻辑的处理以及数据的存储等操作均在服务器端进行。

关于定时模块，现有两种方案：
1. 后端维护一个“信箱”，前端定时发送请求查询状态（通知内容）
2. 使用SSE(Server Sent Event)，后端主动向前端发送信息。

鉴于目前进度以及对于开发工具，平台，框架的熟悉情况，上面两种方案的选择待定。

## 开发环境

> 所用到的所有工具均跨平台（Windows, MacOS, Linux）
> 
- Git 版本控制
- [www.gitee.com](http://www.gitee.com) 托管仓库
- Postman 调试 API

### 客户端

- JavaScript 语言（node.js）
- npm 包管理器
- React.js 框架
- Neovim, VScode 文本编辑器

### 服务器端

- Java 语言（OpenJDK 17）
- Gradle 自动化构建工具
- SpringBoot 框架
- Neovim 文本编辑器，IntelliJ IDEA IDE

## 总体结构

每个用户（包括管理员用户）拥有四个页面，分别是

- 主页
显示控制模拟时间开始、结束的控件，显示系统通知，显示地图。
- 课程页面
显示课表
对于管理员用户而言，课表中有额外的用于添加、删除课程的控件。
所有用户的课程页面均有查询课程、显示课程的组件。
管理员可以看到所有的课程，学生只能看到自己参加的课程。
- 课外活动页面
显示日程表，类似课表
对于管理员用户而言，可以看到所有的课外活动。
对于学生用户而言，只能够查询到自己参与的课外活动。
学生界面有添加课外活动的控件。
- 临时事物页面
显示Todo-List和添加临时事物的控件。

## 模块划分

### 客户端

上文提到的页面，每个页面一个模块。

#### 前端 React 项目目录结构

- frontend/
  - public/
    > 存放图片等静态资源
  - src/
    - components/
      - `NavBar.jsx`
      - `NavBar.css`
      - `ClassTable.jsx`
      - `ClassTable.css`
      > 页面中用到的组件
    - pages/
      - `StudentPage.jsx`
      - `StudentActivities.jsx`
      - `StudentCourse.jsx`
      - `StudentOthers.jsx`
      - `AdminPage.jsx`
      - `AdminActivities.jsx`
      - `AdminCourse.jsx`
      - `AdminOthers.jsx`
      - `LoginPage.jsx`
      > 上文概要设计中所提到的不同页面
    - `index.js`
    - `index.css`
  - `package.json`
    > npm 配置文件

### 服务器端

![structure](./documents/assets/structure.png)

__API__

与客户端通信的模块，监听并处理来自前端的请求。

__业务逻辑__

被上层API模块调用，使用各种算法完成各种逻辑处理。

__数据库__

因要求，本项目不使用真正意义上的数据库，而是将数据以 json 格式储存在服务端的若干个文件中。
这里的数据库模块是指为方便使用，将文件的读写封装成的模块，供上层业务逻辑调用。

#### 后端 SpringBoot 项目目录结构
- schedule_system/
  - configurations/
    - `DataConfig.java`
    - ...
    > 每一个类中定义一些项目初始化时的@Bean
  - controllers/
    - `LoginController.java`
    - ...
    > 每一个类对应着一个提供给前端的API
  - utils/
    - `Course.java`
    - `ClassTime.java`
    - ...
    > 工具类
  - fakeDB/
    - `UserData.java`
    - ...
    > 每一个类对应着一个用于存储对应的数据的json文件，负责这个文件的读、写。
  - `App.java`
