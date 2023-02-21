# Login

<!--toc:start-->
- [Login](#login)
  - [json IO](#json-io)
  - [Login Window GUI](#login-window-gui)
<!--toc:end-->

> 相关类：[theUser](/documents/theUser.md)

本模块由两部分组成

1. json IO
2. Login Window GUI

## json IO

在配置文件users.json中保存用户信息

每次初始化GUI之前，将users.json读取为theUser[] usres数组，以供登陆界面验证用户名/密码使用。

---

`private theUser[] readUsers()`

从resources/users.json中读取users，返回users[]

@return theUser[]

---

`private void addUsers()`

将一组特定的users写入resources/users.json中

## Login Window GUI

使用swing框架，创建一个窗口，包含以下元素：

- 用户名输入框
- 密码输入框
- 确认按钮
- 显示登陆是否成功的Label（文字）

点击确认按钮后，程序遍历users数组，如果查询到了，则登陆成功，否则登陆失败。

如果登陆成功，程序可以判断登陆的用户是否是管理员。如果是管理员，则接下来具有更高的操作权限。

---

`private void initWindow()`

创建登陆界面
