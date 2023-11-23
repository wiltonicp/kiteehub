/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
package com.kiteehub.common.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import com.kiteehub.common.util.CommonCryptogramUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sm4Cbc加解密
 *
 * @author wanglei
 * @date 2022/9/30 15:24
 **/
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CommonSm4CbcTypeHandler<T> extends BaseTypeHandler<T> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, CommonCryptogramUtil.doSm4CbcEncrypt((String)parameter));
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        //有一些可能是空字符
        return StringUtils.isBlank(columnValue) ? (T)columnValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(columnValue);
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) ? (T)columnValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(columnValue);
    }

    @SuppressWarnings("ALL")
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return StringUtils.isBlank(columnValue) ? (T)columnValue : (T)CommonCryptogramUtil.doSm4CbcDecrypt(columnValue);
    }
}
