package cn.exrick.xboot.common.exception;

import cn.exrick.xboot.common.enums.ErrorEnum;
import lombok.Data;

/**
 *
 * @author 司马缸砸缸了
 * @date 2019-07-12
 * @description error类型枚举类
 */

@Data
public class YYException extends RuntimeException {
    private String msg;
    private int code = 500;

    public YYException() {
        super(ErrorEnum.UNKNOWN.getMsg());
        this.msg = ErrorEnum.UNKNOWN.getMsg();
    }

    public YYException(ErrorEnum eEnum, Throwable e) {
        super(eEnum.getMsg(), e);
        this.msg = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public YYException(ErrorEnum eEnum) {
        this.msg = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public YYException(String exception) {
        this.msg = exception;
    }

    public YYException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

}
