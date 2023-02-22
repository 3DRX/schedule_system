# Login

> 相关类：[theUser](/documents/theUser.md) [LoginController](/documents/LoginController.md)

## json IO

在配置文件users.json中保存用户信息

每次创建一个新的实例时，将users.json读取为theUser[] usres数组，以供登陆界面验证用户名/密码使用。

---

`private theUser[] readUsers()`

从resources/users.json中读取users，返回users[]

@return theUser[]

---

`private void addUsers(theUser[] users)`

清空users.json，并将一组特定的users写入resources/users.json中
