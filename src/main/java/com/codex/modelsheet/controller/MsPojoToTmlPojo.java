package com.codex.modelsheet.controller;

import com.codex.modelsheet.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class MsPojoToTmlPojo {
    public EDoc.ObjectEDocProto.Builder convertToTMLPOJO(ModelSheet modelSheet) {
        EDoc.ObjectEDocProto.Builder builder = EDoc.ObjectEDocProto.newBuilder();

        EDoc.WorksheetEDocProto.Builder worksheetBuilder = EDoc.WorksheetEDocProto.newBuilder();

        worksheetBuilder.setName(modelSheet.getWorkSheets().get(0).getWorksheetName());

        for (WorkSheet workSheet : modelSheet.getWorkSheets()) {
            EDoc.Identity.Builder tableBuilder = EDoc.Identity.newBuilder();
            tableBuilder.setName(workSheet.getTables());
            worksheetBuilder.getTablesList().add(tableBuilder.build());
        }

        for(Tables t : modelSheet.getTables()){
            EDoc.TablePath.JoinPath.Builder tablePathJoinPathBuilder = null;
            if(!t.getJoinsWith().isEmpty()){
                EDoc.Join.Builder joinBuilder =  EDoc.Join.newBuilder();
                joinBuilder.setName(t.getJoinName());
                joinBuilder.setSource(t.getTable());
                joinBuilder.setDestination(t.getJoinsWith());
                joinBuilder.setType(t.getJoinType());
                joinBuilder.setIsOneToOne(Boolean.parseBoolean(t.getJoinCardinality()));
                worksheetBuilder.getJoinsList().add(joinBuilder.build());

                tablePathJoinPathBuilder = EDoc.TablePath.JoinPath.newBuilder();
                tablePathJoinPathBuilder.getJoinList().add(t.getJoinName());
            }

            EDoc.TablePath.Builder tablePathBuilder = EDoc.TablePath.newBuilder();
            tablePathBuilder.setId(t.getTable());
            tablePathBuilder.setTable(t.getTable());
            if (tablePathJoinPathBuilder != null ) {
                tablePathBuilder.getJoinPathList().add(tablePathJoinPathBuilder.build());
            }
            worksheetBuilder.getTablePathsList().add(tablePathBuilder.build());
        }

        List<Attribute> worksheetAttributes = modelSheet.getAttributes().parallelStream().filter(attr -> "Y".equalsIgnoreCase(attr.getWorksheetColumn())).collect(Collectors.toList());
        for(Attribute attribute : worksheetAttributes){
            EDoc.WorksheetEDocProto.WorksheetColumn.Builder worksheetColumnProto = EDoc.WorksheetEDocProto.WorksheetColumn.newBuilder();
            worksheetColumnProto.setName(attribute.getColumn());
            worksheetColumnProto.setColumnId(attribute.getTable()+"::"+attribute.getColumn());
            worksheetColumnProto.setDescription(attribute.getDescription());

            EDoc.ColumnProperties.Builder columnPropertiesBuilder = EDoc.ColumnProperties.newBuilder();
            columnPropertiesBuilder.setColumnType(attribute.getColumnType());
            columnPropertiesBuilder.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
            columnPropertiesBuilder.setAggregation(attribute.getAggregation());
            columnPropertiesBuilder.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
            columnPropertiesBuilder.getSynonymsList().add(attribute.getSynonyms());//TODO it's a list
            //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
            //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
            columnPropertiesBuilder.setIndexType(attribute.getIndexType());
            columnPropertiesBuilder.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
            columnPropertiesBuilder.setFormatPattern(attribute.getFormatPattern());
            if(attribute.getCurrencyType() != null && attribute.getCurrencyType().isEmpty()){
                EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                currencyFormat.setColumn(attribute.getCurrencyType());
                columnPropertiesBuilder.setCurrencyType(currencyFormat);
            }
            columnPropertiesBuilder.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
            columnPropertiesBuilder.setSpotiqPreference(attribute.getSpotIqPreference());
            columnPropertiesBuilder.setCalendar(attribute.getCalenderType());

            worksheetColumnProto.setProperties(columnPropertiesBuilder);
            worksheetBuilder.getWorksheetColumnsList().add(worksheetColumnProto.build());
        }

        EDoc.WorksheetEDocProto.QueryProperties.Builder worksheetProperties = EDoc.WorksheetEDocProto.QueryProperties.newBuilder();
        worksheetProperties.setIsBypassRls(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getByPassRls()));
        worksheetProperties.setJoinProgressive(Boolean.parseBoolean(modelSheet.getWorkSheets().get(0).getProgressiveJoin()));
        worksheetBuilder.setProperties(worksheetProperties.build());

        builder.setWorksheet(worksheetBuilder);



        for(Tables table : modelSheet.getTables()){
            EDoc.LogicalTableEDocProto.Builder tableEDocProto = EDoc.LogicalTableEDocProto.newBuilder();
            tableEDocProto.setName(table.getTable());
            tableEDocProto.setDb(table.getDatabase());
            tableEDocProto.setSchema(table.getSchema());
            tableEDocProto.setDbTable(table.getDbTable());

            EDoc.Identity.Builder connectionBuilder = EDoc.Identity.newBuilder();
            connectionBuilder.setName(table.getConnection());
            tableEDocProto.setConnection(connectionBuilder);

            List<Attribute> tableAttributes = modelSheet.getAttributes().parallelStream().filter(a -> a.getTable().equals(table.getTable())).collect(Collectors.toList());
            for (Attribute attribute : tableAttributes) {
                EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.Builder columnEDocProto = EDoc.LogicalTableEDocProto.LogicalColumnEDocProto.newBuilder();
                columnEDocProto.setName(attribute.getColumn());
                columnEDocProto.setDbColumnName(attribute.getColumn());
                columnEDocProto.setDescription(attribute.getDescription());

                EDoc.ColumnProperties.Builder propertyProto = EDoc.ColumnProperties.newBuilder();
                propertyProto.setColumnType(attribute.getColumnType());
                propertyProto.setIsAdditive(Boolean.parseBoolean(attribute.getAdditive()));
                propertyProto.setAggregation(attribute.getAggregation());
                propertyProto.setIsHidden(Boolean.parseBoolean(attribute.getHiddenAttribute()));
                propertyProto.getSynonymsList().add(attribute.getSynonyms());//TODO it's a list
                //attribute.setSuggestedValue(attributeProto.getProperties().get); //TODO need find exact field form ts wrapper
                //attribute.setGeoConfig(attributeProto.getProperties().getGeoConfig().get); TODO GeoConfig is a bean
                propertyProto.setIndexType(attribute.getIndexType());
                propertyProto.setIndexPriority(Double.parseDouble(attribute.getIndexPriority()));
                propertyProto.setFormatPattern(attribute.getFormatPattern());
                if(attribute.getCurrencyType() != null && attribute.getCurrencyType().isEmpty()){
                    EDoc.ColumnProperties.CurrencyFormat.Builder currencyFormat = EDoc.ColumnProperties.CurrencyFormat.newBuilder();
                    currencyFormat.setColumn(attribute.getCurrencyType());
                    propertyProto.setCurrencyType(currencyFormat);
                }
                propertyProto.setIsAttributionDimension(Boolean.parseBoolean(attribute.getAttributeDimension()));
                propertyProto.setSpotiqPreference(attribute.getSpotIqPreference());
                propertyProto.setCalendar(attribute.getCalenderType());
                columnEDocProto.setProperties(propertyProto);

                EDoc.LogicalTableEDocProto.DbColumnProperties.Builder dbPropertyProto = EDoc.LogicalTableEDocProto.DbColumnProperties.newBuilder();
                dbPropertyProto.setDataType(attribute.getDataType());
                columnEDocProto.setDbColumnProperties(dbPropertyProto);

                tableEDocProto.getColumnsList().add(columnEDocProto.build());
            }
            if(!table.getJoinsWith().isEmpty()) {
                EDoc.RelationEDocProto.Builder joinsWithProto = EDoc.RelationEDocProto.newBuilder();
                joinsWithProto.setName(table.getJoinName());
                EDoc.Identity.Builder destinationProto = EDoc.Identity.newBuilder();
                destinationProto.setName(table.getJoinsWith());
                joinsWithProto.setDestination(destinationProto);
                joinsWithProto.setOn(table.getConnection());
                joinsWithProto.setType(table.getJoinType());
                tableEDocProto.getJoinsWithList().add(joinsWithProto.build());
            }
            builder.setTable(tableEDocProto.build());
        }
        return builder;
    }
}