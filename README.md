<div align="center">
    <p align="center">
        <img src="kiteehub-admin-web/public/img/logo.png" height="150" alt="logo"/>
    </p>
</div>

## 快速启动

全栈工程师推荐idea

### 前端支撑
| 插件 | 版本   | 用途 |
|--- | ----- | ----- |
| node.js | ≥16 |  JavaScript运行环境 |

### 启动前端

```
npm install
```
```
npm run dev
```
### 后端支撑
| 插件 | 版本 | 用途 |
| --- | ----- |  ----- |
| jdk | 11 / 1.8 |java环境 |
| lombok | idea内 |代码简化插件 |
| maven | 最新版 |包管理工具 |
| redis | 最新版 | 缓存库 |
| mysql | 8.0 / 5.7 | 数据库 |

### 启动后端
开发工具内配置好maven并在代码中配置数据库即可启动

## 代码结构

KiteeHub2.0框架对代码以插件化的模式进行分包，使得包层级结构更加清晰合理，同时降低了耦合度，关于插件模块化开发的规范请查阅文档
```
KiteeHub
  |-kiteehub-admin-web == 前端
    |-public == 基础静态文件
    |-src == 前端源代码
      |-api == API接口转发
      |-assets == 静态文件
      |-components == VUE组件
      |-config == 基础配置
      |-layout == 基础布局
      |-locales == 多语言配置
      |-router == 基础路由配置
      |-store == Pinia缓存配置
      |-style == 样式风格配置
      |-utils == 工具类
      |-views == 所有视图界面
  |-kiteehub-common == 基础通用模块
  |-kiteehub-plugin == 插件包
    |-kiteehub-plugin-auth == 登录鉴权插件
    |-kiteehub-plugin-biz == 业务功能插件
    |-kiteehub-plugin-client == C端功能插件
    |-kiteehub-plugin-dev == 开发工具插件
    |-kiteehub-plugin-gen == 代码生成插件
    |-kiteehub-plugin-mobile == 移动端管理插件
    |-kiteehub-plugin-sys == 系统功能插件
  |-kiteehub-plugin-api == 插件api包
    |-kiteehub-plugin-auth-api == 登录鉴权插件api接口
    |-kiteehub-plugin-biz-api == 业务功能插件api接口
    |-kiteehub-plugin-client-api == C端功能插件api接口
    |-kiteehub-plugin-dev-api == 开发工具插件api接口
    |-kiteehub-plugin-gen == 代码生成插件api接口
    |-kiteehub-plugin-mobile == 移动端管理插件api接口
    |-kiteehub-plugin-sys-api == 系统功能插件api接口
  |-kiteehub-web-app == 主启动模块
```
