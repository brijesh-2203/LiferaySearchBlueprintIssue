package com.demo.portlet;

import java.beans.ExceptionListener;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.List;
import java.util.Locale;
import java.util.Set;
public interface SXPParameterContributor {
    public void contribute(
            ExceptionListener exceptionListener, SearchContext searchContext,
            SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters);

    public String getSXPParameterCategoryNameKey();

    public List<SXPParameterContributorDefinition>
    getSXPParameterContributorDefinitions(long companyId, Locale locale);
}
