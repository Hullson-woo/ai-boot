package org.aiboot.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.aiboot.constant.SystemConstant;
import org.aiboot.utils.GenerateIdUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>定义通用实体类</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class DataEntity implements Serializable {
    private static final long serialVersionUID = 1L;  // 初始版本

    private String id;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private Integer delFlag;
    private String remarks;

    public DataEntity() {}

    public DataEntity(String id) {
        this();
        this.id = id;
    }

    public void preInsert() {
        if (StringUtils.isBlank(this.getId())) {
            this.setId(GenerateIdUtil.uuidGenerator());
        }
        if (this.getDelFlag() == null) {
            this.setDelFlag(SystemConstant.DEL_FLAG_NORMAL);
        }
        this.createBy = "admin";
        this.updateBy = "admin";
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    public void preUpdate() {
        this.updateBy = "admin";
        this.updateDate = new Date();
    }

    /**
     * 重写equals方法
     * 通过对象ID判断对象是否相同
     * @param o 对比对象
     * @return  boolean
     */
    @Override
    public boolean equals(Object o) {
        /**
         * 当前实例和传入对象地址相同，表示为同一对象
         */
        if (this == o) return true;
        /**
         * 如果传入对象为空并且和不属于当前类，表示不同对象
         */
        if (o == null || getClass() != o.getClass()) return false;
        DataEntity that = (DataEntity) o;
        return null != this.getId() && Objects.equals(id, that.id);
    }

    /**
     * 重写hashCode方法，用于防止重写equals方法后判断两个对象相同时而hashCode不同
     * @return hashCode
     */
    @Override
    public int hashCode() {
        /**
         * 当当前实例id不为空时，将ID进行hash运算，保持与equals一致
         */
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return super.hashCode();
        }
    }
}
