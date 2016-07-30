package com.rta.carstore.domain.search

import org.elasticsearch.common.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * A Car.
 */
@Document(indexName = "carstore", type="car")
class Car implements Serializable {

	@Id
	@Field(type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String id


	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String vin;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String seller;

	@Field(
			type = FieldType.Double,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	BigDecimal year;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String make;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String model;

	@Field(
			type = FieldType.Double,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	BigDecimal odometer;

	@Field(
			type = FieldType.Double,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	BigDecimal openingBid;

	@Field(
			type = FieldType.Date,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	DateTime enddate;

	@Field(
			type = FieldType.Double,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	BigDecimal stockNumber;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String comments;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			searchAnalyzer = "standard",
			indexAnalyzer = "standard",
			store = true)
	String additionalComments;

}