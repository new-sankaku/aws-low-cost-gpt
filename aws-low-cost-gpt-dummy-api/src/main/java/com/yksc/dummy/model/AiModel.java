package com.yksc.dummy.model;

import java.util.Date;

public class AiModel {
	private String modelId;
	private String modelName;
	private String modelDescription;
	private Date createdDate;
	private int modelVersion;
	private String modelCategory;
	private String inputFormat;
	private String outputFormat;

	public AiModel() {
	}

	public AiModel( String modelId, String modelName, String modelDescription, Date createdDate, int modelVersion,
			String modelCategory, String inputFormat, String outputFormat ) {
		this.modelId = modelId;
		this.modelName = modelName;
		this.modelDescription = modelDescription;
		this.createdDate = createdDate;
		this.modelVersion = modelVersion;
		this.modelCategory = modelCategory;
		this.inputFormat = inputFormat;
		this.outputFormat = outputFormat;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId( String modelId ) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName( String modelName ) {
		this.modelName = modelName;
	}

	public String getModelDescription() {
		return modelDescription;
	}

	public void setModelDescription( String modelDescription ) {
		this.modelDescription = modelDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate( Date createdDate ) {
		this.createdDate = createdDate;
	}

	public int getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion( int modelVersion ) {
		this.modelVersion = modelVersion;
	}

	public String getModelCategory() {
		return modelCategory;
	}

	public void setModelCategory( String modelCategory ) {
		this.modelCategory = modelCategory;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat( String inputFormat ) {
		this.inputFormat = inputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat( String outputFormat ) {
		this.outputFormat = outputFormat;
	}

}
