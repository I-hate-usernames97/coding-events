package org.launchcode.codingevents.controllers.models;



public class SearchForm {

    private String query;

    private String searchType;

    public SearchForm() {
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
