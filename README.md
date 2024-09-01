# SingleUserMultiLoginRestrictionApplication
手写使用session实现单用户多端登录限制的方案
基本流程：

首先获得当前浏览器访问服务器的session，然后根据用户的信息（如id等）在redis中查找，如果找到，并且和查找对应的session不同，则可以判断已经有其他设备登录过了，这个时候就可以把redis中对应用户的session替换为当前的session，这个时候就代表其他设备的用户被强制下线了，当前设备成功登录。
详解地址：https://blog.csdn.net/m0_61346425/article/details/141788911?csdn_share_tail=%7B%22type%22%3A%22blog%22%2C%22rType%22%3A%22article%22%2C%22rId%22%3A%22141788911%22%2C%22source%22%3A%22m0_61346425%22%7D
