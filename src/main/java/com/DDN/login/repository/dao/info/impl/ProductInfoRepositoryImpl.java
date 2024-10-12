package com.DDN.login.repository.dao.info.impl;

import com.DDN.login.dto.ProductDTOReceiveFromSQL;
import com.DDN.login.repository.dao.info.queryhelpers.ProductQueryHelper;
import com.DDN.login.repository.dao.info.queryhelpers.context.ParamsToQueryContext;
import com.DDN.login.dto.filter.BrandsAndApparelsDTO;
import com.DDN.login.dto.filter.FilterAttributesWithTotalItemsDTO;
import com.DDN.login.dto.filter.SearchSuggestionForThreeAttrDTO;
import com.DDN.login.dto.filter.SearchSuggestionForTwoAttrDTO;
import com.DDN.login.model.info.ProductInfo;
import com.DDN.login.payload.filter.FilterAttributesResponse;
import com.DDN.login.payload.filter.HomeTabsDataResponse;
import com.DDN.login.utils.ListResultTransFormer;
import org.javatuples.Pair;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public  class ProductInfoRepositoryImpl  {
    @PersistenceContext
    private EntityManager entityManager;



    public Pair<Long, List<ProductDTOReceiveFromSQL>> getProductsByCategories(HashMap<String, String> conditionMap) {
        ParamsToQueryContext paramsToQueryContext = new ProductQueryHelper().getParamsToQueryMap(conditionMap);

        String sortBy = paramsToQueryContext.getSortBy();
        HashMap<Integer,Object> mapParams = paramsToQueryContext.getMapParams();
        List<String> conditions = paramsToQueryContext.getConditions();
        String[] pageInfo = paramsToQueryContext.getPageInfo();

        TypedQuery<Long> totalCountQuery = (TypedQuery<Long>) entityManager.createQuery(
                "select count(*) from ProductInfo p where "
                + String.join(" AND ", conditions));
        mapParams.forEach(totalCountQuery::setParameter);
        List<Long> totalCountQueryResultList = totalCountQuery.getResultList();
        if(totalCountQueryResultList != null && totalCountQueryResultList.get(0) > 0) {
            Long totalCount = totalCountQueryResultList.get(0);
            TypedQuery<ProductInfo> query = entityManager.createQuery(
                    "select p from ProductInfo p where "
                    + String.join(" AND ", conditions) + sortBy, ProductInfo.class);
            mapParams.forEach(query::setParameter);
            List<ProductInfo> queryResult = query.getResultList();
            List<ProductDTOReceiveFromSQL> queryDTOList = new ArrayList<>(1000);

            for(int i = 0; i < queryResult.toArray().length; i++) {
                ProductDTOReceiveFromSQL queryDTO = new ProductDTOReceiveFromSQL();
                queryDTO.setId(queryResult.get(i).getId());
                queryDTO.setApparel_id(queryResult.get(i).getApparelCategory().getId());
                queryDTO.setName(queryResult.get(i).getName());
                queryDTO.setCreated_at(queryResult.get(i).getPublicationDate());
                queryDTO.setAvailableQuantity(queryResult.get(i).getAvailableQuantity());
                queryDTO.setDeliveryTime(queryResult.get(i).getDeliveryTime());
                queryDTO.setPrice(queryResult.get(i).getPrice());
                queryDTO.setBrand_id(queryResult.get(i).getProductBrandCategory().getId());
                queryDTO.setGender_id(queryResult.get(i).getGenderCategory().getId());
                queryDTO.setPrice_id(queryResult.get(i).getPriceRangeCategory().getId());
                queryDTO.setRatings(queryResult.get(i).getRatings());
                queryDTO.setImageLocalPath(queryResult.get(i).getImageLocalPath());
                queryDTO.setImageURL(queryResult.get(i).getImageURL());
                queryDTO.setProductImages(queryResult.get(i).getProductImages());
                queryDTO.setDescription(queryResult.get(i).getDescription());
                queryDTOList.add(queryDTO);
            }
            if(pageInfo!=null && pageInfo.length == 2) {
                List<ProductInfo> queryResult2 = query.setFirstResult(Integer.parseInt(pageInfo[0]))
                        .setMaxResults(Integer.parseInt(pageInfo[1]))
                        .getResultList();
                List<ProductDTOReceiveFromSQL> queryDTOList2 = new ArrayList<>(1000);

                for(int i = 0; i < queryResult2.size(); i++) {
                    ProductDTOReceiveFromSQL queryDTO2 = new ProductDTOReceiveFromSQL();
                    queryDTO2.setId(queryResult2.get(i).getId());
                    queryDTO2.setApparel_id(queryResult2.get(i).getApparelCategory().getId());
                    queryDTO2.setName(queryResult2.get(i).getName());
                    queryDTO2.setCreated_at(queryResult2.get(i).getPublicationDate());
                    queryDTO2.setAvailableQuantity(queryResult2.get(i).getAvailableQuantity());
                    queryDTO2.setDeliveryTime(queryResult2.get(i).getDeliveryTime());
                    queryDTO2.setPrice(queryResult2.get(i).getPrice());
                    queryDTO2.setBrand_id(queryResult2.get(i).getProductBrandCategory().getId());
                    queryDTO2.setGender_id(queryResult2.get(i).getGenderCategory().getId());
                    queryDTO2.setPrice_id(queryResult2.get(i).getPriceRangeCategory().getId());
                    queryDTO2.setRatings(queryResult2.get(i).getRatings());
                    queryDTO2.setImageLocalPath(queryResult2.get(i).getImageLocalPath());
                    queryDTO2.setImageURL(queryResult2.get(i).getImageURL());
                    queryDTO2.setProductImages(queryResult2.get(i).getProductImages());
                    queryDTO2.setDescription(queryResult2.get(i).getDescription());
                    queryDTOList2.add(queryDTO2);
                }


                Pair<Long, List<ProductDTOReceiveFromSQL>> result = new Pair<>(totalCount, queryDTOList2);
                return result;

            }
            return new Pair<>(totalCount,  queryDTOList);
        }
       return null;
    }


    public List<ProductInfo> getProductsById(String[] product_ids_str) {
        List<Integer> productIds = new ArrayList<>();
        for(String id : product_ids_str ) {
            productIds.add(Integer.valueOf(id));
        }
        TypedQuery<ProductInfo> query = entityManager.createQuery(
                "SELECT p FROM ProductInfo p WHERE p.id IN (?1)", ProductInfo.class);
        query.setParameter(1, productIds);
        return query.getResultList();
    }

    private ParamsToQueryContext filterAndGetConditionMap(ProductQueryHelper productQueryHelper,
                                                          HashMap<String,String> conditionMap,
                                                          String queryParam) {
        Map<String,String> filterConditionMap = null;
        if(conditionMap.containsKey(queryParam)){
            filterConditionMap = conditionMap.entrySet()
                    .stream()
                    .filter(map -> !map.getKey().equals(queryParam))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if(!filterConditionMap.containsKey("brands") && !filterConditionMap.containsKey("gender")
                    && !filterConditionMap.containsKey("apparels") && !filterConditionMap.containsKey("prices")){
                filterConditionMap.put("category", "all");
            }
        }

        ParamsToQueryContext paramsToQueryContext;
        if(filterConditionMap != null) {
            paramsToQueryContext = productQueryHelper.getParamsToQueryMap(
                    (HashMap<String,String>) filterConditionMap);
        } else {
            paramsToQueryContext = productQueryHelper.getParamsToQueryMap(conditionMap);
        }

        return paramsToQueryContext;
    }


    public FilterAttributesResponse getFilterAttributesByProducts(HashMap<String, String> conditionMap) {
        ProductQueryHelper productQueryHelper = new ProductQueryHelper();
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();

        ParamsToQueryContext paramsToQueryContext = filterAndGetConditionMap(productQueryHelper, conditionMap, "brands");
        List<FilterAttributesWithTotalItemsDTO>
                brandList = listResultTransFormer.getFilterAttributesWithTotalItemsResultTransformer(
                        "SELECT p.productBrandCategory.id, p.productBrandCategory.type, count(*) as totalItems " +
                                "from ProductInfo p where " +
                                String.join(" AND ", paramsToQueryContext.getConditions()) +
                                "group by p.productBrandCategory.id, p.productBrandCategory.type order by totalItems desc",
                                paramsToQueryContext.getMapParams(), entityManager);
        paramsToQueryContext = filterAndGetConditionMap(productQueryHelper, conditionMap, "genders");
        List<FilterAttributesWithTotalItemsDTO>
                genderList = listResultTransFormer.getFilterAttributesWithTotalItemsResultTransformer(
                "SELECT p.genderCategory.id, p.genderCategory.type, count(*) as totalItems " +
                        "from ProductInfo p where " + String.join(" AND ", paramsToQueryContext.getConditions()) +
                        "group by p.genderCategory.id, p.genderCategory.type order by totalItems desc",
                paramsToQueryContext.getMapParams(), entityManager);

        paramsToQueryContext = filterAndGetConditionMap(productQueryHelper, conditionMap, "apparels");
        List<FilterAttributesWithTotalItemsDTO>
                apparelList = listResultTransFormer.getFilterAttributesWithTotalItemsResultTransformer(
                "SELECT p.apparelCategory.id, p.apparelCategory.type, count(*) as totalItems " +
                        "from ProductInfo p where " + String.join(" AND ", paramsToQueryContext.getConditions()) +
                        "group by p.apparelCategory.id, p.apparelCategory.type order by totalItems desc",
                paramsToQueryContext.getMapParams(), entityManager);

        paramsToQueryContext = filterAndGetConditionMap(productQueryHelper, conditionMap, "prices");
        List<FilterAttributesWithTotalItemsDTO>
                priceList = listResultTransFormer.getFilterAttributesWithTotalItemsResultTransformer(
                "SELECT p.priceRangeCategory.id, p.priceRangeCategory.type, count(*) as totalItems " +
                        "from ProductInfo p where " + String.join(" AND ", paramsToQueryContext.getConditions()) +
                        "group by p.priceRangeCategory.id, p.priceRangeCategory.type order by p.priceRangeCategory.id",
                paramsToQueryContext.getMapParams(), entityManager);

        FilterAttributesResponse filterAttributesResponse = new FilterAttributesResponse();
        filterAttributesResponse.setBrands(brandList);
        filterAttributesResponse.setApparels(apparelList);
        filterAttributesResponse.setGenders(genderList);
        filterAttributesResponse.setPrices(priceList);

        return filterAttributesResponse;

        

    }


    private BrandsAndApparelsDTO getBrandsAndApparelList(int gender_id) {
        BrandsAndApparelsDTO brandsAndApparelsDTO = new BrandsAndApparelsDTO();
        HashMap<Integer, Integer> mapParams = new HashMap<>();
        mapParams.put(1, gender_id);
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();

        brandsAndApparelsDTO.setBrands(listResultTransFormer.getFilterAttributesResultTransformer(
                "SELECT DISTINCT p.productBrandCategory.id, p.productBrandCategory.type " +
                        "from ProductInfo p where p.genderCategory.id=?1" +
                        " group by p.productBrandCategory.id, p.productBrandCategory.type", mapParams, entityManager));

        brandsAndApparelsDTO.setApparels(listResultTransFormer.getFilterAttributesResultTransformer(
                "SELECT DISTINCT p.apparelCategory.id, p.apparelCategory.type " +
                        "from ProductInfo p where p.genderCategory.id=?1 " +
                        "group by p.apparelCategory.id, p.apparelCategory.type ", mapParams, entityManager));

        return brandsAndApparelsDTO;
    }



    public HomeTabsDataResponse getBrandsAndApparelsByGender() {
        HomeTabsDataResponse homeTabsDataResponse = new HomeTabsDataResponse();
        homeTabsDataResponse.setWomen(getBrandsAndApparelList(1));
        homeTabsDataResponse.setMen(getBrandsAndApparelList(2));
        homeTabsDataResponse.setGirls(getBrandsAndApparelList(3));
        homeTabsDataResponse.setBoys(getBrandsAndApparelList(4));
        homeTabsDataResponse.setHomeAndLiving(getBrandsAndApparelList(5));
        homeTabsDataResponse.setEssentials(getBrandsAndApparelList(6));
        return homeTabsDataResponse;
    }


    public List<SearchSuggestionForThreeAttrDTO> getGenderApparelBrandByIdAndName() {
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();
        List<SearchSuggestionForThreeAttrDTO> listResult = listResultTransFormer.getSearchSuggestionForThreeAttrResultTransformer(
                "SELECT DISTINCT " +
                        " p.genderCategory.id, p.genderCategory.type," +
                        " p.apparelCategory.id, p.apparelCategory.type," +
                        " p.productBrandCategory.id, p.productBrandCategory.type from ProductInfo p"
                , entityManager);
        return listResult;
    }


    public List<SearchSuggestionForTwoAttrDTO> getGenderAndApparelByIdAndName() {
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();

        List<SearchSuggestionForTwoAttrDTO> listResult = listResultTransFormer.getSearchSuggestionForTwoAttrResultTransformer("SELECT DISTINCT " +
                        " p.genderCategory.id, p.genderCategory.type," +
                        " p.apparelCategory.id, p.apparelCategory.type" +
                        " from ProductInfo p"
                , entityManager);
        return listResult;
    }


    public List<SearchSuggestionForTwoAttrDTO> getGenderAndBrandByIdAndName() {
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();
        List<SearchSuggestionForTwoAttrDTO> listResult = listResultTransFormer.getSearchSuggestionForTwoAttrResultTransformer(
                "SELECT DISTINCT " +
                        " p.genderCategory.id, p.genderCategory.type," +
                        " p.productBrandCategory.id, p.productBrandCategory.type" +
                        " from ProductInfo p", entityManager);
        return listResult;
    }


    public List<SearchSuggestionForTwoAttrDTO> getApparelAndBrandByIdAndName() {
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();
        List<SearchSuggestionForTwoAttrDTO> listResult =  listResultTransFormer.getSearchSuggestionForTwoAttrResultTransformer("SELECT DISTINCT " +
                        " p.apparelCategory.id, p.apparelCategory.type," +
                        " p.productBrandCategory.id, p.productBrandCategory.type" +
                        " from ProductInfo p"
                , entityManager);
        return listResult;

    }




}
