package com.team32.ong.constant;

public class ConstantSwaggerMessage {
	
	public static final String MSG_DESCRIPTION_ENDPOINT_SAVE = "This endpoint is used to save a category, if it is saved correctly it will show a status of 201";
	public static final String MSG_DESCRIPTION_ENDPOINT_FINDBYID = "This endpoint is used to search a category by id, if id exists, a 200 status will be returned with the category found";
	public static final String MSG_DESCRIPTION_ENDPOINT_DELETE = "A logical delete is performed if the id being passed exists, otherwise a 404 error will be displayed";
	public static final String MSG_DESCRIPTION_ENDPOINT_UPDATE = "A category will be updated depending on the id passed to it, if the id does not exist, a 404 error will be displayed";
	public static final String MSG_DESCRIPTION_ENDPOINT_FINDALL = "This endpoint is used to display a list of categories";

}
