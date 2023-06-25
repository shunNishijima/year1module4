package resources.scoreBoard;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import simpleData.*;

@Path("/scoreboard")
public class ScoreBoardResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    public void showScoreBoard(boolean showScoreBoard){
        if (showScoreBoard) {
            simpleData.setShowScoreBoardTrue();
        }
        else {
            simpleData.setShowScoreBoardFalse();
        }
    }

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public boolean isScoreBoardVisible(){
        return simpleData.getShowScoreBoard();
    }
}

