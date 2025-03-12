create table imgs (
    id char(16) not null comment '图像ID',
    url text comment '图像地址',
    imageName varchar(128) default null comment '图像名称',
    place text comment '图像采集地址',
    uploadTime datetime default null comment '图像上传时间',
    uploader varchar(128) default null comment '图像上传者',
    photoTagId char(16) default null comment '图像内容类别ID',
    safetyCount char(2) default '-1' comment '隐患数量 -1：未判断 0：无隐患',
    finalModifierId char(16) default null comment '最后修改人ID',
    finalModifyTime datetime default null comment '最后修改时间',
    reportId char(32) default null comment '安检报告ID',
    mark text comment '备注',
    isOperated char(1) default '0' comment '是否被标注过0-无 1-是',
    isShown char(1) default '1' comment '是否展示 0-否 1-是',
    primary key (id),
    key idx_rptId (reportId)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像表';



create table imgsafemap (
    id int not null auto_increment comment '逻辑主键',
    imageTypeId char(16) default null comment '图像类别ID',
    safetyTypeId char(64) default null comment '隐患类别ID（安检项）',
    modelName varchar(128) default null comment '算法名称',
    remark char(128) default null comment '备注',
    primary key (id),
    key fk_modelName (modelName),
    key fk_ism_safetyTypeId (safetyTypeId),
    constraint fk_ism_safetyTypeId foreign key (safetyTypeId) references safetype (id) on update cascade,
    constraint fk_modelName foreign key (modelName) references models (name) on update cascade
) engine=InnoDB auto_increment=20 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像类别隐患检查对照表';




create table imgtype (
    id char(16) not null comment '图像类别ID',
    imageType varchar(64) default null comment '图像类别',
    content text comment '内容',
    mark text comment '备注',
    primary key (id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像类别表';



create table inferimg (
    id int not null auto_increment comment '逻辑主键',
    operatorId char(16) default null comment '判断隐患者（模型ID）',
    imageId char(16) default null comment '图像ID',
    safetyTypeId char(16) default null comment '隐患ID',
    riskLevel char(1) default null comment '隐患等级(0一般隐患/1重大隐患)',
    keyTime datetime default null comment '时间戳',
    indexUrl text comment '隐患坐标文件',
    reportId char(32) default null comment '安检报告ID',
    primary key (id)
) engine=InnoDB auto_increment=2 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像推理隐患表';


create table models (
    id int not null auto_increment comment '模型ID',
    modelName varchar(128) default null comment '名称',
    type char(64) default null,
    keyTime datetime default null comment '模型问世时间',
    mark text comment '备注',
    primary key (id),
    key idx_modelName (modelName),
    key fk_safetyTypeId (type),
    constraint fk_safetyTypeId foreign key (type) references safetype (id) on update cascade
) engine=InnoDB auto_increment=10 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '推理模型表';



create table opeimgs (
    id int not null auto_increment comment '逻辑主键',
    operId char(16) default null comment '操作者ID（账号）',
    imageId char(16) default null comment '图像ID',
    safetyType char(16) default null comment '隐患ID',
    keyTime datetime default null comment '时间戳',
    indexUrl text comment '坐标文件',
    flag varchar(32) default null comment 'FLAG无实际意义',
    primary key (id)
) engine=InnoDB auto_increment=35 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像打标表（人为/模型）';


create table safetype (
    id char(64) not null,
    type varchar(64) default null comment '安全隐患类别名称',
    content text comment '内容',
    mark text comment '备注',
    primary key (id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '隐患类型表';



create table users (
    id char(16) not null comment '账号',
    password varchar(128) not null comment '加密密码',
    userType char(1) not null default '0' comment '用户权限 0-普通 1-管理员',
    name varchar(128) default null comment '姓名',
    sex char(1) default '2' comment '0-女 1-男 2-不详',
    phone char(11) default null comment '联系电话',
    finalLogin datetime default null comment '最后登录时间',
    primary key (id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '用户表';



create table warnimgs (
    id int not null auto_increment comment '逻辑主键',
    operatorId char(16) default null comment '告警者ID（账号）',
    imageId char(16) default null comment '图像ID',
    warningType char(16) default null comment '告警ID',
    keyTime datetime default null comment '时间戳',
    flag varchar(32) default null comment 'FLAG无实际意义',
    primary key (id)
) engine=InnoDB auto_increment=5 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci comment '图像告警表(模型)';




CREATE TABLE `warntype` (
  `ID` char(16) NOT NULL COMMENT '安全告警ID',
  `TYPE` varchar(64) DEFAULT NULL COMMENT '安全告警类别名称',
  `CONTENT` text COMMENT '内容',
  `MARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='安全告警表';



