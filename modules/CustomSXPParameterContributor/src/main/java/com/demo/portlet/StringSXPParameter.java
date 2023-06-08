package com.demo.portlet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.liferay.search.experiences.blueprint.parameter.SXPParameter;

import java.util.Map;
import java.util.Objects;

public class StringSXPParameter implements SXPParameter {

    public StringSXPParameter(
            String name, boolean templateVariable, String value) {

        super();

        _value = value;
    }

    @Override
    public boolean evaluateContains(Object value) {
        if (value instanceof Object[]) {
            for (Object object : (Object[])value) {
                if (StringUtil.containsIgnoreCase(
                        _value, GetterUtil.getString(object),
                        StringPool.BLANK)) {

                    return true;
                }
            }

            return false;
        }

        return StringUtil.containsIgnoreCase(
                _value, GetterUtil.getString(value), StringPool.BLANK);
    }

    @Override
    public boolean evaluateEquals(Object object) {
        return Objects.equals(_value, GetterUtil.getString(object));
    }

    @Override
    public boolean evaluateEquals(String format, Object object) {
        return false;
    }

    @Override
    public boolean evaluateIn(Object value) {
        return ArrayUtil.contains(
                GetterUtil.getStringValues(
                        ArrayUtil.toStringArray((Object[])value)),
                _value);
    }

    @Override
    public boolean evaluateRange(Object gt, Object gte, Object lt, Object lte) {
        return false;
    }

    @Override
    public boolean evaluateRange(String format, Object gt, Object gte, Object lt, Object lte) {
        return false;
    }

    @Override
    public String evaluateToString(Map<String, String> options) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTemplateVariable() {
        return null;
    }

    @Override
    public String getValue() {
        return _value;
    }

    @Override
    public boolean isTemplateVariable() {
        return false;
    }

    private final String _value;
}
