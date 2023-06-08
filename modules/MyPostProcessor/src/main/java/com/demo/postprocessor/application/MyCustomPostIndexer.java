package com.demo.postprocessor.application;

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.search.*;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Component(
        immediate = true,
        property = {
                "indexer.class.name=com.liferay.journal.model.JournalArticle"
        },
        service = IndexerPostProcessor.class
)
public class MyCustomPostIndexer implements IndexerPostProcessor {

    @Override
    public void postProcessContextBooleanFilter(BooleanFilter booleanFilter, SearchContext searchContext) throws Exception {
         System.out.println("postProcessContextBooleanFilter");
    }

    @Override
    public void postProcessDocument(Document document, Object object) throws Exception {
        System.out.println("postProcessDocument");
    }

    @Override
    public void postProcessFullQuery(BooleanQuery fullQuery, SearchContext searchContext) throws Exception {
//        searchContext.setAttribute("search.experiences.blueprint.id",45207);
        System.out.println(Arrays.toString(fullQuery.getClass().getFields()));
    }


    @Override
    public void postProcessSearchQuery(BooleanQuery searchQuery, BooleanFilter booleanFilter, SearchContext searchContext) throws Exception{
//        searchContext.setAttribute("search.experiences.blueprint.id","44387");
        System.out.println("postProcessSearchQuery");
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String snippet) {
        System.out.println("postProcessSummary");

    }
}
