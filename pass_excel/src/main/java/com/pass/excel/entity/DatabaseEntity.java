package com.pass.excel.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuanzhonglin
 * @date 2019/3/14
 * @Description:
 */
@Setter
@Getter
public class DatabaseEntity {
    private String table_name;
    private String column_name;
    private String column_type;
    private String data_type;
    private String character_maximum_length;
    private String is_nullable;
    private String column_default;
    private String column_comment;
}
