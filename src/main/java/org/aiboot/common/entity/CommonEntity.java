package org.aiboot.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.aiboot.constant.SystemConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>不带ID的通用实体类</p>
 *
 * @author Hullson
 * @date 2025-06-26
 */
@Data
@Accessors(chain = true)
public class CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;  // 初始版本

    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private Integer delFlag;
    private String remarks;

    private String userId = "0b48216f156d46219c1c11b3a7de41c3";


    public CommonEntity() {}

    public void preInsert() {
        if (this.getDelFlag() == null) {
            this.setDelFlag(SystemConstant.DEL_FLAG_NORMAL);
        }
        this.createBy = userId;
        this.updateBy = userId;
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    public void preUpdate() {
        this.updateBy = userId;
        this.updateDate = new Date();
    }
}
