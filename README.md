# code-generator-mp
## 使用说明
1. 根据实际情况修改 `conf/generator.properties` 文件
2. windows 系统执行 `start.bat`,  mac 或者 linux 系统执行 `start.sh`
3. 日志文件存放在 `logs/code-generator-mp.log`

## 配置文件参数说明
- dataSourceType: 数据源类型, 目前支持 mysql, postgresql

- jdbcUrl：jdbcUrl 连接

  - mysql
    `jdbc:mysql://host:port/database?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai`

  - postgresql

     `jdbc:postgresql://host:port/database?currentSchema=public`

     currentSchema 必须指定

- username：用户名

- password：密码

- tablePrefix: 表名前缀, 生成相应文件时, 会移除指定的前缀

- packageName: 包名

- author：代码作者

- projectDir：生成的代码文件存放的位置，一般指定项目的根路径

