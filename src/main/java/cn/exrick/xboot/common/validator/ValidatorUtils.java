package cn.exrick.xboot.common.validator;

import com.qiniu.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 校验公共类
 * @author pf.yang
 * @date 2019-07-14
 * @description
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws Exception 校验不通过，则报YYException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws Exception {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            List<String> collect = constraintViolations.stream()
                    .map(constant -> constant.getMessage())
                    .collect(Collectors.toList());
            String msg = StringUtils.join(collect, ",");
            throw new Exception(msg);
        }
    }
}
