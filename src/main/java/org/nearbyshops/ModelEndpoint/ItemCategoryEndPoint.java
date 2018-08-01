package org.nearbyshops.ModelEndpoint;

import org.nearbyshops.Model.ItemCategory;

import java.util.ArrayList;

/**
 * Created by sumeet on 30/6/16.
 */
public class ItemCategoryEndPoint {

    private int itemCount;
    private Integer offset;
    private Integer limit;
    private Integer max_limit;
    private ArrayList<ItemCategory> results;



    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public ArrayList<ItemCategory> getResults() {
        return results;
    }

    public void setResults(ArrayList<ItemCategory> results) {
        this.results = results;
    }


    public Integer getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(Integer max_limit) {
        this.max_limit = max_limit;
    }
}
