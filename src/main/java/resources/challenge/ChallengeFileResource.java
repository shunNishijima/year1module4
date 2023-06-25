package resources.challenge;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.print.attribute.standard.Media;
import java.io.*;


@Path("/challenges/file")
public class ChallengeFileResource {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @POST
    @Consumes ({MediaType.MULTIPART_FORM_DATA})
    public void sendFile(@FormDataParam("file") InputStream fileInputStream,
                         @FormDataParam("file") FormDataContentDisposition fileMetaData){

        String uploadPath = "C:/Users/Niek-/Desktop/Module2/integration_Project_Java-65/mod4projec/Resources/challenges/uploaded/";
        try
        {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadPath + fileMetaData.getFileName()));
            while ((read = fileInputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e)
        {
            System.out.println(e);
            throw new WebApplicationException("Error while uploading file. Please try again !!");
        }

        System.out.println("File received");
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(String path){
        File file = new File(path);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).build();
    }


    public static void deleteFile(String path) {
        File file = new File(path);
        System.out.println(path);
        if (!file.delete()) {
            System.out.println("ERROR: could not delete file");
        }
    }


}
