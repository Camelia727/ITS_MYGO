package com.example.MY_Goal_Optimizer.po.converter;

import com.example.MY_Goal_Optimizer.po.User;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * UserTypeConverter
 *
 * @author lsh
 * @version 1.0.0
 * @description
 * @date 2025/10/24 下午5:32
 */
@Converter
public class UserTypeConverter implements AttributeConverter<User.UserType, String> {
    @Override
    public String convertToDatabaseColumn(User.UserType attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public User.UserType convertToEntityAttribute(String dbData) {
        return dbData != null ? User.UserType.fromValue(dbData) : null;
    }
}
