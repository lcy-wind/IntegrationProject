package com.teamwork.integrationproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/11/03 16:23
 * 4G载频扩容分析小区天粒度表（1天）-表头
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("T_4GCARRIER_FREQUENCY_ONE_DAY")
public class CarrierFrequencyOneDay
{

    @TableField("FOD_ID")
    private Long id;
    //中文名称
    @TableField("FOD_CHINESE_NAME")
    private String chineseName;
    //地市码
    @TableField("FOD_REGION_CODE")
    private String regionCode;
    //地市名称
    @TableField("FOD_REGION_NAME")
    private String regionName;
    //厂商
    @TableField("FOD_MANUFACTURER")
    private String manufacturer;
    //cgi
    @TableField("FOD_CGI")
    private String cgi;
    //覆盖类型
    @TableField("FOD_COVER_TYPE")
    private String coverType;
    //覆盖场景
    @TableField("FOD_COVER_SCENE")
    private String coverScene;
    //小区分类
    @TableField("FOD_AREA_CLASSIFY")
    private String areaClassify;
    //连续自忙
    @TableField("FOD_CONTINUOUS")
    private String continuous;
    //是否扩容
    @TableField("FOD_ISDILATATION")
    private String isDilatation;
    //自忙时平均E-RAB流量(KB)
    @TableField("FOD_AVERAGE_FLOW_RATE")
    private String averageFlowRate;
    //链接最大数
    @TableField("FOD_RRC_CONNECT_MAXIMUM")
    private String rrcConnectMaximum;
    //数据传输的RRC
    @TableField("FOD_RRC_DATA_TRANSMISSION")
    private String rrcDataTransmission;
    //上行利用率PUSCH
    @TableField("FOD_UPLINK_UTILIZATION")
    private String upLinkUtilization;
    //下行利用率PDSCH
    @TableField("FOD_DOWNSTREAM_UTILIZATION")
    private String downstreamUtilization;
    //上行流量
    @TableField("FOD_UPSTREAM_TRAFFIC")
    private String upstreamTraffic;
    //下行流量
    @TableField("FOD_DOWNSTREAM_TRAFFIC")
    private String downstreamTraffic;
    //下行利用率PDCCH
    @TableField("FOD_DOWNSTREAM_UTILIZATIONCCH")
    private String downstreamUtilizationcch;
    //小区类型
    @TableField("FOD_AREA_TYPE")
    private String areaType;
    //高负荷类型
    @TableField("FOD_HIGH_LOAD_TYPE")
    private String highLoadType;
    //区域宽带
    @TableField("FOD_REGIONAL_BROADBAND")
    private String regionalBroadband;
    //工作频段
    @TableField("FOD_WORKING_FREQUENCY")
    private String workingFrequency;
    //中心频点
    @TableField("FOD_CENTER_FREQUENCY")
    private String centerFrequency;
    //日均流量GB
    @TableField("FOD_AVERAGE_DAILY_FLOW")
    private String averageDailyFlow;
    //覆盖场景归类(系统)
    @TableField("FOD_SCENE_CLASSIFICATION")
    private String sceneClassification;
    //细化工作频段
    @TableField("FOD_REFINE_WORKING_FREQUENCY")
    private String refineWorkingFrequency;
    //方向角
    @TableField("FOD_DIRECTION_ANGLE")
    private String directionAngle;
    //是否3D-MIMO小区
    @TableField("FOD_WHETHER_3D_MIMO_CELL")
    private String whether3dMimoCell;
    //RRU型号
    @TableField("FOD_RRU_MODEL")
    private String rruModel;
    //创建时间
    @TableField("FOD_RECORD_TIME")
    private Date recordTime;
    //数据时间
    @TableField("FOD_DATA_TIME")
    private Date dataTime;

}