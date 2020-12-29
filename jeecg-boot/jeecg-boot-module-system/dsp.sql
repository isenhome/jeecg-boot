DROP TABLE IF EXISTS `dsp_ad_format`;
CREATE TABLE `dsp_ad_format` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '广告形式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- insert into dsp_ad_format(name) values('横幅'),('插屏'),('开屏'),('视频'),('信息流');

DROP TABLE IF EXISTS `dsp_ad_radio`;
CREATE TABLE `dsp_ad_radio` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `width` int(11) DEFAULT 0 NOT NULL COMMENT '宽度',
  `height` int(11) DEFAULT 0 NOT NULL COMMENT '高度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `dsp_platform`;
CREATE TABLE `dsp_platform` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '平台',
  `icon_url` varchar(32) DEFAULT NULL COMMENT 'icon', 
  `intro` text DEFAULT NULL COMMENT '简介', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_media`;
CREATE TABLE `dsp_media` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '媒体名称', 
  `platform_id` varchar(32) DEFAULT NULL COMMENT '平台编号', 
  `intro` text DEFAULT NULL COMMENT '简介', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_adspace`;
CREATE TABLE `dsp_adspace` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '媒体名称', 
  `platform_id` varchar(32) DEFAULT NULL COMMENT '平台编号', 
  `media_id` varchar(32) DEFAULT NULL COMMENT '媒体编号', 
  `ad_format_id` varchar(32) DEFAULT NULL COMMENT '广告形式', 
  `ad_radio_id` varchar(32) DEFAULT NULL COMMENT '广告尺寸',
  `comment` text DEFAULT NULL COMMENT '备注', 
  `sell_type` ENUM('CPM','CPC') DEFAULT 'CPM' NOT NULL COMMENT '售卖方式',
  `interaction` text DEFAULT 'link,download' NOT NULL COMMENT '交互方式',
  `image_type`text DEFAULT 'jpg,png,gif' NULL COMMENT '图片格式', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_advertiser`;
CREATE TABLE `dsp_advertiser` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '广告主', 
  `company` varchar(32) DEFAULT NULL COMMENT '公司名称', 
  `intro` text DEFAULT NULL COMMENT '简介', 
  `license` varchar(32) NOT NULL COMMENT '营业执照',
  `ICP` varchar(32) NOT NULL COMMENT 'ICP',
  `contact_name` varchar(32) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(32) DEFAULT NULL COMMENT '手机',
  `contact_mail` varchar(32) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_campaign`;
CREATE TABLE `dsp_campaign` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '媒体名称', 
  `advertiser_id` varchar(32) DEFAULT NULL COMMENT '广告主编号', 
  `industry_id` varchar(36) NOT NULL COMMENT '行业编号', 
  `start` datetime NOT NULL COMMENT '开始时间',
  `end` datetime NULL COMMENT '结束时间',
  `comment` text DEFAULT NULL COMMENT '备注',
  `limit_cost` Decimal(11,2) default 100000000.00 NOT NULL COMMENT '预算',
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_strategy`;
CREATE TABLE `dsp_strategy` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '策略名称', 
  `advertiser_id` varchar(32) DEFAULT NULL COMMENT '广告主编号', 
  `start` datetime NOT NULL COMMENT '开始时间',
  `end` datetime NULL COMMENT '结束时间',
  `comment` text DEFAULT NULL COMMENT '备注',
  `buy_type` ENUM('CPM','CPC') DEFAULT 'CPM' NOT NULL COMMENT '出价方式',
  `buy_max_bidprice` Decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '最高价格',
  `buy_min_bidprice` Decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '最低价格',
  `buy_daily_limit` Decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '消耗日限',
  `pv_limit_daily` bigint(20) DEFAULT 0 NOT NULL COMMENT '展示日限',
  `click_limit_daily` bigint(20) DEFAULT 0 NOT NULL COMMENT '点击日限',
  `excute_type` ENUM('fast','average') DEFAULT 'fast' NOT NULL COMMENT '投放速度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `dsp_creative`;
CREATE TABLE `dsp_creative` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '创意名称', 
  `advertiser_id` varchar(32) DEFAULT NULL COMMENT '广告主编号', 
  `ad_format_id` varchar(36) NOT NULL COMMENT '创意形式',
  `comment` text DEFAULT NULL COMMENT '备注',
  `interaction_type` text DEFAULT 'link' NOT NULL COMMENT '交互方式',
  `interaction_content` text DEFAULT '' NOT NULL COMMENT '交互内容',
  `pv_monitor` varchar(255) DEFAULT NULL COMMENT '展示监测', 
  `click_monitor` varchar(255) DEFAULT NULL COMMENT '点击监测', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `dsp_material`;
CREATE TABLE `dsp_material` (
  `id` varchar(36) NOT NULL, 
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人', 
  `create_time` datetime DEFAULT NULL COMMENT '创建日期', 
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人', 
  `update_time` datetime DEFAULT NULL COMMENT '更新日期', 
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门', 
  `status` tinyint(2) DEFAULT 1 COMMENT '状态 1:有效,-1:删除,',
  `name` varchar(32) DEFAULT NULL COMMENT '媒体名称', 
  `advertiser_id` varchar(36) NOT NULL COMMENT '创意形式',
  `comment` text DEFAULT NULL COMMENT '备注',
  `comment` text DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

