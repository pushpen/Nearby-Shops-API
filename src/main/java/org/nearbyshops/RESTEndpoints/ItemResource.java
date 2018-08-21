package org.nearbyshops.RESTEndpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.coobird.thumbnailator.Thumbnails;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.nearbyshops.DAOsPrepared.ItemDAO;
import org.nearbyshops.DAOsPrepared.ItemDAOJoinOuter;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndpoint.ItemEndPoint;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Path("/api/v1/Item")
public class ItemResource {

	private ItemDAO itemDAO = Globals.itemDAO;
	private ItemDAOJoinOuter itemDAOJoinOuter = Globals.itemDAOJoinOuter;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response createItem(Item item)
	{

//		if(Globals.accountApproved instanceof Staff) {
//
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}


		int idOfInsertedRow = itemDAO.saveItem(item,false);
		
		item.setItemID(idOfInsertedRow);
		
		if(idOfInsertedRow >=1)
		{

			return Response.status(Status.CREATED)
					.location(URI.create("/api/Item/" + idOfInsertedRow))
					.entity(item)
					.build();
			
		}else if(idOfInsertedRow <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		
		return null;
	}





	@PUT
	@Path("/ChangeParent/{ItemID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response changeParent(Item item, @PathParam("ItemID")int itemID)
	{


//		if(Globals.accountApproved instanceof Staff) {
//
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}



		item.setItemID(itemID);

		//System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		//+ " " + itemCategory.getCategoryDescription());

		int rowCount = itemDAO.changeParent(item);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;

	}





	@PUT
	@Path("/ChangeParent")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response changeParentBulk(List<Item> itemList)
	{


//		if(Globals.accountApproved instanceof Staff) {
//
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}




		int rowCountSum = 0;

//		for(Item item : itemList)
//		{
//			rowCountSum = rowCountSum + itemDAO.updateItem(item);
//		}

		rowCountSum = itemDAO.changeParentBulk(itemList);

		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}






	@PUT
	@Path("/{ItemID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response updateItem(Item item, @PathParam("ItemID")int itemID)
	{


//		if(Globals.accountApproved instanceof Staff) {
//
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}


			
		item.setItemID(itemID);
	
		//System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		//+ " " + itemCategory.getCategoryDescription());
		
		int rowCount = itemDAO.updateItem(item);
		
		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;

	}



//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
//	public Response updateItemBulk(List<Item> itemList)
//	{
//
//		if(Globals.accountApproved instanceof Staff) {
//
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//		int rowCountSum = 0;
//
////		for(Item item : itemList)
////		{
////			rowCountSum = rowCountSum + itemDAO.updateItem(item);
////		}
//
//		rowCountSum = itemDAO.updateItemBulk(itemList);
//
//		if(rowCountSum ==  itemList.size())
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//		else if( rowCountSum < itemList.size() && rowCountSum > 0)
//		{
//
//			return Response.status(Status.PARTIAL_CONTENT)
//					.entity(null)
//					.build();
//		}
//		else if(rowCountSum == 0 ) {
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//		return null;
//	}
	
	
	@DELETE
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response deleteItem(@PathParam("ItemID")int itemID)
	{

//		if(Globals.accountApproved instanceof Staff) {
//
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
		
//		int rowCount = itemDAO.deleteItem(itemID);

		Item item = itemDAO.getItemImageURL(itemID);
		int rowCount = itemDAO.deleteItem(itemID);


		if(item!=null && rowCount>=1)
		{
			// delete successful delete the image also
			System.out.println("Image FIle : " + item.getItemImageURL());
			deleteImageFileInternal(item.getItemImageURL());
		}



		if(rowCount>=1)
		{

			return Response.status(Status.OK)
					.build();
		}
		
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}
		
		return null;
	}
	
	
	

//	@Path("/Deprecated")
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
            @QueryParam("ItemCategoryID")Integer itemCategoryID,
            @QueryParam("ShopID")Integer shopID,
            @QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
            @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
            @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
            @QueryParam("proximity")Double proximity,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset)
	
	{
				
		//Marker
		
		List<Item> list =
				itemDAO.getItems(
						itemCategoryID,
						shopID,
						latCenter, lonCenter,
						deliveryRangeMin,
						deliveryRangeMax,
						proximity,null,
						sortBy,limit,offset
				);
		
		
		GenericEntity<List<Item>> listEntity = new GenericEntity<List<Item>>(list){
			
			};
		
			
			if(list.size()<=0)
			{

				return Response.status(Status.NO_CONTENT)
						.entity(listEntity)
						.build();
				
			}else
			{

				return Response.status(Status.OK)
						.entity(listEntity)
						.build();
			}	
	}




	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
            @QueryParam("ItemCategoryID")Integer itemCategoryID,
            @QueryParam("ShopID")Integer shopID,
            @QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
            @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
            @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
            @QueryParam("proximity")Double proximity,
            @QueryParam("SearchString")String searchString,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("metadata_only")Boolean metaonly)
	{

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;

		if(limit!= null)
		{

			if (limit >= max_limit) {

				set_limit = max_limit;
			}
			else
			{

				set_limit = limit;
			}

		}

		if(offset!=null)
		{
			set_offset = offset;
		}

		ItemEndPoint endPoint = itemDAO.getEndPointMetadata(itemCategoryID,
				shopID,latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAO.getItems(
							itemCategoryID,
							shopID,
							latCenter, lonCenter,
							deliveryRangeMin,
							deliveryRangeMax,
							proximity,searchString,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		//Marker

		return Response.status(Status.OK)
                .entity(endPoint)
                .build();

	}



	//@QueryParam("ShopID")Integer shopID,

	@GET
	@Path("/OuterJoin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
            @QueryParam("ItemCategoryID")Integer itemCategoryID,
            @QueryParam("IsDetached")Boolean parentIsNull,
            @QueryParam("SearchString") String searchString,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("metadata_only")Boolean metaonly)
	{

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;

		if(limit!= null)
		{

			if (limit >= max_limit) {

				set_limit = max_limit;
			}
			else
			{

				set_limit = limit;
			}

		}

		if(offset!=null)
		{
			set_offset = offset;
		}




		ItemEndPoint endPoint = itemDAOJoinOuter.getEndPointMetadata(itemCategoryID,parentIsNull,searchString);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAOJoinOuter.getItems(
							itemCategoryID,
							parentIsNull,searchString,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}










	// Image Utility Methods

	boolean deleteImageFileInternal(String fileName)
	{
		boolean deleteStatus = false;

		System.out.println("Filename: " + fileName);

		try {

			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return deleteStatus;
	}




	private String saveNewImage(String serviceURL,String imageID)
	{
		try
		{
			serviceURL = serviceURL + "/api/v1/Item/Image/" + imageID;

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(serviceURL)
					.build();

			okhttp3.Response response = null;
			response = client.newCall(request).execute();
//			response.body().byteStream();
//			System.out.println();

			return uploadNewImage(response.body().byteStream());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}




	String uploadNewImage(InputStream in)
	{

		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("Creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(Exception se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}



		String fileName = "" + System.currentTimeMillis();


		try {

			// Copy the file to its location.
			long filesize = 0;

			filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

			if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
			{
				// delete file if it exceeds the file size limit
				Files.deleteIfExists(BASE_DIR.resolve(fileName));
				return null;
			}

			createThumbnails(fileName);

			Image image = new Image();
			image.setPath(fileName);

			// Return a 201 Created response with the appropriate Location header.

		}
		catch (IOException e) {
			e.printStackTrace();

			return null;
		}

		return fileName;
	}




	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Item");
	private static final double MAX_IMAGE_SIZE_MB = 2;




	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF, GlobalConstants.ROLE_SHOP_ADMIN})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{

//		if(Globals.accountApproved instanceof Staff)
//		{
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}


		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("seven_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + previousImageName + ".jpg"));
		}


		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("Creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(Exception se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}



		String fileName = "" + System.currentTimeMillis();

		// Copy the file to its location.
		long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
		{
			// delete file if it exceeds the file size limit
			Files.deleteIfExists(BASE_DIR.resolve(fileName));

			return Response.status(Status.EXPECTATION_FAILED).build();
		}


		createThumbnails(fileName);


		Image image = new Image();
		image.setPath(fileName);

		// Return a 201 Created response with the appropriate Location header.

		return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
	}



	private void createThumbnails(String filename)
	{
		try {

			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(300,300)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

			//.toFile(new File("five-" + filename + ".jpg"));

			//.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(500,500)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(700,700)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "seven_hundred_" + filename));



			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(900,900)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "nine_hundred_" + filename));



		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@GET
	@Path("/Image/{name}")
	@Produces("image/jpeg")
	public InputStream getImage(@PathParam("name") String fileName) {

		//fileName += ".jpg";
		java.nio.file.Path dest = BASE_DIR.resolve(fileName);

		if (!Files.exists(dest)) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}


		try {
			return Files.newInputStream(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}



	@DELETE
	@Path("/Image/{name}")
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

//		if(Globals.accountApproved instanceof Staff) {
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//			if (!staff.isCreateUpdateItems())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}


		boolean deleteStatus = false;
		Response response;
		System.out.println("Filename: " + fileName);

		try {


			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("seven_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(!deleteStatus)
		{
			response = Response.status(Status.NOT_MODIFIED).build();

		}else
		{
			response = Response.status(Status.OK).build();
		}

		return response;
	}




}
