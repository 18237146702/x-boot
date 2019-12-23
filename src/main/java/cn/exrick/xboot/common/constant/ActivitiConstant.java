package cn.exrick.xboot.common.constant;

/**
 * <p>
 * Activiti常量类
 * </p>
 *
 * @author 司马缸砸缸了
 * @since 2019-07-25
 */
public class ActivitiConstant {

    public static final String BPMN20 = ".bpmn20.xml";
    public static final String IMAGE = "image";
    public static final String XML = "xml";
    public static final String PNG = "png";
    public static final String BAR = "bar";
    public static final String ZIP = "zip";
    public static final String BPMN = "bpmn";

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;


    /**
     * 请假流程KEY
     */
    public static final String LEAVE_KEY = "leave";

    /**
     * 工单状态, 草稿
     */
    public static final int ORDER_STATUS_NEW = 0;
    /**
     * 工单状态, 已提交
     */
    public static final int ORDER_STATUS_SUBMIT = 1;
    /**
     * 工单状态, 已提交
     */
    public static final int ORDER_STATUS_FINISH= 2;
    /**
     * 请假流程KEY businessKey
     */
    public static final String LEAVE_BUSINESSKEY = "leave.";
}
