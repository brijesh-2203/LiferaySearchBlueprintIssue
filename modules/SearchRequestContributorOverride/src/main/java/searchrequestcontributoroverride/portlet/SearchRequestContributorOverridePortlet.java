package searchrequestcontributoroverride.portlet;

import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPExceptionUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author root
 */@Component(
		enabled = false,
		property = "javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_OPTIONS,
		service = PortletSharedSearchContributor.class
)
public class SearchRequestContributorOverridePortlet implements PortletSharedSearchContributor {
	public SearchRequest contribute(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
				_searchRequestBuilderFactory.builder(searchRequest);

		_contributeSXPBlueprintId(searchRequestBuilder);
		_contributeSXPBlueprintJSON(searchRequestBuilder);

		return searchRequestBuilder.build();

	}

	private void _contributeSXPBlueprintId(
			SearchRequestBuilder searchRequestBuilder) {

		Object object = searchRequestBuilder.withSearchContextGet(
				searchContext -> searchContext.getAttribute(
						"search.experiences.blueprint.id"));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint ID " + object);
		}

		if (object == null) {
		} else if (object instanceof Number) {
			_enhance(searchRequestBuilder, GetterUtil.getLong(object));
		} else if (object instanceof String) {
			String string = (String)object;

			if (Validator.isNotNull(string)) {
				_enhance(
						searchRequestBuilder,
						GetterUtil.getLongValues(StringUtil.split(string)));
			}
		} else {
			throw new IllegalArgumentException(
					"Invalid search experiences blueprint ID " + object);
		}
	}

	private void _contributeSXPBlueprintJSON(
			SearchRequestBuilder searchRequestBuilder) {

		String sxpBlueprintJSON = searchRequestBuilder.withSearchContextGet(
				searchContext -> GetterUtil.getString(
						searchContext.getAttribute(
								"search.experiences.blueprint.json")));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint JSON " + sxpBlueprintJSON);
		}

		RuntimeException runtimeException = new RuntimeException();

		try {
			if (Validator.isNotNull(sxpBlueprintJSON)) {
				_sxpBlueprintSearchRequestEnhancer.enhance(
						searchRequestBuilder, sxpBlueprintJSON);
			}
		} catch (Exception exception) {
			runtimeException.addSuppressed(exception);
		}

		if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
			if (_log.isWarnEnabled()) {
				_log.warn(runtimeException);
			}
		}

		if (SXPExceptionUtil.hasErrors(runtimeException)) {
			throw runtimeException;
		}
	}

	private void _enhance(
			SearchRequestBuilder searchRequestBuilder, long... sxpBlueprintIds) {

		RuntimeException runtimeException = new RuntimeException();

		for (long sxpBlueprintId : sxpBlueprintIds) {
			if (sxpBlueprintId == 0) {
				continue;
			}

			SXPBlueprint sxpBlueprint =
					_sxpBlueprintLocalService.fetchSXPBlueprint(sxpBlueprintId);

			if (_log.isDebugEnabled()) {
				_log.debug("Search experiences blueprint " + sxpBlueprint);
			}

			try {
				if (sxpBlueprint != null) {
					_sxpBlueprintSearchRequestEnhancer.enhance(
							searchRequestBuilder, sxpBlueprint);
				}
			} catch (Exception exception) {
				runtimeException.addSuppressed(exception);
			}
		}

		if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
			if (_log.isWarnEnabled()) {
				_log.warn(runtimeException);
			}
		}

		if (SXPExceptionUtil.hasErrors(runtimeException)) {
			throw runtimeException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
			SearchRequestContributorOverridePortlet.class);

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
			_sxpBlueprintSearchRequestEnhancer;

	@Override
	public void contribute(PortletSharedSearchSettings portletSharedSearchSettings) {
		System.out.println("conrtibute PortletSharedSearchContributor");
	}
}