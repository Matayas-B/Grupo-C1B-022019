package backend.controller;

import backend.controller.requests.SearchRequest;
import backend.model.Menu;
import backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService = new SearchService();

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Iterable<Menu> searchMenusByCriteria(@RequestBody SearchRequest searchRequest) {
        return searchService.searchMenusByCriteria(searchRequest);
    }
}
