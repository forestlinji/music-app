# XMusic音乐应用

该项目已经发布到[我的服务器](http://106.14.209.11/#/)，项目的数据库文件在/sql文件夹下

[点击查看项目前端代码](https://github.com/forestlinji/music-frontend)





## 项目技术栈

- web前端: Vue + Element UI + Axios
- 后端: SpringBoot + MybatisPlus + SpringSecurity + JWT + Redis + OSS存储

## 项目简介

music音乐应用。真的没啥好简介的hhhh，一个常规的音乐app

## 项目架构

### 总览

使用springboot的前后端分离项目，后端部分使用mysql+redis作为数据库，持久性数据存入mysql，验证码，激活码等临时数据存入redis。登录鉴权方面使用的是SpringSecurity和jwt，不再使用传统的session和cookie。

### 具体细节

#### 1.数据库结构

- 用户表
- 音乐表
- 歌单表
- 音乐-歌单表
- 角色表
- 用户-角色表
- 收藏表
- 评论表

#### 2.歌曲模块

歌曲模块提供随机推荐功能和搜索功能，可以试听音乐或加入播放列表。管理员可以上传新的音乐

#### 3.歌单模块

登录用户可以创建歌单，并且把自己喜欢的音乐加入歌单，可以对歌单进行删除或播放歌单

#### 4.评论模块

歌曲的评论功能。登录后可以对歌曲进行评论或删除自己曾经发表的评论

#### 5.收藏模块

用户可以将自己喜欢的音乐加入收藏，以便随时查看或进行播放