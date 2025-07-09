package org.aiboot.constant;

/**
 * <p>会话常量</p>
 *
 * @author Hullson
 * @date 2025-06-27
 */
public class ChatConstant {
    /**
     * 消息类型
     * 1-请求
     * 2-响应
     */
    public static String MESSAGE_REQUEST = "request";
    public static String MESSAGE_RESPONSE = "response";

    /**
     * 消息信号
     * normal-正常生成
     * terminate-中断生成
     */
    public static String MESSAGE_SIGNAL_NORMAL = "normal";
    public static String MESSAGE_SIGNAL_TERMINATE = "terminate";

    /**
     * <p>消息角色</p>
     * <p>USER：用户角色</p>
     * <p>SYSTEM：系统角色（用于指定AI的身份角色）</p>
     * <p>ASSISTANT：AI助力角色</p>
     */
    public static String MESSAGE_ROLE_USER = "USER";
    public static String MESSAGE_ROLE_SYSTEM = "SYSTEM";
    public static String MESSAGE_ROLE_ASSISTANT = "ASSISTANT";

    /**
     * <p>对话结束状态</p>
     * <p>TERMINATE：用户终止</p>
     * <p>FINISH：自然结束</p>
     */
    public static String CHAT_END_STATUS_TERMINATE = "TERMINATE";
    public static String CHAT_END_STATUS_FINISH = "FINISH";

    /**
     * <p>对话流式输出类型</p>
     * <p>ready：就绪信号</p>
     * <p>update_session：窗口更新时间</p>
     * <p>reasoning_message：深度思考消息</p>
     * <p>message：普通消息</p>
     * <p>finish：结束信号</p>
     * <p>end_status：结束状态</p>
     */
    public static String MESSAGE_TYPE_READY = "ready";
    public static String MESSAGE_TYPE_UPDATE_SESSION = "update_session";
    public static String MESSAGE_TYPE_REASONING_MESSAGE = "reasoning_message";
    public static String MESSAGE_TYPE_MESSAGE = "message";
    public static String MESSAGE_TYPE_FINISH = "finish";
    public static String MESSAGE_TYPE_END_STATUS = "end_status";



}
