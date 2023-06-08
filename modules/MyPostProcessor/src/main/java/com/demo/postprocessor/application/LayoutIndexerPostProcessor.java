//package com.demo.postprocessor.application;
//
//import com.liferay.portal.kernel.search.*;
//import com.liferay.portal.kernel.search.filter.BooleanFilter;
//import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
//import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;
//import org.osgi.service.component.annotations.Component;
//
//import java.util.Locale;
//
//@Component(
//        immediate = true,
//        property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
//        service = SearchContextContributor.class
//)
//public class LayoutIndexerPostProcessor implements SearchContextContributor {
//
//    @Override
//    public void contribute(SearchContext searchContext, SearchContextContributorHelper searchContextContributorHelper) {
//        searchContext.setAttribute("queryStringBlacklist", "content_en_US");
//    }
//}
