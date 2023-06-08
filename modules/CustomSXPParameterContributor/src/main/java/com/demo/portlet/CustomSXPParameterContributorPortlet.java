package com.demo.portlet;

import com.demo.constants.CustomSXPParameterContributorPortletKeys;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinitionProvider;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.service.SXPElementLocalServiceUtil;
import com.liferay.search.experiences.service.SXPElementLocalServiceWrapper;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import java.beans.ExceptionListener;
import java.util.*;


/**
 * @author root
 */

//@Component(
//		immediate = true,
//		property = {
//				"indexer.class.name=com.liferay.portal.search.internal.searcher.SearchRequestSearcher"
//		},
//		service = SearchContextContributor.class
//)
//@Component(
//		enabled = false,
//		service = {
//				SXPParameterContributorDefinition.class,
//		}
//)
//@Component(
//        immediate = true,
//        property = {
//                "indexer.class.name=com.liferay.journal.model.JournalArticle"
//        },
//        service = SXPParameterContributor.class
//)

public class CustomSXPParameterContributorPortlet implements SXPParameterContributor {


//	@Override
//	public void contribute(SearchContext searchContext, SearchContextContributorHelper searchContextContributorHelper) {
//		searchContext.setAttribute("Brijesh", "Brijesh Rajput");
//	}

//	@Override
//	public void contribute(ExceptionListener exceptionListener, SearchContext searchContext, SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {
//		System.out.println("contrubute");
//	}

//	@Override
//	public String getSXPParameterCategoryNameKey() {
//		System.out.println("getSXPParameterCategoryNameKey");
//		return null;
//	}

	@Override
	public void contribute(ExceptionListener exceptionListener, SearchContext searchContext, SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {
		System.out.println("contri");
		try {
			_contribute(searchContext, sxpParameters);
		}
		catch (PortalException portalException) {
			exceptionListener.exceptionThrown(portalException);

		}
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		System.out.println("getSXPParameterContributorDefinitions name");

		return "custom";
	}

	@Override
	public List<SXPParameterContributorDefinition> getSXPParameterContributorDefinitions(long companyId, Locale locale) {
		System.out.println("getSXPParameterContributorDefinitions");
		List<SXPParameterContributorDefinition> sxpParameterContributorDefinitions = new ArrayList<>();
		sxpParameterContributorDefinitions.add(
				new SXPParameterContributorDefinition(
						CustomSXPParameterContributorPortlet.class,
						"custom data",
						"custom predifined variables"));
		return null;
	}

	private void _contribute(
			SearchContext searchContext, Set<SXPParameter> sxpParameters)
			throws PortalException {


		sxpParameters.add(new SXPParameter() {
			@Override
			public boolean evaluateContains(Object value) {
				return false;
			}

			@Override
			public boolean evaluateEquals(Object object) {
				return false;
			}

			@Override
			public boolean evaluateEquals(String format, Object object) {
				return false;
			}

			@Override
			public boolean evaluateIn(Object value) {
				return false;
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
				return "custom";
			}

			@Override
			public String getTemplateVariable() {
				if (isTemplateVariable()) {
					return "${custom}";
				}

				return null;
			}

			@Override
			public Object getValue() {
				return null;
			}

			@Override
			public boolean isTemplateVariable() {
				return true;
			}
		});

	}

//	public CustomSXPParameterContributorPortlet(
//			GroupLocalService groupLocalService, Language language) {
//
//		_groupLocalService = groupLocalService;
//		_language = language;
//	}
//	@Override
//	public void contribute(ExceptionListener exceptionListener, SearchContext searchContext, SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {
//		long[] commerceAccountGroupIds = (long[])searchContext.getAttribute(
//				"commerceAccountGroupIds");
//
//
//
//		Long commerceChannelGroupId = (Long)searchContext.getAttribute(
//				"commerceChannelGroupId");
//
//
//
//		Locale locale = searchContext.getLocale();
//
//		sxpParameters.add(
//				new StringSXPParameter(
//						"context.language", true, locale.getLanguage()));
//		sxpParameters.add(
//				new StringSXPParameter(
//						"context.language_id", true, _language.getLanguageId(locale)));
//
//		Layout layout = searchContext.getLayout();
//
//		if (layout != null) {
//			sxpParameters.add(
//					new StringSXPParameter(
//							"context.layout-name-localized", true,
//							layout.getName(locale, true)));
//
//		}
//
//		long scopeGroupId = GetterUtil.getLong(
//				searchContext.getAttribute("search.experiences.scope.group.id"));
//
//	}
//
//	@Override
//	public String getSXPParameterCategoryNameKey() {
//		return "context";
//	}
//
//	@Override
//	public List<SXPParameterContributorDefinition> getSXPParameterContributorDefinitions(long companyId, Locale locale) {
//		return Arrays.asList(
//				new SXPParameterContributorDefinition(
//						StringSXPParameter.class, "brijesh", "Brijesh Rajput"));
//	}

//	private final GroupLocalService _groupLocalService;
//	private final Language _language;
}