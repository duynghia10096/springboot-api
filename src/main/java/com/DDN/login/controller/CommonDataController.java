package com.DDN.login.controller;

import com.DDN.login.common.ApiResponse;
import com.DDN.login.dto.CardToken;
import com.DDN.login.dto.PaymentStatus;
import com.DDN.login.dto.ProductDTOReceiveFromSQL;
import com.DDN.login.dto.ProductDto;
import com.DDN.login.dto.filter.BrandsAndApparelsAndGenderDTO;
import com.DDN.login.dto.filter.ProductInfoDTO;
import com.DDN.login.model.info.ProductInfo;
import com.DDN.login.payload.filter.FilterAttributesResponse;
import com.DDN.login.payload.filter.HomeTabsDataResponse;
import com.DDN.login.payload.filter.MainScreenResponse;
import com.DDN.login.payload.filter.SearchSuggestionResponse;
import com.DDN.login.repository.dao.info.ProductInfoRepository;
import com.DDN.login.security.service.interfaces.CommonDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/commondata")
public class CommonDataController {

   

    @Autowired
    CommonDataService commonDataService;
    @Autowired
    private ProductInfoRepository productInfoRepository;


    @GetMapping(value = "/products", params = "q")
    public ResponseEntity<?> getProductsByCategories(@RequestParam("q") String queryPrams) {
        ProductInfoDTO productInfoDTO = commonDataService.getProductsByCategories(queryPrams);
        if (productInfoDTO == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }
        return ResponseEntity.ok(productInfoDTO);
    }

    @GetMapping(value = "/products", params = "product_id")
    public ResponseEntity<?> getProductsById(@RequestParam("product_id") String queryParams) {
        HashMap<Integer, ProductDTOReceiveFromSQL> resultMap = commonDataService.getProductsById(queryParams);

        if (resultMap == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getMainScreenData() {
        MainScreenResponse mainScreenResponse = commonDataService.getHomeScreenData("homeAPI");
        if (mainScreenResponse == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(mainScreenResponse);
    }

    @GetMapping("/tabs")
    public ResponseEntity<?> getHomeTabsDataResponse() {
        HomeTabsDataResponse homeTabsDataResponse = commonDataService.getBrandsAndApparelsByGender("tabsAPI");
        if (homeTabsDataResponse == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(homeTabsDataResponse);
    }

    @GetMapping(value = "/filter", params = "q")
    public ResponseEntity<?> getFilterAttributesByProducts(@RequestParam("q") String queryParams) {

        // TODO: Add support for productname parameter for filter selection.
        String[] splitParams = queryParams.split("=");
        if (splitParams.length >= 1 && splitParams[0].equals("productname")) {
            queryParams = "category=all";
        }

        FilterAttributesResponse result = commonDataService.getFilterAttributesByProducts(queryParams);

        if (result == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search-suggestion-list")
    public ResponseEntity<?> getSearchSuggestionList() {
        SearchSuggestionResponse searchSuggestionList = commonDataService.getSearchSuggestionList();
        if (searchSuggestionList == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(searchSuggestionList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductInfo>> searchProduct(@RequestParam("query") String query) {
        return ResponseEntity.ok(productInfoRepository.searchByName(query));
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        BrandsAndApparelsAndGenderDTO categoryList = commonDataService.getCategoryList();
        if (categoryList == null) {
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(categoryList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> addProduct(@RequestHeader HttpHeaders headers,
            @ModelAttribute ProductDto productDto) {

        commonDataService.addProduct(productDto, productDto.getImages());
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<Map<String, Object>> getAllProducts(@RequestParam int page, @RequestParam int pageSize) {

        List<ProductInfo> products = new ArrayList<ProductInfo>();
        Pageable pagingSort = PageRequest.of(page, pageSize);
        Page<ProductInfo> pageProducts;

        pageProducts = productInfoRepository.findAll(pagingSort);

        products = pageProducts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalItems", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductDTOReceiveFromSQL> findById(@PathVariable Integer id) {
        Optional<ProductInfo> productDto = productInfoRepository.findById(id);

        ProductDTOReceiveFromSQL productDTOReceiveFromSQL = new ProductDTOReceiveFromSQL();
        productDTOReceiveFromSQL.setId(productDto.get().getId());
        productDTOReceiveFromSQL.setApparel_id(productDto.get().getApparelCategory().getId());
        productDTOReceiveFromSQL.setName(productDto.get().getName());
        productDTOReceiveFromSQL.setCreated_at(productDto.get().getPublicationDate());
        productDTOReceiveFromSQL.setAvailableQuantity(productDto.get().getAvailableQuantity());
        productDTOReceiveFromSQL.setDeliveryTime(productDto.get().getDeliveryTime());
        productDTOReceiveFromSQL.setPrice(productDto.get().getPrice());
        productDTOReceiveFromSQL.setBrand_id(productDto.get().getProductBrandCategory().getId());
        productDTOReceiveFromSQL.setGender_id(productDto.get().getGenderCategory().getId());
        productDTOReceiveFromSQL.setPrice_id(productDto.get().getPriceRangeCategory().getId());
        productDTOReceiveFromSQL.setRatings(productDto.get().getRatings());
        productDTOReceiveFromSQL.setImageLocalPath(productDto.get().getImageLocalPath());
        productDTOReceiveFromSQL.setImageURL(productDto.get().getImageURL());
        productDTOReceiveFromSQL.setDescription(productDto.get().getDescription());
        productDTOReceiveFromSQL.setProductImages(productDto.get().getProductImages());

        return new ResponseEntity<>(productDTOReceiveFromSQL, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") int productId) {
        productInfoRepository.deleteById(productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been removed"), HttpStatus.OK);
    }
}
