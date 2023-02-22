# LoginController

> 相关类：[Login](/documents/Login.md)

## 响应 http 请求

首先新建一个[Login](/documents/Login.md)实例。

对所有来自`/login`的POST请求（具有id和password两个参数，默认值均为空字符串），
判断是否是用户。

HTTP Response 是一个 [UserRecord](/documents/UserRecord.md) 实例，包含以下内容：
- `boolean isValid` 用户名密码是否正确
- `boolean isAdmin` 是否是管理员（若`isValid`为`false`则`isAdmin`也为`false`）
