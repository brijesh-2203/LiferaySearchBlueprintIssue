package customblueprintparametercontributorportlet.portlet.shared.search;

import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import customblueprintparametercontributorportlet.constants.CustomBlueprintParameterContributorPortletKeys;
import org.osgi.service.component.annotations.Component;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;

import javax.portlet.PortletPreferences;

@Component(
        property = "javax.portlet.name=" + CustomBlueprintParameterContributorPortletKeys.CUSTOMBLUEPRINTPARAMETERCONTRIBUTOR,
        service = PortletSharedSearchContributor.class
)
public class CustomBlueprintParameterContributorPortletSharedSearchContributor implements PortletSharedSearchContributor {
    @Override
    public void contribute(
            PortletSharedSearchSettings portletSharedSearchSettings) {

        Optional<PortletPreferences> portletPreferencesOptional = portletSharedSearchSettings.getPortletPreferencesOptional();

        PortletPreferences portletPreferences = portletPreferencesOptional.get();

        SearchRequestBuilder searchRequestBuilder =
                portletSharedSearchSettings.getFederatedSearchRequestBuilder(
                        portletPreferences.getValue("federatedSearchKey", ""));

        if (searchRequestBuilder == null) {
            return;
        }

        ThemeDisplay themeDisplay =
                portletSharedSearchSettings.getThemeDisplay();

        if(themeDisplay.getRequest().getParameter("orderBy") != null) {
            if (themeDisplay.getRequest().getParameter("orderBy").equals("newest")) {
                searchRequestBuilder.withSearchContext(
                        searchContext -> {
                            searchContext.setAttribute(
                                    "custom.orderBy", "newest");
                        }
                );
            }
            if (themeDisplay.getRequest().getParameter("orderBy").equals("oldest")) {
                searchRequestBuilder.withSearchContext(
                        searchContext -> {
                            searchContext.setAttribute(
                                    "custom.orderBy", "oldest");
                        }
                );
            }
        }
    }
}
