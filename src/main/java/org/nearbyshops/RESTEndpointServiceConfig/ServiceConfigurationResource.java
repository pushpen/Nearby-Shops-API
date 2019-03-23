package org.nearbyshops.RESTEndpointServiceConfig;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.DAOPreparedSettings.ServiceConfigurationDAOPrepared;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.ModelSettings.ServiceConfigurationLocal;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;







@Path("/api/serviceconfiguration")
public class ServiceConfigurationResource {


	private ServiceConfigurationDAOPrepared daoPrepared = Globals.serviceConfigDAO;


	public ServiceConfigurationResource() {
		super();
		// TODO Auto-generated constructor stub
	}



	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(ServiceConfigurationLocal serviceConfigurationLocal)
	{

		int idOfInsertedRow = daoPrepared.saveService(serviceConfigurationLocal);

		serviceConfigurationLocal.setServiceID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/CurrentServiceConfiguration/" + idOfInsertedRow))
					.entity(serviceConfigurationLocal)
					.build();
			
			return response;
			
		}else if(idOfInsertedRow <=0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			//Response.status(Status.CREATED).location(arg0)
			
			return response;
		}
		
		return null;
		
	}


	//	@Path("/{ServiceID}")
//@PathParam("ServiceID")int serviceID,

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response updateService(ServiceConfigurationLocal serviceConfigurationLocal)
	{

		serviceConfigurationLocal.setServiceID(1);
		int rowCount =	daoPrepared.updateService(serviceConfigurationLocal);

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


//	@DELETE
//	@Path("/{ServiceID}")
	public Response deleteCart(@PathParam("ServiceID")int serviceID)
	{

		//int rowCount = Globals.cartService.deleteCart(cartID);

		int rowCount = daoPrepared.deleteService(serviceID);
		
		
		if(rowCount>=1)
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
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getService(@QueryParam("ServiceLevel") int serviceLevel,
                               @QueryParam("ServiceType") int serviceType,
                               @QueryParam("LatCenter") Double latCenterQuery,
                               @QueryParam("LonCenter") Double lonCenterQuery,
                               @QueryParam("SortBy") String sortBy,
                               @QueryParam("Limit") int limit, @QueryParam("Offset") int offset)

	{


		List<ServiceConfigurationLocal> servicesList = daoPrepared.readServices(serviceLevel,serviceType,latCenterQuery,lonCenterQuery,
                                    								sortBy,limit,offset);

		GenericEntity<List<ServiceConfigurationLocal>> listEntity = new GenericEntity<List<ServiceConfigurationLocal>>(servicesList){
			
		};
	
		
		if(servicesList.size()<=0)
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
	public Response getService(@QueryParam("latCenter")Double latCenter,
                               @QueryParam("lonCenter")Double lonCenter)
	{
//		@PathParam("ServiceID")int service

		ServiceConfigurationLocal serviceConfigurationLocal = daoPrepared.getServiceConfiguration(latCenter,lonCenter);
		
		if(serviceConfigurationLocal != null)
		{

			return Response.status(Status.OK)
			.entity(serviceConfigurationLocal)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.build();
		}
		
	}




	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/ServiceConfiguration");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{




		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
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
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

		boolean deleteStatus = false;

		Response response;

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
