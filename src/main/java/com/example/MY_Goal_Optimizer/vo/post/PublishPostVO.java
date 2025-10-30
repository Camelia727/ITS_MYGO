package com.example.MY_Goal_Optimizer.vo.post;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * PublishPostVO
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/29 下午10:17
 */
@Data
public class PublishPostVO {
    private String userId;
    private String goalId;

    @Length(max = 20, message = "标题长度不能超过20个字符")
    private String title;

    @Length(max = 2000, message = "内容长度不能超过2000个字符")
    private String content;
}
