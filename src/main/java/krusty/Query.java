package krusty;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private String queryString;

    private Query(QueryBuilder builder){
        this.queryString = builder.queryString;
    }

    public String getQueryString(){
        return queryString;
    }


    public static class QueryBuilder{

        private String queryString;

        private List<String> columns;


        public QueryBuilder(String tableName){
            columns = new ArrayList<>();
        }

        /**
         * If no column are added all columns will be return in the
         * final query.
         * */
        public QueryBuilder addColumn(String columnName){
            columns.add(columnName);
            return this;
        }

        private void validateQuery(Query query){

        }

        public Query build(){
            Query query = new Query(this);
            validateQuery(query);
            return query;
        }

    }

    private String query;

    public Query(){}

    public Query setFrom(String tableName){

        return this;
    }

    public Query addWhere(String column, String value){

        return this;
    }




    public String build(){
        return "";
    }

}
